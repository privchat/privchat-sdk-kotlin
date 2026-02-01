import org.gradle.internal.os.OperatingSystem
import javax.inject.Inject
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
val privchatRustDir = rootProject.layout.projectDirectory.dir("../privchat-sdk/crates/privchat-ffi")
val privchatSdkDir = rootProject.layout.projectDirectory.dir("../privchat-sdk")
val privchatWorkspaceTargetDir = rootProject.layout.projectDirectory.dir("../privchat-sdk/target/release")
val privchatHostLibName = when {
    os.isMacOsX -> "libprivchat_ffi.dylib"
    os.isWindows -> "privchat_ffi.dll"
    else -> "libprivchat_ffi.so"
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

val uniffiAndroidOut = layout.buildDirectory.dir("generated/uniffi/privchat/androidMain/kotlin")
val genUniFFIAndroid = tasks.register<GenerateUniFFITask>("genPrivchatUniFFIAndroid") {
    dependsOn(cargoBuildHost)
    libraryFile.set(privchatHostLibFile)
    configFile.set(privchatRustDir.file("uniffi.android.toml"))
    language.set("kotlin")
    uniffiPath.set(providers.gradleProperty("uniffiBindgenPath").orElse("uniffi-bindgen"))
    useFallbackCargo.set(providers.provider { true })
    cargoBin.set(providers.provider { cargoPath })
    vendoredManifest.set(privchatRustDir.file("uniffi-bindgen/Cargo.toml"))
    outDir.set(uniffiAndroidOut)
}

kotlin {
    sourceSets {
        getByName("androidMain") {
            kotlin.srcDir(uniffiAndroidOut)
        }
    }
}

tasks.named("preBuild") { dependsOn(genUniFFIAndroid, cargoBuildAndroid) }
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
            OperatingSystem.current().isMacOsX -> "libprivchat_ffi.dylib"
            OperatingSystem.current().isWindows -> "privchat_ffi.dll"
            else -> "libprivchat_ffi.so"
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

@DisableCachingByDefault(because = "Runs external tool")
abstract class GenerateUniFFITask @Inject constructor(private val execOps: org.gradle.process.ExecOperations) : DefaultTask() {
    @get:InputFile abstract val libraryFile: RegularFileProperty
    @get:Optional @get:InputFile abstract val configFile: RegularFileProperty
    @get:Input abstract val language: Property<String>
    @get:Input abstract val uniffiPath: Property<String>
    @get:Input abstract val useFallbackCargo: Property<Boolean>
    @get:Input abstract val cargoBin: Property<String>
    @get:Optional @get:InputFile abstract val vendoredManifest: RegularFileProperty
    @get:OutputDirectory abstract val outDir: DirectoryProperty

    @TaskAction
    fun run() {
        val lib = libraryFile.get().asFile
        val outDirFile = outDir.get().asFile
        outDirFile.mkdirs()
        val manifest = vendoredManifest.get().asFile
        val cmd = mutableListOf(
            cargoBin.get(), "run", "--release",
            "--manifest-path", manifest.absolutePath,
            "--bin", "uniffi-bindgen",
            "--", "generate", "--library", lib.absolutePath,
            "--language", language.get(), "--out-dir", outDirFile.absolutePath,
            "--config", configFile.get().asFile.absolutePath
        )
        execOps.exec { workingDir = manifest.parentFile; commandLine(cmd) }
    }
}
