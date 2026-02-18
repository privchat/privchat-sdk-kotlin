# Privchat SDK Kotlin

Kotlin 多平台统一 SDK，当前架构为：

- `androidMain`：Android 实现
- `nativeMain`：Native 实现（iOS/macOS/Linux/Windows 共用一套 actual）

底层统一依赖 `privchat-rust`（`privchat-sdk` + `privchat-sdk-ffi`），通过 UniFFI + cinterop 绑定。

产物：`com.netonstream.privchat:shared:0.1.0`

## 在其他项目中使用

**方式一：Maven 本地发布（推荐，仅 Android 或同机多项目）**

1. 在本仓库根目录先构建 Rust FFI，再发布 Kotlin 库到本地 Maven：
   ```bash
   cd ../privchat-rust && cargo build -p privchat-sdk-ffi --release
   cd ../privchat-sdk-kotlin && ./gradlew :shared:publishToMavenLocal
   ```
2. 在你的项目里添加本地 Maven 仓库并依赖：
   ```kotlin
   // settings.gradle.kts 或 build.gradle.kts
   repositories { mavenLocal(); google(); mavenCentral() }
   dependencies { implementation("com.netonstream.privchat:shared:0.1.0") }
   ```

**方式二：作为本地子项目（需保留整仓目录结构）**

1. 将本仓库克隆或 submodule 到你的项目旁（或子目录），保证 `privchat-rust` 与 `privchat-sdk-kotlin` 同级。
2. 在你的项目 `settings.gradle.kts` 里 include 本仓库的 shared 模块，例如：
   ```kotlin
   includeBuild("../msgtrans-rust/privchat-sdk-kotlin")  // 或 include 子模块
   ```
   或把 `privchat-sdk-kotlin` 作为复合构建 / 子项目引入，然后：
   ```kotlin
   implementation(project(":privchat-sdk-kotlin:shared"))
   ```

说明：`shared` 构建依赖同仓库内的 `privchat-rust`，不能仅拷贝 `privchat-sdk-kotlin` 单目录独立构建。

## 技术栈

- Kotlin 2.1.21
- Java 17 LTS
- Rust + UniFFI 0.31
- Targets: Android + Native（iOS/macOS/Linux/Windows）

## 前置要求

- JDK 17+
- Android SDK (Android target)
- Rust + cargo-ndk (Android)
- Xcode (iOS)

## 构建

```bash
# 确保 privchat-rust FFI 已构建
cd ../privchat-rust
cargo build -p privchat-sdk-ffi --release

# 构建 shared 模块（按需）
cd ../privchat-sdk-kotlin
./gradlew :shared:assembleDebug           # Android
./gradlew :shared:compileKotlinIosArm64   # iOS
./gradlew :shared:compileKotlinMacosArm64 # macOS
./gradlew :shared:compileKotlinLinuxX64   # Linux Desktop
./gradlew :shared:compileKotlinMingwX64   # Windows Desktop
```

## 日常脚本（推荐固定流程）

修改 Rust FFI（UDL/ffi 导出）后，统一使用下面顺序：

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
source ~/.zshrc
./scripts/regenerate-uniffi.sh
./build-ios.sh
./scripts/gate-smoke.sh
```

- `scripts/regenerate-uniffi.sh`：生成 UniFFI 绑定、刷新头文件并做 Kotlin 编译校验（失败自动回滚生成文件）。
- `build-ios.sh`：构建 iOS 所需 Rust 静态库并同步 iOS framework。
- `scripts/gate-smoke.sh`：执行 Rust + Kotlin(Android/iOS shared) 门禁编译检查。

如果只改 Kotlin 业务代码（未改 Rust FFI），可直接执行：

```bash
cd /Users/zoujiaqing/projects/privchat/privchat-sdk-kotlin
source ~/.zshrc
./scripts/gate-smoke.sh
```

## FFI 头文件与静态库生成方法（privchat-rust）

修改 Rust 侧 `privchat-sdk-ffi` 接口（如新增/修改 FFI 方法）后，需要更新头文件并重新编译各目标 `.a`。

下面提供两种方式（二选一）：

- 方式 A：标准 UniFFI 命令（官方 `uniffi-bindgen`）
- 方式 B：KMP bindgen（`uniffi-kotlin-multiplatform-bindings`，一并刷新 Kotlin 生成代码）

### 方式 A：标准 UniFFI（官方）

适用：你只想走标准 UniFFI 头文件生成链路。

```bash
# 1) 编译 host dylib
cd ../privchat-rust
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

适用：Rust FFI 方法/类型有改动，需要同步刷新 `shared/src/**/uniffi/privchat_sdk_ffi/*` 与 cinterop 头文件。

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
./gradlew :shared:compileKotlinIosSimulatorArm64
./gradlew :shared:compileDebugKotlinAndroid
```

说明：本项目**不产出 XCFramework**，各平台使用 `privchat_sdk_ffi.def` 中的 `libraryPaths` 直接链接 `target/<triple>/release/libprivchat_sdk_ffi.a`。
请不要手动编辑 `shared/src/**/uniffi/privchat_sdk_ffi/*`，统一通过 `scripts/regenerate-uniffi.sh` 刷新。

### UniFFI contract version 修正

当前 `uniffi-bindgen-kotlin-multiplatform`（v0.4.3）内置的 `uniffi_bindgen` 版本为 **0.28.3**，生成的 binding 代码中 `bindings_contract_version = 26`。而 `privchat-rust` 使用的 `uniffi` 版本为 **0.31.0**，其 scaffolding 返回的 contract version 为 **30**。

每次通过 `regenerate-uniffi.sh` 重新生成 binding 后，需要手动修正 Android binding 中的版本号，否则运行时会报 `UniFFI contract version mismatch` 错误：

```
shared/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt
```

搜索 `bindings_contract_version = 26`，改为：

```kotlin
val bindings_contract_version = 30
```

后续如果升级 `uniffi-bindgen-kotlin-multiplatform` 使其内置的 `uniffi_bindgen` 版本与 Rust 侧一致，则无需此手动修正。

## Sample

```bash
./gradlew :sample:installDebug           # Android
./gradlew :sample:linkDebugFrameworkIosArm64  # iOS framework
```

详见 [sample/README.md](sample/README.md)。当前主验证链路是 Android + iOS：connect -> login/register -> authenticate -> bootstrap -> 会话列表。

## 使用

```kotlin
import om.netonstream.privchat.sdk.*

val config = PrivchatConfig(
    dataDir = "/path/to/data",
    assetsDir = "/path/to/privchat-rust/assets",
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

遵循 `privchat-rust` 的公开 API 约束与迁移文档（见 `../privchat-rust/docs/public-api-v2.md`、`../privchat-rust/docs/architecture-spec.md`），与 privchat-sdk-android / privchat-sdk-swift 保持一致。

## 约束

- 不走旧版手写 iOS 桥接（`SdkInvoker.ios.kt`/同步 helper/by-handle）。
- Native 路径全部走 UniFFI async 导出 + Kotlin `suspend`。
- 不产出 XCFramework；KMP 在各目标编译阶段直接链接 Rust 静态库。
