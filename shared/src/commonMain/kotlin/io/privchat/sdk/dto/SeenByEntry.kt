package io.privchat.sdk.dto

/** 已读用户条目 - 与 SDK_API_CONTRACT 对齐 */
data class SeenByEntry(
    val userId: ULong,
    val readAt: ULong,
)
