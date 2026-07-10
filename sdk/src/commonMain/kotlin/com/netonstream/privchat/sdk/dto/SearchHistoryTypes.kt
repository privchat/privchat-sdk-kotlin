package com.netonstream.privchat.sdk.dto

/**
 * 云端历史搜索命中（MESSAGE_HISTORY spec §4）。
 *
 * snippet 投影——**不落本地 message 表**；点击后调
 * [com.netonstream.privchat.sdk.PrivchatClient.getMessagesAround]
 * 拉完整上下文（SDK 内部回填本地库），UI 再从本地渲染并定位 anchor。
 */
data class SearchHistoryHit(
    val channelId: ULong,
    val messageId: ULong,
    val senderUserId: ULong,
    /** 毫秒时间戳 */
    val createdAt: Long,
    val messageType: String,
    val snippet: String,
    /** 相对 snippet 的字符偏移 [start, end)，供高亮 */
    val highlightRanges: List<Pair<Int, Int>>,
)

/**
 * 搜索分页结果。[nextCursor] 原样回传下一页请求；null = 到底。
 *
 * 服务端限频 300ms/user：UI 必须 debounce 300–500ms、忽略过期 in-flight
 * 结果、query < 2 字符不发起远程。
 */
data class SearchHistoryPage(
    val hits: List<SearchHistoryHit>,
    val nextCursor: String?,
)

/** around 上下文里的一条完整消息（已由 SDK 回填本地库） */
data class AroundContextEntry(
    val messageId: ULong,
    val channelId: ULong,
    val senderId: ULong,
    val content: String,
    val messageType: String,
    /** 毫秒时间戳 */
    val timestamp: ULong,
    /** per-channel pts；本地排序权威 = (pts, server_message_id) */
    val messageSeq: Long?,
    val revoked: Boolean,
)

/**
 * jump-to-message 上下文（spec §5）。before/anchor/after 均为完整消息且
 * **已回填本地库**——UI 应以本地库为渲染真源，用 [anchor] 定位/高亮。
 */
data class MessagesAroundPage(
    val beforeMessages: List<AroundContextEntry>,
    val anchor: AroundContextEntry,
    val afterMessages: List<AroundContextEntry>,
    val hasMoreBefore: Boolean,
    val hasMoreAfter: Boolean,
)
