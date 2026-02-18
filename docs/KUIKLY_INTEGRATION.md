# Privchat SDK 与 KuiklyUI 集成指南

本指南说明如何将 privchat-sdk-kotlin 集成到基于 **KuiklyUI** 的应用中，参照 `app/` 脚手架实现 4 页面结构（登录/注册、会话列表、好友列表、会话详情）。

## 4 页面结构（与 Android 一致）

1. **登录/注册页**：服务器地址、账号、密码、登录/注册按钮
2. **会话列表页**：展示所有会话，点击进入聊天
3. **好友列表页**：展示好友列表，点击创建/进入私聊
4. **会话详情页**：聊天消息列表 + 输入框发送

底部导航在「会话」和「好友」之间切换。

## 集成步骤

### 1. 依赖配置

在 `app/shared/build.gradle.kts` 中添加：

```kotlin
implementation(project(":privchat-sdk-kotlin:shared"))
```

或通过 Maven 依赖（若已发布）：

```kotlin
implementation("com.netonstream.privchat:sdk-kotlin:0.1.0")
```

### 2. 创建 KuiklyUI 页面

参照 `app/shared/src/commonMain/kotlin/com/xxxxx/livestreaming/pages/` 的结构：

- **PrivchatLoginPage**：`@Page("PrivchatLoginPage")`，继承 `BasePager` 或 `ComposeView`，实现登录/注册表单
- **PrivchatMainTabPage**：底部 Tab（会话 | 好友），内含 `PageList` 切换 SessionsPage 和 FriendsPage
- **PrivchatSessionsPage**：`ComposeView`，展示 `channels` 列表，点击调用 `RouterModule.openPage("PrivchatChatPage", params)`
- **PrivchatFriendsPage**：`ComposeView`，展示 `friends` 列表，点击调用 `getOrCreateDirectChannel` 后 `openPage`
- **PrivchatChatPage**：`@Page("PrivchatChatPage")`，接收 `channelId`、`channelType`、`channelName` 参数，展示消息列表和输入框

### 3. 页面注册

在 KuiklyConfig 或页面注册处添加：

```
PrivchatLoginPage
PrivchatMainTabPage
PrivchatSessionsPage
PrivchatFriendsPage
PrivchatChatPage
```

### 4. 入口启动

启动时打开 `PrivchatLoginPage`：

```kotlin
KuiklyRenderActivity.start(context, "PrivchatLoginPage", JSONObject())
```

### 5. 平台适配

- **Android**：使用 `KuiklyRenderActivity` 渲染 KuiklyUI 页面
- **iOS**：使用 `KuiklyRenderViewController` 加载 KuiklyUI 页面

### 6. 数据流与状态

- 使用 `observable` / `observableList` 管理 `channels`、`friends`、`messages`
- SDK 的 `PrivchatClient` 为 suspend 函数，需在协程中调用，并通过 `acquireModule<NotifyModule>().post` 或主线程调度将结果回写到 observable

## 参考

- **app 脚手架**：`../app/` — KuiklyUI 页面结构、`BasePager`、`MainTabPage`、`RouterPage`
- **Android Sample**：`../privchat-sdk-android/sample/` — 4 页面 UI 与流程
- **privchat-rust API**：`../privchat-rust/docs/public-api-v2.md` — 新 SDK API 与流程
- **privchat-rust 架构**：`../privchat-rust/docs/architecture-spec.md` — Actor / local-first / FFI 约束
