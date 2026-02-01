package io.privchat.sdk.dto

/** 反应芯片 - 与 SDK_API_CONTRACT 对齐 */
data class ReactionChip(
    val emoji: String,
    val userIds: List<ULong>,
    val count: ULong,
)
