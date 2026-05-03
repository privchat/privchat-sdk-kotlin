package com.netonstream.privchat.sdk.dto

/**
 * `PrivchatClient.refreshAccessToken` 返回结果（TOKEN_REFRESH_SPEC v1.0 §5）。
 *
 * - [accessToken]：新的 access token，业务层应立即调 `authenticate(uid, accessToken, deviceId)` 应用
 * - [refreshToken]：B1 默认不轮换，服务端可能省略；非空时业务层应替换本地存的 refresh_token
 * - [expiresAt]：access token 过期时刻（Unix 毫秒）
 * - [refreshExpiresAt]：refresh token 过期时刻；B1 可能不下发
 */
data class RefreshAccessTokenResult(
    val accessToken: String,
    val refreshToken: String?,
    val expiresAt: ULong,
    val refreshExpiresAt: ULong?,
)
