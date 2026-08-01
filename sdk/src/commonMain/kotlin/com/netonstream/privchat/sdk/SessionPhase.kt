package com.netonstream.privchat.sdk

/**
 * Core 会话阶段的**无损**投影，与 Rust `SessionState` 一一对应。
 *
 * 为什么不复用 [ConnectionState]：那个枚举把 `CONNECTED` / `LOGGED_IN` / `AUTHENTICATED`
 * 三者都折叠成 `Connected`。对「能不能连上」这个问题它够用，对「能不能发业务 RPC」
 * 就不够——Rust 侧写得很清楚：`Connected/LoggedIn` 都可能跑在服务端尚未授权的通道上
 * （重连刚握好 TCP、ConnAuth 还没回），此时发 RPC 只会拿到 10000。
 *
 * 谁需要精确值：连接横幅（只有 [Authenticated] 才能撤下「重连中」）、出站队列闸门、
 * 任何「已就绪」的判定。用折叠后的值做这些判断，会在「连上但没认证」的窗口里
 * 谎报就绪。
 */
enum class SessionPhase {
    /** 无 transport。冷启动初值，也是断线后的落点。 */
    New,

    /** transport 已建立，但既未登录也未鉴权。**不可**发业务 RPC。 */
    Connected,

    /** 已登录，鉴权未完成。**不可**发业务 RPC。 */
    LoggedIn,

    /** 已鉴权——唯一可以发业务 RPC、也是唯一可以撤下重连横幅的状态。 */
    Authenticated,

    /** 认证终局：不会自动重连，需宿主按 forced_logout 处理。 */
    Terminated,

    /** 客户端已关停。 */
    Shutdown,
}
