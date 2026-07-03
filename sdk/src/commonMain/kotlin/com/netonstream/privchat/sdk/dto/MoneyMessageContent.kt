package com.netonstream.privchat.sdk.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * PrivChat Money Message 内容（标准 IM 能力，RP-ferry）。
 *
 * 这是消息 payload —— **只放引用 + 展示快照**，不是资金真相。`amountText` 等是发送时刻的
 * 快照，可能过期；客户端交互（领取/查看）前一律以 platform/application 的订单/ledger 为准。
 * SDK 只负责 content 的编解码与收发，不处理余额/扣款/入账/退款。
 */
@Serializable
data class RedPacketContent(
    /** platform 红包订单 id（字符串承载，避免 JS Long 精度问题）。 */
    val redPacketId: String,
    /** dm / group */
    val scene: String = "dm",
    val title: String = "",
    val summary: String = "",
    /** active / finished / expired 等展示态快照。 */
    val status: String = "active",
    val senderUserId: Long = 0,
    val currency: String = "CNY",
    /** 展示用金额文本（如「¥8.88」）——非资金依据。 */
    val amountText: String? = null,
    val totalCount: Int? = null,
    val openedCount: Int? = null,
    val expiredAt: Long? = null,
) {
    fun encode(): String = json.encodeToString(serializer(), this)

    companion object {
        internal val json = Json { ignoreUnknownKeys = true; encodeDefaults = true; explicitNulls = false }
        fun decodeOrNull(content: String): RedPacketContent? =
            runCatching { json.decodeFromString(serializer(), content) }.getOrNull()
    }
}

@Serializable
data class MoneyTransferContent(
    /** platform 转账订单 id。 */
    val transferId: String,
    val title: String = "",
    val summary: String = "",
    /** success / refunded 等展示态快照。 */
    val status: String = "success",
    val fromUserId: Long = 0,
    val toUserId: Long = 0,
    val currency: String = "CNY",
    /** 展示用金额文本——非资金依据。 */
    val amountText: String = "",
    val createdAt: Long? = null,
) {
    fun encode(): String = RedPacketContent.json.encodeToString(serializer(), this)

    companion object {
        fun decodeOrNull(content: String): MoneyTransferContent? =
            runCatching { RedPacketContent.json.decodeFromString(serializer(), content) }.getOrNull()
    }
}
