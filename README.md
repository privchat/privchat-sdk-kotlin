# Privchat SDK Kotlin

Kotlin 多平台统一 SDK，当前架构为：

- `androidMain`：Android 实现
- `nativeMain`：Native 实现（iOS/macOS/Linux/Windows 共用一套 actual）

底层统一依赖 `privchat-sdk`（`privchat-sdk` + `privchat-sdk-ffi`），通过 UniFFI + cinterop 绑定。

产物：`com.netonstream.privchat:sdk:0.1.0`

## 在 privchat-app 中使用

`privchat-app` 通过 Gradle Composite Build 直接依赖本目录的 `:sdk` 模块。日常 Android 联调不需要手动先编译 `.so`，在 `privchat-app` 中执行安装任务即可自动串起：

1. `privchat-sdk` Rust FFI Android `.so` 构建
2. `privchat-sdk-kotlin:sdk` Kotlin/UniFFI wrapper 编译
3. `privchat-app` 业务/UI 编译
4. APK 打包并安装到手机

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-app
./gradlew :androidApp:installLocalDebug
```

如果刚改过 Rust FFI、UniFFI 生成代码，或怀疑 Gradle 增量缓存没有捕捉到变更，用更保守的强制重建命令：

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-app
./gradlew :privchat-sdk-kotlin:sdk:privchatCargoBuildAndroid :androidApp:installLocalDebug --rerun-tasks
```

## 在其他项目中使用

**方式一：作为本地子项目（推荐，需保留整仓目录结构）**

1. 将本仓库克隆或 submodule 到你的项目旁（或子目录），保证 `privchat-sdk` 与 `privchat-sdk-kotlin` 同级。
2. 在你的项目 `settings.gradle.kts` 里 include 本仓库的 sdk 模块，例如：
   ```kotlin
   includeBuild("../privchat-sdk-kotlin") {
       dependencySubstitution {
           substitute(module("com.netonstream.privchat:sdk")).using(project(":sdk"))
       }
   }
   ```
   然后在依赖中使用：
   ```kotlin
   implementation("com.netonstream.privchat:sdk")
   ```

说明：`sdk` 构建依赖同仓库内的 `privchat-sdk`，不能仅拷贝 `privchat-sdk-kotlin` 单目录独立构建。

**方式二：Maven 本地发布（仅 Android 或同机多项目）**

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
./gradlew :sdk:publishToMavenLocal
```

然后在目标项目里添加本地 Maven 仓库并依赖：

```kotlin
repositories { mavenLocal(); google(); mavenCentral() }
dependencies { implementation("com.netonstream.privchat:sdk:0.1.0") }
```

发布任务会按 Gradle 依赖自动构建 Android 所需 `.so`；如果是 UniFFI 接口变更，仍应先按下文执行 `./scripts/regenerate-uniffi.sh`。

## 技术栈

- Kotlin 2.1.21
- Java 17 LTS
- Rust + UniFFI 0.31
- Targets: Android + Native（iOS/macOS/Linux/Windows）

## 前置要求

- JDK 17+
- Android SDK + NDK (Android 端必须)
- Rust + cargo-ndk (Android)
- Xcode (iOS/macOS)

> **重要说明**：Android 端的 `libprivchat_sdk_ffi.so` **不提交到代码仓库**。它是构建产物，Gradle 会通过 `privchatCargoBuildAndroid` 自动调用 `cargo ndk` 从源码编译生成，输出到 `sdk/build/generated/jniLibs`，再由 Android 打包任务收进去。确保你的环境已安装 `cargo-ndk`：`cargo install cargo-ndk`。

## 构建

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin

# Android SDK 模块。会自动执行 :sdk:privchatCargoBuildAndroid 并生成 .so。
./gradlew :sdk:assembleDebug

# 只想显式构建 Android Rust FFI .so 时使用。
./gradlew :sdk:privchatCargoBuildAndroid

# Apple 静态库。iOS/macOS 链接前建议手动触发一次。
./gradlew :sdk:privchatCargoBuildAppleFfi

# Kotlin/Native 编译检查（按需）
./gradlew :sdk:compileKotlinIosArm64
./gradlew :sdk:compileKotlinMacosArm64
./gradlew :sdk:compileKotlinLinuxX64
./gradlew :sdk:compileKotlinMingwX64
```

