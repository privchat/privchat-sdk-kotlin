package com.netonstream.privchat.sdk.internal

import com.netonstream.privchat.sdk.dto.StickerMessagePayload
import kotlinx.serialization.json.*
import kotlin.test.*

class StickerMessageInputTest {
    @Test fun typedProjectionRetainsIdentitySeparateFromUrl() {
        val body = uniffi.privchat_sdk_ffi.MessageContentBody(
            kind = "sticker", text = "", entities = emptyList(), mentionedUserIds = emptyList(),
            systemRefs = emptyList(), stickerId = "9007199254740993", attachmentUrl = "https://cdn.example/image",
        ).toSdkMessageContent()
        assertEquals(com.netonstream.privchat.sdk.dto.MessageContentKind.Sticker, body.kind)
        assertEquals("9007199254740993", body.stickerId)
        assertEquals("https://cdn.example/image", body.attachmentUrl)
    }

    @Test fun sendsTypeSixAndStandardMetadataWithoutTextDisguise() {
        val payload = StickerMessagePayload("9223372036854775807", "https://api.example/app/sticker/resource/get/9")
        val message = stickerMessageInput(41u, 2, 8u, payload)
        assertEquals(6, message.messageType)
        assertEquals("", message.content)
        assertEquals(41uL, message.channelId)
        assertEquals(8uL, message.fromUid)
        val extra = Json.parseToJsonElement(message.extra).jsonObject
        assertEquals(payload.stickerId, extra["sticker_id"]?.jsonPrimitive?.content)
        assertEquals(payload.imageUrl, extra["image_url"]?.jsonPrimitive?.content)
    }

    @Test fun rejectsMissingIdentityAndNonPublicUrl() {
        assertFailsWith<IllegalArgumentException> { stickerMessageInput(1u, 1, 2u, StickerMessagePayload(" ", "https://api.example/s")) }
        assertFailsWith<IllegalArgumentException> { stickerMessageInput(1u, 1, 2u, StickerMessagePayload("1", "file:///tmp/s")) }
        assertFailsWith<IllegalArgumentException> { stickerMessageInput(1u, 1, 2u, StickerMessagePayload("1", "/app/sticker/resource/get/1")) }
    }

    @Test fun metadataEscapesRatherThanInterpolatesInput() {
        val payload = StickerMessagePayload("legacy\"id", "https://api.example/a?x=\"quoted\"")
        val extra = Json.parseToJsonElement(stickerMessageInput(1u, 1, 2u, payload).extra).jsonObject
        assertEquals(payload.stickerId, extra["sticker_id"]?.jsonPrimitive?.content)
        assertEquals(payload.imageUrl, extra["image_url"]?.jsonPrimitive?.content)
    }
}
