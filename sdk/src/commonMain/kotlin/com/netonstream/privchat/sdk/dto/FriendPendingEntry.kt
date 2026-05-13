package com.netonstream.privchat.sdk.dto

data class FriendPendingEntry(
    val fromUserId: ULong,
    val user: SearchedUserDto,
    val message: String?,
    val createdAt: ULong,
)

data class SearchedUserDto(
    val userId: ULong,
    val username: String,
    val nickname: String,
    val avatarUrl: String?,
    val userType: Short,
    val searchSessionId: ULong,
    val isFriend: Boolean,
    val canSendMessage: Boolean,
    /** 是否已关注（仅 userType=2 (Bot) 有意义；非 bot 永远 false） */
    val isFollow: Boolean = false,
)
