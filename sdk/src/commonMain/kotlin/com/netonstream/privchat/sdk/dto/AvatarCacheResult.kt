package com.netonstream.privchat.sdk.dto

/**
 * 显式头像 re-cache 结果（CLIENT_GLOBAL_STATE §4.3 P2）。
 *
 * [avatarLocalPath] 是本地展示主字段（UI 优先渲染）；[avatarCachedUrl] = 本地文件对应的远程版本，
 * 作为 freshness 判据（与最新 remote avatar 不等 ⇒ 需重新 re-cache）。
 */
data class AvatarCacheResult(
    val userId: ULong,
    val avatarLocalPath: String,
    val avatarCachedUrl: String,
)
