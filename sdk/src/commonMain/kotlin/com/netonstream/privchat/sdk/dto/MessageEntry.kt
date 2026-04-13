package om.netonstream.privchat.sdk.dto

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
    /** 协议标准消息类型（privchat-protocol ContentMessageType） */
    val messageType: Int = 0,
    /** 协议标准 extra 原始 JSON 字符串 */
    val extra: String = "",
    /** 是否已撤回，由 message_extra.revoke 驱动 */
    val isRevoked: Boolean = false,
    /** 撤回操作者 uid */
    val revoker: ULong? = null,
)
