package com.netonstream.privchat.sdk.config

import com.netonstream.privchat.sdk.PrivchatClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ComponentConfig(
    val schemaVersion: Int,
    val revision: String,
    val maxAgeSeconds: Int,
    val components: Components,
)
@Serializable
data class Components(val rtc: RtcComponent)
@Serializable
data class RtcComponent(
    val schemaVersion: Int,
    val enabled: Boolean,
    val livekitUrl: String,
    val peerEnabled: Boolean,
    val allowTurn: Boolean,
)

private val componentJson = Json { ignoreUnknownKeys = true }

/** Read-only authenticated discovery. Business credentials still come from call/join. */
suspend fun PrivchatClient.getComponentConfig(): Result<ComponentConfig> =
    callRpc("config/get", "{}").mapCatching { componentJson.decodeFromString<ComponentConfig>(it) }
