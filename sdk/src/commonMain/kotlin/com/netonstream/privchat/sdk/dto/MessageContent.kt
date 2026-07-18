package com.netonstream.privchat.sdk.dto

enum class MessageContentKind {
    Text, Voice, Image, Video, File, System, Sticker, Contact, Location, Link,
    Forward, RedPacket, MoneyTransfer, Unknown,
}

enum class MessageTextEntityType { Mention, Url, Phone, Number, Email, Unknown }

data class MessageTextEntity(
    val type: MessageTextEntityType,
    /** UTF-16 offsets, matching Kotlin String indices on Android/iOS. */
    val start: Int,
    val end: Int,
    val text: String,
    val value: String,
    val userId: ULong? = null,
)

data class MessageContentRef(
    val type: String,
    val targetId: String? = null,
    val text: String? = null,
)

/**
 * Canonical SDK projection of message payloads.
 *
 * Applications render this model and must not parse wire/storage JSON from MessageEntry.content.
 */
data class MessageContent(
    val kind: MessageContentKind,
    val text: String,
    val entities: List<MessageTextEntity> = emptyList(),
    val replyToServerMessageId: String? = null,
    val mentionedUserIds: List<ULong> = emptyList(),
    val attachmentUrl: String? = null,
    val attachmentFileId: ULong? = null,
    val thumbnailUrl: String? = null,
    val thumbnailFileId: ULong? = null,
    val fileName: String? = null,
    val fileSize: Long? = null,
    val duration: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val coordinateSystem: String? = null,
    val locationName: String? = null,
    val address: String? = null,
    val poiId: String? = null,
    val poiSource: String? = null,
    val linkUrl: String? = null,
    val linkTitle: String? = null,
    val linkDescription: String? = null,
    val contactUserId: ULong? = null,
    val contactName: String? = null,
    val contactAvatarUrl: String? = null,
    val systemTemplate: String? = null,
    val systemRefs: List<MessageContentRef> = emptyList(),
    val moneyRefId: String? = null,
    val moneyTitle: String? = null,
    val moneySummary: String? = null,
    val moneyStatus: String? = null,
    val moneyAmountText: String? = null,
    val moneyScene: String? = null,
    val moneyType: Int? = null,
)
