package com.netonstream.privchat.sdk.dto

/**
 * Bot 关注结果（spec `02-server/SERVICE_ACCOUNT_FOLLOW_SPEC` §2.2）。
 *
 * 关注成功后即可用 [channelId] 调 [com.netonstream.privchat.sdk.PrivchatClient.subscribeChannel]
 * 和 [com.netonstream.privchat.sdk.PrivchatClient.transfer]（如 `bot/menu/get`）。
 */
data class BotFollowOutcome(
    val botUserId: ULong,
    val channelId: ULong,
    /** v1.0 固定 2 (Bot)；保留以兼容未来扩展。 */
    val accountUserType: Int,
    val followed: Boolean,
    /** `true` = 新建关系或从 unfollowed 复活；`false` = 已 followed 幂等复用。 */
    val created: Boolean,
)

/**
 * Bot 取消关注结果。
 *
 * unfollow **不**删除 channel / 历史 / application 业务行；server 端只切 status=0。
 */
data class BotUnfollowOutcome(
    val botUserId: ULong,
    /** 已存在的 direct channel id（保留，**不**删除）；`0` 表示原本就没关注过。 */
    val channelId: ULong,
    /** `true` = 已取消关注；`false` = 原本就没关注，no-op。 */
    val unfollowed: Boolean,
)
