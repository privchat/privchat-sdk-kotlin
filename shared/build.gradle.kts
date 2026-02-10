import org.gradle.internal.os.OperatingSystem
import javax.inject.Inject
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    `maven-publish`
}

group = "io.privchat"
version = "0.1.0"

// ========== 100% Native Stack（不含 JVM）==========
// 架构：Rust Core + FFI + Kotlin/Native + KuiklyUI
// - androidTarget()：Android（JNI → .so）
// - ios/macos/linux/mingw：Kotlin/Native → .a / .so / .dll（纯原生二进制，不依赖 JVM）
// - 不配置 jvm()：避免编译负担，与 KuiklyUI Native-first 一致

kotlin {
    jvmToolchain(17)

    androidTarget()

    // Apple 平台（iosMain 一等公民 + macOS）
    // 配置 cinterop 链接 Rust FFI 静态库
    val privchatFfiDefFile = file("src/nativeInterop/cinterop/privchat_sdk_ffi.def")
    val uniffiRuntimeDefFile = file("src/nativeInterop/cinterop/uniffi_runtime.def")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosArm64(),
        macosX64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = "shared"
            isStatic = true
        }

        // 配置 cinterop 链接 privchat-ffi
        target.compilations.getByName("main") {
            cinterops {
                create("privchat_sdk_ffi") {
                    defFile(privchatFfiDefFile)
                    packageName("privchat_sdk_ffi.cinterop")
                    includeDirs {
                        allHeaders(project.file("src/nativeInterop/cinterop"))
                    }
                }
                create("uniffi_runtime") {
                    defFile(uniffiRuntimeDefFile)
                    packageName("uniffi_runtime.cinterop")
                    includeDirs {
                        allHeaders(project.file("src/nativeInterop/cinterop"))
                    }
                }
            }
        }
    }

    // Desktop Native（Kotlin/Native，无 JVM）
    linuxX64()
    mingwX64()

    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation("org.jetbrains.kotlinx:atomicfu:0.25.0")
            implementation("com.squareup.okio:okio:3.9.0")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
        }
        androidMain.dependencies {
            implementation("androidx.annotation:annotation:1.8.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
            implementation("net.java.dev.jna:jna:5.14.0@aar")
        }
    }
}

android {
    namespace = "io.privchat.sdk"
    compileSdk = 34
    defaultConfig { minSdk = 24 }
    buildFeatures { buildConfig = false }
}

// ========== Privchat FFI 配置 ==========
val os = OperatingSystem.current()!!
val privchatRustDir = rootProject.layout.projectDirectory.dir("../privchat-rust/crates/privchat-sdk-ffi")
val privchatSdkDir = rootProject.layout.projectDirectory.dir("../privchat-rust")
val privchatWorkspaceTargetDir = rootProject.layout.projectDirectory.dir("../privchat-rust/target/release")
val privchatHostLibName = when {
    os.isMacOsX -> "libprivchat_sdk_ffi.dylib"
    os.isWindows -> "privchat_sdk_ffi.dll"
    else -> "libprivchat_sdk_ffi.so"
}
val privchatHostLibFile = privchatWorkspaceTargetDir.file(privchatHostLibName)
val targetAbis = listOf("arm64-v8a", "x86_64")
val localProps = Properties()
rootProject.file("local.properties").takeIf { it.exists() }?.reader()?.use { localProps.load(it) }
val ndkDirPath = localProps.getProperty("ndk.dir") ?: run {
    val sdkDir = localProps.getProperty("sdk.dir") ?: ""
    if (sdkDir.isNotEmpty()) "$sdkDir/ndk/${libs.versions.ndk.get()}" else null
}

val cargoPath = localProps.getProperty("cargo.dir")?.let { "$it/cargo${if (os.isWindows) ".exe" else ""}" }
    ?: System.getenv("CARGO_HOME")?.let { "$it/bin/cargo${if (os.isWindows) ".exe" else ""}" }
    ?: "${System.getProperty("user.home")}/.cargo/bin/cargo${if (os.isWindows) ".exe" else ""}"

