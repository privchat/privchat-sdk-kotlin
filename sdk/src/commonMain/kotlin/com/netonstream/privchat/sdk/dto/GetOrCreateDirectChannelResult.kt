package om.netonstream.privchat.sdk.dto

data class GetOrCreateDirectChannelResult(
    val channelId: ULong,
    val created: Boolean,
)
