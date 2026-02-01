package io.privchat.sdk.dto

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
)
