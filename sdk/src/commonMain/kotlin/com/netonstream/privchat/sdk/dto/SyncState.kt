package com.netonstream.privchat.sdk.dto

enum class CoordinatorSyncPhase {
    Idle,
    Syncing,
    Synced,
    Retrying,
    FailedTerminal,
}

enum class CoordinatorReadiness {
    Disconnected,
    Authenticated,
    SyncingCritical,
    Ready,
    CriticalFailed,
}

enum class CoordinatorCriticalFailure {
    Network,
    ServerUnavailable,
    Protocol,
    Storage,
    Unknown,
}

enum class SyncRunKind {
    Bootstrap,
    Resume,
}

data class SyncState(
    val readiness: CoordinatorReadiness,
    val failure: CoordinatorCriticalFailure?,
    val retryable: Boolean,
    /** Compatibility projection. New consumers must use [readiness]. */
    val phase: CoordinatorSyncPhase,
    val runKind: SyncRunKind?,
    val attempt: UInt,
    val errorCode: UInt?,
    val message: String?,
    val updatedAtMs: Long,
)
