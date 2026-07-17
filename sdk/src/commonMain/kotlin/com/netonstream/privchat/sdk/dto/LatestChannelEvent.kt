package com.netonstream.privchat.sdk.dto

/**
 * 会话列表里"最近一条事件"快照。
 *
 * Preview rendering consumes [body]. Raw storage payload is retained only for migration.
 */
data class LatestChannelEvent(
    val eventType: String,
    @Deprecated("Raw storage payload is SDK-internal; render body instead")
    val content: String,
    val body: MessageContent = MessageContent(MessageContentKind.Unknown, content),
    val timestamp: ULong,
    /**
     * 最后一条消息的 ContentMessageType 整型值。null 表示 SDK 尚未拿到具体类型
     * （兼容旧数据 / 同步未就绪），UI 层按 UNKNOWN 处理。
     */
    val messageType: Int? = null,
    /** 最后一条消息是否已被撤回。 */
    val isRevoked: Boolean = false,
)
