package io.privchat.sdk.dto

data class SyncStateEntry(
    val channelId: ULong,
    val channelType: Int,
    val localPts: ULong,
    val serverPts: ULong,
    val needsSync: Boolean,
    val lastSyncAt: Long?,
)
