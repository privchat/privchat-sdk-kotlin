package io.privchat.sdk.dto

data class FriendEntry(
    val userId: ULong,
    val username: String,
    val nickname: String?,
    val avatarUrl: String?,
    val userType: Short,
    val status: String,
    val addedAt: Long,
    val remark: String?,
)
