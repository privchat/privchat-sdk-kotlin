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

/**
 * 上滑加载更早历史一页（SDK-HISTORY-5，MESSAGE_HISTORY spec §2.5/§2.5.1）。
 * [messages]=本次回填的更早消息（已 upsert 本地库、显示序 DESC），UI prepend 到时间线头部；
 * [hasMoreBefore]=服务端是否还有更早（**来自 SDK 持久化 gap 态**，false=到顶，UI 停止上滑加载）。
 */
data class MessageHistoryPage(
    val messages: List<MessageEntry>,
    val hasMoreBefore: Boolean,
    /**
     * 本次是否真的向服务端取过数据（仅 openConversation 会置 true）。
     *
     * 纯诊断用，不参与渲染。存在的理由：空列表有两种成因——「服务端确实没有」和
     * 「压根没去问」——不区分的话，一个没生效的补历史修复看起来和一个真空会话
     * 一模一样，验收时无从判断。
     */
    val fetchedFromServer: Boolean = false,
)
