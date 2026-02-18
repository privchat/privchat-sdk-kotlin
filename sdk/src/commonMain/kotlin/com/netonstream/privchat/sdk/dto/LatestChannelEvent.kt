package om.netonstream.privchat.sdk.dto

data class LatestChannelEvent(
    val eventType: String,
    val content: String,
    val timestamp: ULong,
)
