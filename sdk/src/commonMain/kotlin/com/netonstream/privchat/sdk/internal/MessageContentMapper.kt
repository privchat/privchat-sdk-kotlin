package com.netonstream.privchat.sdk.internal

import com.netonstream.privchat.sdk.dto.MessageContent
import com.netonstream.privchat.sdk.dto.MessageContentKind
import com.netonstream.privchat.sdk.dto.MessageContentRef
import com.netonstream.privchat.sdk.dto.MessageTextEntity
import com.netonstream.privchat.sdk.dto.MessageTextEntityType
import uniffi.privchat_sdk_ffi.MessageContentBody

internal fun MessageContentBody.toSdkMessageContent() = MessageContent(
    kind = when (kind) {
        "text" -> MessageContentKind.Text
        "voice" -> MessageContentKind.Voice
        "image" -> MessageContentKind.Image
        "video" -> MessageContentKind.Video
        "file" -> MessageContentKind.File
        "system" -> MessageContentKind.System
        "sticker" -> MessageContentKind.Sticker
        "contact" -> MessageContentKind.Contact
        "location" -> MessageContentKind.Location
        "link" -> MessageContentKind.Link
        "forward" -> MessageContentKind.Forward
        "red_packet" -> MessageContentKind.RedPacket
        "money_transfer" -> MessageContentKind.MoneyTransfer
        else -> MessageContentKind.Unknown
    },
    text = text,
    entities = entities.map { entity ->
        MessageTextEntity(
            type = when (entity.kind) {
                "mention" -> MessageTextEntityType.Mention
                "url" -> MessageTextEntityType.Url
                "phone" -> MessageTextEntityType.Phone
                "number" -> MessageTextEntityType.Number
                "email" -> MessageTextEntityType.Email
                else -> MessageTextEntityType.Unknown
            },
            start = entity.start.toInt(),
            end = entity.end.toInt(),
            text = entity.text,
            value = entity.value,
            userId = entity.userId,
        )
    },
    replyToServerMessageId = replyToMessageId,
    mentionedUserIds = mentionedUserIds,
    attachmentUrl = attachmentUrl,
    attachmentFileId = attachmentFileId,
    thumbnailUrl = thumbnailUrl,
    thumbnailFileId = thumbnailFileId,
    fileName = fileName,
    fileSize = fileSize,
    duration = duration,
    width = width,
    height = height,
    latitude = latitude,
    longitude = longitude,
    coordinateSystem = coordinateSystem,
    locationName = locationName,
    address = address,
    poiId = poiId,
    poiSource = poiSource,
    linkUrl = linkUrl,
    linkTitle = linkTitle,
    linkDescription = linkDescription,
    contactUserId = contactUserId,
    contactName = contactName,
    contactAvatarUrl = contactAvatarUrl,
    systemTemplate = systemTemplate,
    systemRefs = systemRefs.map { MessageContentRef(it.kind, it.targetId, it.text) },
    moneyRefId = moneyRefId,
    moneyTitle = moneyTitle,
    moneySummary = moneySummary,
    moneyStatus = moneyStatus,
    moneyAmountText = moneyAmountText,
    moneyScene = moneyScene,
    moneyType = moneyType,
)
