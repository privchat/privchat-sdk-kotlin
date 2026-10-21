package com.netonstream.privchat.sdk.dto.call

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/** Structured System-message summary; never a local synthetic timeline message. */
@Serializable
data class CallHistory(
    val event: String,
    @SerialName("call_id") val callId: ULong,
    @SerialName("media_kind") val mediaKind: CallMediaKind,
    @SerialName("duration_ms") val durationMs: Long,
    @SerialName("end_reason") val endReason: CallEndReason? = null,
)

private val historyJson = Json { ignoreUnknownKeys = true }

fun parseCallHistory(content: String?): CallHistory? = content?.let {
    runCatching { historyJson.decodeFromString<CallHistory>(it) }.getOrNull()
        ?.takeIf { history -> history.event == "call.ended" && history.durationMs >= 0 }
}
