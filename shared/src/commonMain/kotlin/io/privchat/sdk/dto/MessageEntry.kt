package io.privchat.sdk.dto

/** 消息状态 - 与 SDK_API_CONTRACT 对齐 */
enum class MessageStatus { Pending, Sending, Sent, Failed, Read }

/** 消息条目 - 与 SDK_API_CONTRACT 对齐 */
data class MessageEntry(
    val id: ULong,
    val serverMessageId: ULong?,
    val localMessageId: ULong?,
    val channelId: ULong,
    val channelType: Int,
    val fromUid: ULong,
    val content: String,
    val status: MessageStatus,
    val timestamp: ULong,
)
