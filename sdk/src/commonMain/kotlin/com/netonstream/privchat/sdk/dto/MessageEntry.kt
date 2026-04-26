package com.netonstream.privchat.sdk.dto

/** 消息状态 - 与 SDK_API_CONTRACT 对齐 */
enum class MessageStatus { Pending, Sending, Sent, Failed, Read }

/** 消息条目 - 与 SDK_API_CONTRACT 对齐 */
data class MessageEntry(
    val id: ULong,
    val serverMessageId: ULong?,
    val localMessageId: ULong?,
    val channelId: ULong,
    val channelType: Int,
    val fromUid: ULong,
    val content: String,
    val status: MessageStatus,
    val timestamp: ULong,
    /** 协议标准消息类型（privchat-protocol ContentMessageType） */
    val messageType: Int = 0,
    /** 协议标准 extra 原始 JSON 字符串 */
    val extra: String = "",
    /** 是否已撤回，由 message_extra.revoke 驱动 */
    val isRevoked: Boolean = false,
    /** 撤回操作者 uid */
    val revoker: ULong? = null,
    /** 媒体 MIME 类型（从 content/extra JSON 提取） */
    val mimeType: String? = null,
    /** 媒体文件是否已下载到本地 */
    val mediaDownloaded: Boolean = false,
    /** 缩略图状态：0=missing, 1=ready, 2=failed, 3=none（协议层无缩略图） */
    val thumbStatus: Int = 0,
    /** 本地缩略图路径（规范路径 thumb.webp），thumbStatus==1 时有值；thumbStatus==3 时永远为 null */
    val localThumbnailPath: String? = null,
    /** 本地媒体文件路径（规范路径 payload.{ext}） */
    val localMediaPath: String? = null,
    /** 是否已送达（对端在线 session 已 ack） */
    val delivered: Boolean = false,
    /** 消息在所属 channel timeline 内的序号，用于 Read 投影 */
    val pts: ULong? = null,
    /** 引用消息的 server_message_id（REPLY_SPEC），为 null 表示非回复消息 */
    val replyToServerMessageId: String? = null,
    /** @ 提及的用户 ID 列表（MENTION_SPEC），空列表表示无 @ */
    val mentionedUserIds: List<ULong> = emptyList(),
)
