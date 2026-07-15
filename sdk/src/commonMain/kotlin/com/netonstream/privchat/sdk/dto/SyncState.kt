package com.netonstream.privchat.sdk.dto

enum class CoordinatorSyncPhase {
    Idle,
    Syncing,
    Synced,
    Retrying,
    FailedTerminal,
}

enum class SyncRunKind {
    Bootstrap,
    Resume,
}

data class SyncState(
    val phase: CoordinatorSyncPhase,
    val runKind: SyncRunKind?,
    val attempt: UInt,
    val errorCode: UInt?,
    val message: String?,
    val updatedAtMs: Long,
)