## UniFFI 产物同步

只改 Rust 业务实现、SQL、网络逻辑等，且 **没有改变 UniFFI 暴露的方法/类型/字段** 时，不需要重新生成 UniFFI 产物，直接跑 Gradle 构建即可。

只要修改了 `privchat-sdk-ffi` 对外接口，例如新增/删除/改名 FFI 方法、修改 FFI data class 字段、修改错误类型、更新 UniFFI 配置，就必须同步 Kotlin/Native 生成文件：

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
source ~/.zshrc
./scripts/regenerate-uniffi.sh
```

脚本会自动完成：

- 编译 host `privchat-sdk-ffi` dylib，作为 bindgen 输入。
- 调用 `uniffi-bindgen-kotlin-multiplatform` 生成 Kotlin/Native 绑定。
- 安装生成产物到 `sdk/src/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt`。
- 安装生成产物到 `sdk/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt`。
- 安装生成产物到 `sdk/src/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt`。
- 刷新 cinterop 头文件 `sdk/src/nativeInterop/cinterop/privchat_sdk_ffi.h`。
- 修正 Android UniFFI contract version。
- 执行 `:sdk:compileDebugKotlinAndroid` 与 `:sdk:compileKotlinIosSimulatorArm64` 校验；如果校验失败，会自动回滚生成文件。

如果 bindgen 不在默认路径，使用：

```bash
KMP_BINDGEN=/abs/path/to/uniffi-bindgen-kotlin-multiplatform ./scripts/regenerate-uniffi.sh
```

请不要手动编辑 `sdk/src/**/uniffi/privchat_sdk_ffi/*` 或 `sdk/src/nativeInterop/cinterop/privchat_sdk_ffi.h`，这些文件应统一由脚本刷新。生成后如果公共 API 语义变化，还需要手动更新 SDK wrapper/DTO，例如 `sdk/src/commonMain/kotlin/com/netonstream/privchat/sdk/**`。

## 日常脚本（推荐固定流程）

修改 Rust FFI 对外接口后，推荐顺序：

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
source ~/.zshrc
./scripts/regenerate-uniffi.sh
./build-ios.sh
./scripts/gate-smoke.sh
```

如果只改 Kotlin wrapper/DTO 或 app 业务代码，没有改 UniFFI 接口，可直接执行 `./scripts/gate-smoke.sh` 或从 `privchat-app` 执行 `./gradlew :androidApp:installLocalDebug`。

## iOS 编译流程（手动触发 Rust FFI）

默认情况下，Gradle/Xcode **不会**在每次 iOS 编译前自动重建 `libprivchat_sdk_ffi.a`。  
推荐流程如下：

1. 首次拉代码、切换机器、或修改了 Rust FFI（`privchat-sdk-ffi` / UDL / UniFFI 导出）后，先执行：
   ```bash
   cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
   ./gradlew :sdk:privchatCargoBuildAppleFfi
   ```
2. 然后再在 Xcode 编译 iOS App（或命令行 `xcodebuild`）。
3. 如果只改了 Kotlin UI/业务代码，没有改 Rust FFI，可以直接编译 iOS，不需要重建 `.a`。

说明：`Product -> Clean Build Folder` 主要清理 Xcode/DerivedData，不保证重建 `privchat-sdk/target/.../libprivchat_sdk_ffi.a`。

## FFI 头文件与静态库生成方法（privchat-sdk）

修改 Rust 侧 `privchat-sdk-ffi` 接口（如新增/修改 FFI 方法）后，需要更新头文件并重新编译各目标 `.a`。

下面提供两种方式（二选一）：

- 方式 A：标准 UniFFI 命令（官方 `uniffi-bindgen`）
- 方式 B：KMP bindgen（`uniffi-kotlin-multiplatform-bindings`，一并刷新 Kotlin 生成代码）

### 方式 A：标准 UniFFI（官方）

适用：你只想走标准 UniFFI 头文件生成链路。

```bash
# 1) 编译 host dylib
cd ../privchat-sdk
cargo build -p privchat-sdk-ffi --release

# 2) 生成 Swift/C 头（标准 UniFFI 命令）
cd crates/privchat-sdk-ffi
cargo run --manifest-path uniffi-bindgen/Cargo.toml --release -- \
  generate ../../target/release/libprivchat_sdk_ffi.dylib \
  --language swift --out-dir bindings/swift --config uniffi.toml

# 3) 编译目标静态库（按需）
cd ../../
cargo build -p privchat-sdk-ffi --release --target aarch64-apple-ios-sim
cargo build -p privchat-sdk-ffi --release --target aarch64-apple-ios
```

### 方式 B：KMP bindgen（推荐）

适用：Rust FFI 方法/类型有改动，需要同步刷新 `sdk/src/**/uniffi/privchat_sdk_ffi/*` 与 cinterop 头文件。

优先使用脚本（一键生成+安装+编译校验）：

```bash
cd ../privchat-sdk-kotlin
./scripts/regenerate-uniffi.sh
```

脚本会在编译校验失败时自动回滚生成文件，避免把不可编译产物写入工作区。

可选：如果 `uniffi-bindgen-kotlin-multiplatform` 不在默认路径，先指定：

```bash
KMP_BINDGEN=/abs/path/to/uniffi-bindgen-kotlin-multiplatform ./scripts/regenerate-uniffi.sh
```

### 验证

```bash
cd ../privchat-sdk-kotlin
./gradlew :sdk:compileKotlinIosSimulatorArm64
./gradlew :sdk:compileDebugKotlinAndroid
```

说明：本项目**不产出 XCFramework**，各平台使用 `privchat_sdk_ffi.def` 中的 `libraryPaths` 直接链接 `target/<triple>/release/libprivchat_sdk_ffi.a`。
请不要手动编辑 `sdk/src/**/uniffi/privchat_sdk_ffi/*`，统一通过 `scripts/regenerate-uniffi.sh` 刷新。

### UniFFI contract version 修正

当前 `uniffi-bindgen-kotlin-multiplatform`（v0.4.3）内置的 `uniffi_bindgen` 版本为 **0.28.3**，生成的 Android binding 默认 `bindings_contract_version = 26`；而 `privchat-sdk`（`uniffi = 0.31.0`）的 scaffolding contract version 为 **30**。

`scripts/regenerate-uniffi.sh` 和 `build-ios.sh` 已内置自动修正逻辑，会在生成后把 Android binding 中的 `bindings_contract_version` 覆盖为 `30`，避免运行时 `UniFFI contract version mismatch`。

如需调整该值，可在执行脚本时覆盖：

```bash
UNIFFI_CONTRACT_VERSION=30 ./scripts/regenerate-uniffi.sh
```

## Sample

```bash
./gradlew :sample:installDebug           # Android
./gradlew :sample:linkDebugFrameworkIosArm64  # iOS framework
```

详见 [sample/README.md](sample/README.md)。当前主验证链路是 Android + iOS：connect -> login/register -> authenticate -> bootstrap -> 会话列表。

## 使用

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
            useTls = false
        )
    )
)

val client = PrivchatClient.create(config).getOrThrow()
client.connect()
client.runBootstrapSync()
```

## API 契约

遵循 `privchat-sdk` 的公开 API 约束与迁移文档（见 `../privchat-sdk/docs/public-api-v2.md`、`../privchat-sdk/docs/architecture-spec.md`），与 privchat-sdk-android / privchat-sdk-swift 保持一致。

## 约束

- 不走旧版手写 iOS 桥接（`SdkInvoker.ios.kt`/同步 helper/by-handle）。
- Native 路径全部走 UniFFI async 导出 + Kotlin `suspend`。
- 不产出 XCFramework；KMP 在各目标编译阶段直接链接 Rust 静态库。
