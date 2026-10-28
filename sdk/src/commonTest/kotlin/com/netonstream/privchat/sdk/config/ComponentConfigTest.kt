package com.netonstream.privchat.sdk.config

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ComponentConfigTest {
    @Test fun decodePublicComponentShape() {
        val view = Json { ignoreUnknownKeys = true }.decodeFromString<ComponentConfig>(
            """{"schemaVersion":1,"revision":"opaque","maxAgeSeconds":300,"components":{"rtc":{"schemaVersion":1,"enabled":true,"livekitUrl":"wss://rtc.example.invalid","peerEnabled":true,"allowTurn":false}}}"""
        )
        assertEquals("opaque", view.revision)
        assertEquals(300, view.maxAgeSeconds)
        assertFalse(view.components.rtc.allowTurn)
    }
}
