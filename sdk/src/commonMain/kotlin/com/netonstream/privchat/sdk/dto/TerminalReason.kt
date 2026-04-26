package com.netonstream.privchat.sdk.dto

/**
 * ForcedLogout 触发来源。对应 SDK 核心层 `ForcedLogoutSource`：
 * - [ConnectAuth]：CONNECT 阶段 token 被终审不可自愈
 * - [RpcAuth]：会话期 RPC 认证被拒
 * - [Manual]：宿主主动调用 authenticate 并被 terminal 拒绝；UI 不应再弹错误提示
 */
enum class ForcedLogoutSource {
    ConnectAuth,
    RpcAuth,
    Manual,
}

/**
 * 认证终局快照。用于冷启动时判断是否应阻止按旧 token 自动重连，
 * 也用于问题排查（code/message/at_ms）。`Connect` 成功后 SDK 会清空该记录。
 */
data class TerminalReason(
    /**
     * 对应 `privchat_protocol::ErrorCode` 的 u32 码；0 表示服务端未携带错误码。
     * 常见值：10001 InvalidToken / 10002 TokenExpired / 10003 TokenRevoked /
     * 10004 PermissionDenied / 10005 SessionExpired / 10007 UserBanned。
     */
    val code: UInt,
    val message: String,
    val source: ForcedLogoutSource,
    /** 记录时刻（Unix ms）。 */
    val atMs: Long,
)
