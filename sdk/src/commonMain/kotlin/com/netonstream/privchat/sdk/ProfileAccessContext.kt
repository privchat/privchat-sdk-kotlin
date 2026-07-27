package com.netonstream.privchat.sdk

/**
 * 资料查询的**来源上下文**（PROFILE_VISIBILITY_SPEC §2.5 来源校验矩阵）。
 *
 * 取代原来的 `(source: String, sourceId: String?)` 二元组：字符串来源 + 可空 channelId
 * 无法在类型上阻止「没有上下文就随便声称是好友」，生产上因此每天产生 17.8 万次
 * `Forbidden: Friend source claimed but users are not friends`（2026-07-26 日志）。
 *
 * 每种来源服务端都会做**真伪校验**：
 * - `Conversation` / `Group`：viewer 与 target 必须都属于该频道/群；
 * - `Friend`：必须真的是好友；
 * - `SelfLookup`：本人，无需来源。
 *
 * 因此客户端只能声明**自己确实处在**的上下文；拿不到上下文时用 [Unknown]，
 * SDK 会跳过远程详情拉取（返回本地投影），而不是伪造一个必被拒的来源。
 */
sealed interface ProfileAccessContext {

    /** 本人查本人。 */
    data object SelfLookup : ProfileAccessContext

    /** 在某个会话里查看对方（DM 或群会话）。 */
    data class Conversation(val channelId: ULong) : ProfileAccessContext

    /** 在群成员列表里查看成员。 */
    data class Group(val groupId: ULong) : ProfileAccessContext

    /** 确认对方是好友时才可用。 */
    data object Friend : ProfileAccessContext

    /** 搜索结果里点开资料，需带搜索会话 id。 */
    data class Search(val searchSessionId: String) : ProfileAccessContext

    /**
     * 没有可声明的合法来源。**不会**发起远程详情请求——昵称/头像这类公开字段
     * 由 user 实体增量同步维护，不需要、也不应该靠 detail 接口刷。
     */
    data object Unknown : ProfileAccessContext

    /** wire 层来源名（与服务端 `UserDetailSource` 对齐）；[Unknown] 无 wire 表示。 */
    val wireSource: String?
        get() = when (this) {
            SelfLookup -> "self"
            is Conversation -> "conversation"
            is Group -> "group"
            Friend -> "friend"
            is Search -> "search"
            Unknown -> null
        }

    /** wire 层 source_id；服务端据此定位频道/群/搜索会话。 */
    fun wireSourceId(targetUserId: ULong): String? = when (this) {
        SelfLookup -> targetUserId.toString()
        is Conversation -> channelId.toString()
        is Group -> groupId.toString()
        Friend -> targetUserId.toString()
        is Search -> searchSessionId
        Unknown -> null
    }
}
