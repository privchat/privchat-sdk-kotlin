package com.netonstream.privchat.sdk.dto

data class ChannelListEntry(
    val channelId: ULong,
    val channelType: Int,
    val name: String,
    val lastTs: ULong,
    val notifications: UInt,
    val messages: UInt,
    val mentions: UInt,
    val markedUnread: Boolean,
    val isFavourite: Boolean,
    val isLowPriority: Boolean,
    val avatarUrl: String?,
    val isDm: Boolean,
    val isEncrypted: Boolean,
    val memberCount: UInt,
    val topic: String?,
    val latestEvent: LatestChannelEvent?,
    val peerUserId: ULong? = null,
    /** DM 对端账号类型(本地 user 实体在场时带出;null=未知)。显示名单点判定依据。 */
    val peerUserType: Int? = null,
    /** DM 对端 username(配合语言包按 username 精确匹配)。 */
    val peerUsername: String? = null,
)
