# PrivChat SDK for Kotlin

*[English](./README.md)*

PrivChat 客户端 SDK 的 Kotlin Multiplatform 绑定。在 Rust 内核之上提供一套 Kotlin API，
覆盖 Android 与 Kotlin/Native（iOS、macOS、Linux、Windows）。

```
androidMain ─┐
             ├─ commonMain ─→ UniFFI 绑定 ─→ privchat-sdk-ffi (Rust)
nativeMain  ─┘
```

产物：`com.netonstream.privchat:sdk:0.1.0`

> 本模块**不能单独构建**——它要编译 `../privchat-sdk` 里的 Rust 内核，两个仓库必须同级放置。

## 快速开始

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

## 环境要求

| | |
|---|---|
| JDK | 17+ |
| Kotlin | 2.1.21 |
| Rust | 配合 UniFFI 0.31 |
| Android | SDK + NDK，另需 `cargo install cargo-ndk` |
| Apple | Xcode |

`libprivchat_sdk_ffi.so` **不提交到仓库**。它是构建产物：Gradle 执行
`privchatCargoBuildAndroid` 从 Rust 源码编译，输出到 `sdk/build/generated/jniLibs`，
再由 Android 打包任务收进去。

## 引入方式

### 在 privchat-app 中

`privchat-app` 通过 Gradle Composite Build 直接依赖本模块，一条安装命令即可串起全链：
Rust `.so` → Kotlin/UniFFI wrapper → 业务代码 → APK。

```bash
cd ../privchat-app
./gradlew :androidApp:installLocalDebug
```

刚改过 Rust 或生成代码、怀疑 Gradle 增量缓存没捕捉到变更时：

```bash
./gradlew :privchat-sdk-kotlin:sdk:privchatCargoBuildAndroid \
          :androidApp:installLocalDebug --rerun-tasks
```

### 在其他项目中

**Composite Build（推荐）。** 把本仓库克隆到你的项目旁边，并保证 `privchat-sdk` 与它同级，
然后在 `settings.gradle.kts` 里：

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

**本地 Maven**（Android，或同一台机器上的多个项目）：

```bash
./gradlew :sdk:publishToMavenLocal
```

```kotlin
repositories { mavenLocal(); google(); mavenCentral() }
dependencies { implementation("com.netonstream.privchat:sdk:0.1.0") }
```

发布任务会按 Gradle 依赖顺带构建 Android `.so`，但**不会**重新生成 UniFFI 绑定——见下节。

## 构建

```bash
# Android SDK 模块（会执行 privchatCargoBuildAndroid 并产出 .so）
./gradlew :sdk:assembleDebug

# 只构建 Android Rust FFI .so
./gradlew :sdk:privchatCargoBuildAndroid

# Apple 静态库
./gradlew :sdk:privchatCargoBuildAppleFfi

# 编译检查
./gradlew :sdk:compileKotlinIosArm64
./gradlew :sdk:compileKotlinMacosArm64
./gradlew :sdk:compileKotlinLinuxX64
./gradlew :sdk:compileKotlinMingwX64
```

## 改动 Rust FFI 接口之后

只改 Rust 内部实现——业务逻辑、SQL、网络——这里什么都不用做，直接重新构建即可。
只有**改变了 UniFFI 对外接口**才需要重新生成：新增/删除/改名导出方法、改 data class 字段、
改错误类型、改 UniFFI 配置。

```bash
source ~/.zshrc                  # bindgen 通常由它带进 PATH
./scripts/regenerate-uniffi.sh
```

脚本会：编译 host dylib 作为 bindgen 输入 → 生成 KMP 绑定 → 安装到 `commonMain` /
`androidMain` / `nativeMain` → 刷新 cinterop 头文件 → 修正 contract version（见下）→
最后编译 Android 与 iOS 模拟器目标做校验。**校验失败会自动回滚生成的文件**，不会把编译
不过的产物留在工作区。

bindgen 不在 `PATH` 上时：

```bash
KMP_BINDGEN=/abs/path/to/uniffi-bindgen-kotlin-multiplatform ./scripts/regenerate-uniffi.sh
```

不要手工编辑 `sdk/src/**/uniffi/privchat_sdk_ffi/*` 和
`sdk/src/nativeInterop/cinterop/privchat_sdk_ffi.h`——重新生成会覆盖它们，脚本是唯一
受支持的修改途径。如果公共 API 的**语义**变了，还要手动更新
`sdk/src/commonMain/kotlin/com/netonstream/privchat/sdk/` 下的 wrapper 与 DTO。

改完 FFI 的标准流程：

```bash
./scripts/regenerate-uniffi.sh
./build-ios.sh
./scripts/gate-smoke.sh
```

### contract version

`uniffi-bindgen-kotlin-multiplatform` v0.4.3 内置的是 `uniffi_bindgen` 0.28.3，生成的
Android 绑定里写的是 `bindings_contract_version = 26`；而 `privchat-sdk` 用 UniFFI 0.31
构建 scaffolding，contract version 是 **30**。不处理的话，这个不一致**只在运行期**才暴露，
报 `UniFFI contract version mismatch`。

`regenerate-uniffi.sh` 与 `build-ios.sh` 会自动改写成 30。需要覆盖时：

```bash
UNIFFI_CONTRACT_VERSION=30 ./scripts/regenerate-uniffi.sh
```

## iOS

Gradle 和 Xcode **不会**在每次 iOS 编译前重建 `libprivchat_sdk_ffi.a`。首次拉代码、换机器、
或改过 Rust FFI 之后，需要自己先构建：

```bash
./gradlew :sdk:privchatCargoBuildAppleFfi
```

然后再用 Xcode 或 `xcodebuild` 编译。只改 Kotlin 代码则不需要重建。

`Product → Clean Build Folder` 清的是 Xcode 的 DerivedData，**不会**重建 Rust 静态库，
所以它救不了一个过期的 `.a`。

本项目**不产出 XCFramework**。各目标通过 `privchat_sdk_ffi.def` 里的 `libraryPaths`
直接链接 `privchat-sdk/target/<triple>/release/libprivchat_sdk_ffi.a`。

## 示例

`:sample` 是共享 KMP 库模块，可安装的 Android 应用是 `:sample-androidApp`
（目录 `sample/androidApp`）。

```bash
./gradlew :sample-androidApp:installDebug
./gradlew :sample:linkDebugFrameworkIosArm64   # 供 sample/iosApp 链接的 framework
```

iOS 首次构建 `sample/iosApp` 之前，先跑一次 `:sdk:privchatCargoBuildAppleFfi`。

示例覆盖 Android 与 iOS 的主验证链路：connect → login/register → authenticate →
bootstrap → 会话列表。详见 [sample/README.md](sample/README.md)。

## 设计约束

- **只走 UniFFI async 导出。** 不使用手写 iOS 桥接（`SdkInvoker.ios.kt`、同步 helper、
  by-handle 调用）；所有 native 调用都经 UniFFI async，在 Kotlin 侧表现为 `suspend` 函数。
- **不产出 XCFramework。** 各目标在编译期直接链接 Rust 静态库。
- 公共 API 遵循 `privchat-sdk` 的契约（见
  [`CLIENT_SERVICE_FACADE_SPEC.md`](../privchat-sdk/docs/CLIENT_SERVICE_FACADE_SPEC.md)），
  并与 privchat-sdk-android、privchat-sdk-swift 保持一致。
