package io.privchat.sdk.dto

enum class SyncPhase { Idle, Running, BackingOff, Error }

data class SyncStatus(
    val phase: SyncPhase,
    val message: String?,
)
