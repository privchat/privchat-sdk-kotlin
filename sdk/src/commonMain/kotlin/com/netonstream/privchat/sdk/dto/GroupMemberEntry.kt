package com.netonstream.privchat.sdk.dto

data class GroupMemberEntry(
    val userId: ULong,
    val channelId: ULong,
    val channelType: Int,
    val name: String,
    val remark: String,
    val avatar: String,
    val role: Int,
    val status: Int,
    val inviteUserId: ULong,
    /** 入群时间（epoch ms）；群九宫格头像按此升序取最早 9 人（微信规则）。0=未知。 */
    val joinedAt: Long = 0L,
)
