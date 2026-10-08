// sdk for HarmonyOS.
//
// The ohos Kotlin/Native artifacts (Kuikly, kotlinx) are compiled with Tencent's
// Kotlin 2.0.21-KBA distribution, which the normal 2.1.21 build cannot consume, so
// HarmonyOS is a parallel build driven by privchat-app's settings.ohos.gradle.kts
// (the same arrangement as gearui-kit). Sources are the same commonMain + nativeMain;
// the Rust FFI comes from privchat-sdk built for aarch64-unknown-linux-ohos
// (see scripts/build-ohos-ffi.sh).
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "com.netonstream.privchat"

kotlin {
    ohosArm64 {
        compilations.getByName("main") {
            cinterops {
                create("privchat_sdk_ffi") {
                    defFile(project.file("src/nativeInterop/cinterop/privchat_sdk_ffi.def"))
                    packageName("privchat_sdk_ffi.cinterop")
                    includeDirs { allHeaders(project.file("src/nativeInterop/cinterop")) }
                }
                create("uniffi_runtime") {
                    defFile(project.file("src/nativeInterop/cinterop/uniffi_runtime.def"))
                    packageName("uniffi_runtime.cinterop")
                    includeDirs { allHeaders(project.file("src/nativeInterop/cinterop")) }
                }
            }
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-Xexpect-actual-classes",
                    "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                )
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Versions are the ohos (KBA) builds; coroutines and atomicfu match
                // KuiklyUI's own ohos build.
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-KBA-001")
                implementation("org.jetbrains.kotlinx:atomicfu:0.23.2-KBA-001")
                implementation("com.squareup.okio:okio:3.9.10-KBA-001")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1-KBA-003")
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
            // UniFFI runtime helper for timestamps. No type in the bindings uses it, and
            // it is written against kotlin.time.Instant, which the 2.0.21 stdlib lacks.
            kotlin.exclude("**/uniffi/runtime/TimestampHelper.kt")
        }
        val ohosArm64Main by getting {
            dependsOn(nativeMain)
        }
    }
}
