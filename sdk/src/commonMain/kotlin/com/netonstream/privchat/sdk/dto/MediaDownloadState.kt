package com.netonstream.privchat.sdk.dto

/**
 * Per-message download state mirrored from the Rust SDK.
 * Emitted via `SdkEventEnvelope` (type = "media_download_state_changed").
 */
sealed class MediaDownloadState {
    object Idle : MediaDownloadState()
    data class Downloading(val bytes: ULong, val total: ULong?) : MediaDownloadState()
    data class Paused(val bytes: ULong, val total: ULong?) : MediaDownloadState()
    data class Done(val path: String) : MediaDownloadState()
    data class Failed(val code: UInt, val message: String) : MediaDownloadState()
}
