package com.netonstream.privchat.sdk.dto

data class UserEntry(
    val userId: ULong,
    val username: String,
    val nickname: String?,
    val avatarUrl: String?,
    val userType: Short,
    val isFriend: Boolean,
    val canSendMessage: Boolean,
    val searchSessionId: ULong?,
    val isOnline: Boolean?,
    /** 是否已关注（仅 userType=2 (Bot) 有意义；非 bot 永远 false） */
    val isFollow: Boolean = false,
)