val cargoBuildAndroid = tasks.register<CargoNdkTask>("privchatCargoBuildAndroid") {
    abis.set(targetAbis)
    cargoBin.set(providers.provider { cargoPath })
    rustDir.set(privchatRustDir)
    jniOut.set(layout.projectDirectory.dir("src/androidMain/jniLibs/privchat"))
    ndkDir.set(ndkDirPath ?: "")
}

val cargoBuildHost = tasks.register<CargoHostTask>("privchatCargoBuildHost") {
    cargoBin.set(providers.provider { cargoPath })
    rustDir.set(privchatRustDir)
    sdkSourceDir.set(privchatSdkDir)
    jniOut.set(layout.buildDirectory.dir("nativeLibs/privchat"))
}

tasks.named("preBuild") { dependsOn(cargoBuildAndroid) }
tasks.matching { it.name.contains("JniLibFolders") || (it.name.contains("merge") && it.name.contains("NativeLibs")) }
    .configureEach { dependsOn(cargoBuildAndroid) }

// ========== Custom tasks ==========
@DisableCachingByDefault(because = "Builds native code")
abstract class CargoHostTask @Inject constructor(private val execOps: org.gradle.process.ExecOperations) : DefaultTask() {
    @get:Input abstract val cargoBin: Property<String>
    @get:InputDirectory abstract val rustDir: DirectoryProperty
    @get:Optional @get:InputDirectory abstract val sdkSourceDir: DirectoryProperty
    @get:OutputDirectory abstract val jniOut: DirectoryProperty

    @TaskAction
    fun run() {
        val rustDirFile = rustDir.get().asFile
        execOps.exec { workingDir = rustDirFile; commandLine(cargoBin.get(), "build", "--release") }
        val libName = when {
            OperatingSystem.current().isMacOsX -> "libprivchat_sdk_ffi.dylib"
            OperatingSystem.current().isWindows -> "privchat_sdk_ffi.dll"
            else -> "libprivchat_sdk_ffi.so"
        }
        val libSource = rustDirFile.parentFile.parentFile.resolve("target/release/$libName")
        if (!libSource.exists()) throw GradleException("Library not found: $libSource")
        jniOut.get().asFile.mkdirs()
        libSource.copyTo(jniOut.get().asFile.resolve(libName), overwrite = true)
    }
}

@DisableCachingByDefault(because = "Builds native code")
abstract class CargoNdkTask @Inject constructor(private val execOps: org.gradle.process.ExecOperations) : DefaultTask() {
    @get:Input abstract val abis: ListProperty<String>
    @get:Input abstract val cargoBin: Property<String>
    @get:InputDirectory abstract val rustDir: DirectoryProperty
    @get:OutputDirectory abstract val jniOut: DirectoryProperty
    @get:Optional @get:Input abstract val ndkDir: Property<String>

    @TaskAction
    fun run() {
        val rustDirFile = rustDir.get().asFile
        val outDir = jniOut.get().asFile
        outDir.mkdirs()
        val ndk = ndkDir.get().takeIf { it.isNotEmpty() }
            ?: throw GradleException("NDK path not found. Add ndk.dir to local.properties or ensure sdk.dir points to Android SDK with NDK installed.")
        abis.get().forEach { abi ->
            execOps.exec {
                workingDir = rustDirFile
                environment("ANDROID_NDK_HOME", ndk)
                commandLine(cargoBin.get(), "ndk", "-t", abi, "-o", outDir.absolutePath, "build", "--release")
            }
        }
    }
}

// ========== Maven 发布（供其他项目依赖）==========
// 发布到本地：./gradlew :shared:publishToMavenLocal
// 其他项目：repositories { mavenLocal(); ... } 然后 implementation("io.privchat:shared:0.1.0")
// artifactId 由 Gradle/KMP 按各平台自动生成，不要统一覆盖为同一 id
publishing {
    publications.withType<MavenPublication>().configureEach {
        groupId = "io.privchat"
        version = project.version.toString()
    }
}
