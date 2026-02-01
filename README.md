# Privchat SDK Kotlin

Kotlin 生态统一入口 SDK，面向 KuiklyUI、Compose Desktop、KMP、Android。与 [privchat-sdk-android](../privchat-sdk-android) / [privchat-sdk-swift](../privchat-sdk-swift) 并行，均直连 privchat-ffi。

**产物**：klib（Kotlin/Native bindings，构建时 cinterop 链接 privchat-ffi，**不产出 XCFramework**，详见 [ARTIFACT_CONTRACT](../privchat-sdk/docs/ARTIFACT_CONTRACT.md)）

**Maven**：`io.privchat:sdk-kotlin:0.1.0`

## 技术栈

- Kotlin 2.1.21
- Java 17 LTS
- Targets: `androidMain` + `iosMain` + `macosMain` + `linuxMain` + `mingwMain`（**不含 jvmMain**）

**架构**：100% Native Stack — Rust Core + Kotlin/Native + KuiklyUI。iosMain 为一等公民。  
**无 JVM**：macosMain / linuxMain / mingwMain 为 Kotlin/Native（.a / .so / .dll），不依赖 JVM。

## 前置要求

- JDK 17+
- Android SDK (Android target)
- Rust + cargo-ndk (Android)
- Xcode (iOS)

## 构建

```bash
# 确保 privchat-ffi 已构建
cd ../privchat-sdk
cargo build -p privchat-ffi --release

# 构建 shared 模块
cd ../privchat-sdk-kotlin
./gradlew :shared:assembleDebug           # Android
./gradlew :shared:compileKotlinIosArm64   # iOS
./gradlew :shared:compileKotlinMacosArm64 # macOS
./gradlew :shared:compileKotlinLinuxX64   # Linux Desktop
./gradlew :shared:compileKotlinMingwX64   # Windows Desktop
```

## Sample

```bash
./gradlew :sample:installDebug           # Android
./gradlew :sample:linkDebugFrameworkIosArm64  # iOS framework
```

详见 [sample/README.md](sample/README.md)。Compose Multiplatform，支持 Android + iOS。功能最小集：connect • bootstrap sync • list sessions • send text • send image • upload progress • retry • reconnect

## 使用

```kotlin
import io.privchat.sdk.*

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

遵循 [SDK_API_CONTRACT](../privchat-sdk/docs/SDK_API_CONTRACT.md)，与 privchat-sdk-android / privchat-sdk-swift 保持一致。

## Native 实现说明（iosMain / macosMain / linuxMain）

当前 `iosMain`、`macosMain`、`linuxMain` 为占位实现。完整实现需：

1. **构建 Rust**：`cargo build -p privchat-ffi --release --target aarch64-apple-ios` 等
2. **cinterop 链接**：配置 cinterop.def 指向 UniFFI C ABI 头文件（`PrivchatSDKFFI.h`）并链接 `.a` / `.so`
3. **实现 actual**：复用 androidMain 逻辑，将 `io.privchat.ffi.*` 映射到 cinterop 生成的 C 绑定
4. **不依赖 XCFramework**：KMP 产物为 klib，各平台构建时各自链接 FFI

参考 [uniffi-kotlin-multiplatform-bindings](https://github.com/UbiqueInnovation/uniffi-kotlin-multiplatform-bindings)（需 uniffi 版本兼容）。
