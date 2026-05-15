package com.netonstream.privchat.sdk.dto

/**
 * F-sync.3: 好友申请视图，承载 [FriendRequestPage] Received/Sent 列表所需全部字段。
 *
 * 数据源：本地 friendships 投影（`PrivchatClient.listFriendRequests`），由 server
 * 通过 entity sync 多端分发。每行天然就是单一对端：
 * - `isOutgoing = true` 表示这是我发出的申请，[userId] = 被申请人；
 * - `isOutgoing = false` 表示这是我收到的申请，[userId] = 申请人。
 *
 * 字段语义对齐 server `friendships`：
 * - [status]：0=pending / 1=accepted / 2=blocked / 3=rejected / 4=recalled / 5=expired
 *   （但 1=accepted / 2=blocked 不应出现在 list_friend_requests 结果中）。
 * - [message]：申请附言；可为 null/空。
 * - [source] / [sourceId]：来源类型 + ID（search / phone / qrcode / group /
 *   card_share / conversation），UI 用于显示「通过 XXX 添加」。
 */
data class FriendRequestEntry(
    val userId: ULong,
    val username: String,
    val nickname: String?,
    val avatarUrl: String?,
    val status: Short,
    val isOutgoing: Boolean,
    val message: String?,
    val source: String?,
    val sourceId: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
