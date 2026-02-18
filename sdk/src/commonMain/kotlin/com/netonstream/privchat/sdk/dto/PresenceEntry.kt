package om.netonstream.privchat.sdk.dto

data class PresenceEntry(
    val userId: ULong,
    val isOnline: Boolean,
    val lastSeen: Long?,
    val deviceType: String?,
)
