package com.netonstream.privchat.sdk.dto

/**
 * Login / register / refresh 等认证 RPC 返回值。
 *
 * **业务层是 token 的逻辑持有者**：从本结构里捕获 [refreshToken] 存到自己的安全存储，
 * catch 10002 时调 [com.netonstream.privchat.sdk.PrivchatClient.refreshAccessToken] 续期。
 * SDK 不持久化 refresh_token——这样适配第一方 / 第三方业务后台两种 login 流程。
 *
 * 详见 TOKEN_REFRESH_SPEC v1.0 §3 / §4。
 */
data class AuthResult(
    val userId: ULong,
    val token: String,
    val refreshToken: String? = null,
    val deviceId: String = "",
    val expiresAt: ULong = 0u,
)
