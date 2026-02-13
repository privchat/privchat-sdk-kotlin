package io.privchat.sdk.dto

data class SdkEventEnvelope(
    val sequenceId: ULong,
    val timestampMs: Long,
    val event: SdkEventPayload,
)

data class SdkEventPayload(
    val type: String,
    val fromState: String? = null,
    val toState: String? = null,
    val fromNetworkHint: String? = null,
    val toNetworkHint: String? = null,
    val userId: ULong? = null,
    val entityType: String? = null,
    val entityId: String? = null,
    val scope: String? = null,
    val deleted: Boolean? = null,
    val channelId: ULong? = null,
    val channelType: Int? = null,
    val messageId: ULong? = null,
    val reason: String? = null,
    val status: Int? = null,
    val serverMessageId: ULong? = null,
    val isRead: Boolean? = null,
    val isTyping: Boolean? = null,
    val kind: String? = null,
    val action: String? = null,
    val queueIndex: ULong? = null,
    val queued: ULong? = null,
    val applied: ULong? = null,
    val droppedDuplicates: ULong? = null,
)
