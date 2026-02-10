# Privchat SDK Kotlin

Kotlin 多平台统一 SDK，当前架构为：

- `androidMain`：Android 实现
- `nativeMain`：Native 实现（iOS/macOS/Linux/Windows 共用一套 actual）

底层统一依赖 `privchat-rust`（`privchat-sdk` + `privchat-sdk-ffi`），通过 UniFFI + cinterop 绑定。

产物：`io.privchat:shared:0.1.0`

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
   dependencies { implementation("io.privchat:shared:0.1.0") }
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

## FFI 头文件与静态库生成方法（privchat-rust）

修改 Rust 侧 `privchat-sdk-ffi` 接口（如新增/修改 FFI 方法）后，需更新 C 头文件并重新编译各平台 `.a`，Kotlin cinterop 才能正确解析并链接。

**1. 修改 Rust 并本地编译通过**

```bash
cd ../privchat-rust
cargo build -p privchat-sdk-ffi --release
```

**2. 重新生成 C 头文件（Swift 绑定目录）**

头文件路径：`privchat-rust/crates/privchat-sdk-ffi/bindings/swift/PrivchatSDKFFI.h`。  
**注意**：若升级了 UniFFI（例如从 0.31 切到 main），必须重新执行本步骤并重新编译各 target 的 `libprivchat_sdk_ffi.a`，否则 iOS 可能 EXC_BAD_ACCESS。  
若 UniFFI 未为新增的同步方法生成 C 声明，需在该头文件中**手动**添加对应声明（例如 `void uniffi_privchat_ffi_fn_method_privchatsdk_xxx(...RustCallStatus *_Nonnull out_status);`）。

```bash
cd privchat-rust/crates/privchat-sdk-ffi
cargo run --manifest-path uniffi-bindgen/Cargo.toml --release -- \
  generate ../../target/release/libprivchat_sdk_ffi.dylib \
  --language swift --out-dir bindings/swift --config uniffi.toml
```

**3. 为各 Apple 目标编译静态库 `.a`**

cinterop 的 `libraryPaths` 指向 `privchat-rust/target/<target>/release/`，需为用到的目标各编一份 `libprivchat_sdk_ffi.a`：

```bash
cd privchat-rust
cargo build -p privchat-sdk-ffi --release --target aarch64-apple-ios-sim   # iOS 模拟器 (M1)
cargo build -p privchat-sdk-ffi --release --target aarch64-apple-ios        # 真机
cargo build -p privchat-sdk-ffi --release --target x86_64-apple-ios         # Intel 模拟器（按需）
cargo build -p privchat-sdk-ffi --release --target aarch64-apple-darwin     # macOS ARM（按需）
cargo build -p privchat-sdk-ffi --release --target x86_64-apple-darwin      # macOS x64（按需）
```

**4. 再编 Kotlin / 跑应用**

```bash
cd privchat-sdk-kotlin
./gradlew :shared:compileKotlinIosSimulatorArm64   # 仅 iOS 模拟器
# 或在包含本库的工程（如 privchat-ui）中执行对应 app 的 iOS 编译与运行
```

说明：本项目**不产出 XCFramework**，各平台使用 `privchat_sdk_ffi.def` 中的 `libraryPaths` 直接链接上述 `target/<triple>/release/libprivchat_sdk_ffi.a`。

## Sample

```bash
./gradlew :sample:installDebug           # Android
./gradlew :sample:linkDebugFrameworkIosArm64  # iOS framework
```

详见 [sample/README.md](sample/README.md)。当前主验证链路是 Android + iOS：connect -> login/register -> authenticate -> bootstrap -> 会话列表。

## 使用

```kotlin
import io.privchat.sdk.*

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
