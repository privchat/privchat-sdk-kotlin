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
    /** SDK 按群内备注 > 用户备注 > 昵称 > 可见 username > uid 解析出的唯一展示名。 */
    val displayName: String = name,
    val username: String? = null,
    val nickname: String? = null,
    val userAlias: String? = null,
    /** 账号类型；1 为系统账号，不得出现在普通群成员投影。 */
    val userType: Int = 0,
)
