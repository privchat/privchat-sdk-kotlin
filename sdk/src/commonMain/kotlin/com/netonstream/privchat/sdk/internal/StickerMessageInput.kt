package com.netonstream.privchat.sdk.internal

import com.netonstream.privchat.sdk.dto.StickerMessagePayload
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import uniffi.privchat_sdk_ffi.NewMessage

internal fun stickerMessageInput(
    channelId: ULong,
    channelType: Int,
    fromUid: ULong,
    payload: StickerMessagePayload,
): NewMessage {
    require(payload.stickerId.isNotBlank()) { "stickerId is required" }
    require(payload.imageUrl.startsWith("https://") || payload.imageUrl.startsWith("http://")) {
        "published imageUrl must be absolute HTTP(S)"
    }
    val metadata = buildJsonObject {
        put("sticker_id", payload.stickerId)
        put("image_url", payload.imageUrl)
    }
    return NewMessage(
        channelId = channelId, channelType = channelType, fromUid = fromUid,
        messageType = 6, content = "", searchableWord = "", setting = 0,
        extra = metadata.toString(), mimeType = null, mediaDownloaded = false, thumbStatus = 3,
    )
}
