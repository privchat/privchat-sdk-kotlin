package om.netonstream.privchat.sdk.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val presenceEventJson = Json { ignoreUnknownKeys = true }

@Serializable
data class PresenceSnapshotDto(
    @SerialName("user_id")
    val userId: ULong,
    @SerialName("is_online")
    val isOnline: Boolean,
    @SerialName("last_seen_at")
    val lastSeenAt: Long,
    @SerialName("device_count")
    val deviceCount: UInt,
    val version: ULong,
)

@Serializable
data class PresenceChangedNotification(
    @SerialName("user_id")
    val userId: ULong,
    val version: ULong,
    val snapshot: PresenceSnapshotDto,
)

fun PresenceChangedNotification.toPresenceEntry(): PresenceEntry = PresenceEntry(
    userId = snapshot.userId,
    isOnline = snapshot.isOnline,
    lastSeen = snapshot.lastSeenAt,
    deviceType = null,
)

fun decodePresenceChangedNotification(payload: ByteArray?): PresenceChangedNotification? {
    val raw = payload?.decodeToString()?.takeIf { it.isNotBlank() } ?: return null
    return runCatching {
        presenceEventJson.decodeFromString<PresenceChangedNotification>(raw)
    }.getOrNull()
}
