# PrivChat SDK for Kotlin

*[简体中文](./README.zh-Hans.md)*

Kotlin Multiplatform bindings for the PrivChat client SDK. One Kotlin API over the Rust
core, for Android and Kotlin/Native (iOS, macOS, Linux, Windows).

```
androidMain ─┐
             ├─ commonMain ─→ UniFFI bindings ─→ privchat-sdk-ffi (Rust)
nativeMain  ─┘
```

Artifact: `com.netonstream.privchat:sdk:0.1.0`

> This module cannot be built on its own — it compiles the Rust core from `../privchat-sdk`.
> Keep the two repositories as siblings.

## Quick start

```kotlin
import com.netonstream.privchat.sdk.*

val config = PrivchatConfig(
    dataDir = "/path/to/data",
    assetsDir = "/path/to/privchat-sdk/assets",
    serverEndpoints = listOf(
        ServerEndpoint(
            protocol = TransportProtocol.WebSocket,
            host = "127.0.0.1",
            port = 8081,
            path = "/",
            useTls = false,
        ),
    ),
)

val client = PrivchatClient.create(config).getOrThrow()
client.connect()
client.runBootstrapSync()
```

## Requirements

| | |
|---|---|
| JDK | 17+ |
| Kotlin | 2.1.21 |
| Rust | with UniFFI 0.31 |
| Android | SDK + NDK, plus `cargo install cargo-ndk` |
| Apple | Xcode |

`libprivchat_sdk_ffi.so` **is not committed**. It is a build artifact: Gradle runs
`privchatCargoBuildAndroid`, which compiles it from Rust source into
`sdk/build/generated/jniLibs`, and the Android packaging task picks it up from there.

## Consuming the SDK

### From privchat-app

`privchat-app` includes this module through a Gradle composite build, so a single install
task chains everything: Rust `.so` → Kotlin/UniFFI wrapper → app code → APK.

```bash
cd ../privchat-app
./gradlew :androidApp:installLocalDebug
```

If you have just changed Rust or the generated bindings and suspect Gradle's incremental
cache missed it:

```bash
./gradlew :privchat-sdk-kotlin:sdk:privchatCargoBuildAndroid \
          :androidApp:installLocalDebug --rerun-tasks
```

### From another project

**Composite build (recommended).** Clone this repository next to your project, keeping
`privchat-sdk` as its sibling, then in `settings.gradle.kts`:

```kotlin
includeBuild("../privchat-sdk-kotlin") {
    dependencySubstitution {
        substitute(module("com.netonstream.privchat:sdk")).using(project(":sdk"))
    }
}
```

```kotlin
implementation("com.netonstream.privchat:sdk")
```

**Local Maven** (Android, or several projects on one machine):

```bash
./gradlew :sdk:publishToMavenLocal
```

```kotlin
repositories { mavenLocal(); google(); mavenCentral() }
dependencies { implementation("com.netonstream.privchat:sdk:0.1.0") }
```

Publishing builds the Android `.so` as a normal Gradle dependency. It does **not**
regenerate UniFFI bindings — see below.

## Building

```bash
# Android SDK module (runs privchatCargoBuildAndroid and produces the .so)
./gradlew :sdk:assembleDebug

# Just the Android Rust FFI .so
./gradlew :sdk:privchatCargoBuildAndroid

# Apple static libraries
./gradlew :sdk:privchatCargoBuildAppleFfi

# Compile checks
./gradlew :sdk:compileKotlinIosArm64
./gradlew :sdk:compileKotlinMacosArm64
./gradlew :sdk:compileKotlinLinuxX64
./gradlew :sdk:compileKotlinMingwX64
```

## When the Rust FFI surface changes

Changing Rust internals — business logic, SQL, networking — needs nothing here; just
rebuild. Regenerate only when the **UniFFI surface** changes: adding, removing or renaming
an exported method, changing a data class field or an error type, or editing the UniFFI
config.

```bash
source ~/.zshrc                  # bindgen usually lives on PATH from here
./scripts/regenerate-uniffi.sh
```

The script builds a host dylib as bindgen input, generates the Kotlin Multiplatform
bindings, installs them into `commonMain` / `androidMain` / `nativeMain`, refreshes the
cinterop header, patches the contract version (below), and finally compiles Android and
iOS-simulator targets to check the result. **If that check fails it rolls the generated
files back**, so a broken generation never lands in your working tree.

If the bindgen binary is not on `PATH`:

```bash
KMP_BINDGEN=/abs/path/to/uniffi-bindgen-kotlin-multiplatform ./scripts/regenerate-uniffi.sh
```

Do not hand-edit `sdk/src/**/uniffi/privchat_sdk_ffi/*` or
`sdk/src/nativeInterop/cinterop/privchat_sdk_ffi.h`. Regenerating overwrites them; the
script is the only supported way to change them. If the public API's *meaning* changed,
the hand-written wrapper and DTOs in `sdk/src/commonMain/kotlin/com/netonstream/privchat/sdk/`
still need updating by hand.

The usual full loop after an FFI change:

```bash
./scripts/regenerate-uniffi.sh
./build-ios.sh
./scripts/gate-smoke.sh
```

### Contract version

`uniffi-bindgen-kotlin-multiplatform` v0.4.3 embeds `uniffi_bindgen` 0.28.3, which writes
`bindings_contract_version = 26` into the Android binding — but `privchat-sdk` builds its
scaffolding with UniFFI 0.31, whose contract version is **30**. Left alone, that mismatch
only surfaces at runtime as a `UniFFI contract version mismatch`.

`regenerate-uniffi.sh` and `build-ios.sh` rewrite it to 30 automatically. To override:

```bash
UNIFFI_CONTRACT_VERSION=30 ./scripts/regenerate-uniffi.sh
```

## iOS

Gradle and Xcode do **not** rebuild `libprivchat_sdk_ffi.a` before every iOS build. Build
it yourself after a fresh clone, after switching machines, or after touching the Rust FFI:

```bash
./gradlew :sdk:privchatCargoBuildAppleFfi
```

Then build in Xcode or with `xcodebuild`. Kotlin-only changes need no rebuild.

`Product → Clean Build Folder` clears Xcode's DerivedData; it does **not** rebuild the Rust
static library, so it will not fix a stale `.a`.

There is no XCFramework. Each target links
`privchat-sdk/target/<triple>/release/libprivchat_sdk_ffi.a` directly, through
`libraryPaths` in `privchat_sdk_ffi.def`.

## Sample

`:sample` is a shared KMP library; the installable Android app is `:sample-androidApp`
(under `sample/androidApp`).

```bash
./gradlew :sample-androidApp:installDebug
./gradlew :sample:linkDebugFrameworkIosArm64   # framework for sample/iosApp
```

On iOS, run `:sdk:privchatCargoBuildAppleFfi` once before building `sample/iosApp`.

The sample covers the main verification path on Android and iOS: connect → login/register →
authenticate → bootstrap → conversation list. See [sample/README.md](sample/README.md).

## Design constraints

- **UniFFI async exports only.** No hand-written iOS bridge (`SdkInvoker.ios.kt`, sync
  helpers, by-handle calls); every native call goes through UniFFI async and surfaces as a
  Kotlin `suspend` function.
- **No XCFramework.** Targets link the Rust static library at compile time.
- The public API follows `privchat-sdk`'s contract — see
  [`CLIENT_SERVICE_FACADE_SPEC.md`](../privchat-sdk/docs/CLIENT_SERVICE_FACADE_SPEC.md) —
  and stays aligned with privchat-sdk-android and privchat-sdk-swift.
