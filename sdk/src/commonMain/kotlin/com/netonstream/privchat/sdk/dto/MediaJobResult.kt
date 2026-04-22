package com.netonstream.privchat.sdk.dto

/**
 * Plan 2: host reply to [SdkEventPayload] of type `media_job_requested`.
 *
 * When [ok] is true, [outputPath] must be the file the host produced (JPEG for
 * `video_thumbnail`). When false, [error] should describe the reason; Rust will
 * fall back to `thumb_status=3`.
 */
data class MediaJobResult(
    val ok: Boolean,
    val outputPath: String? = null,
    val error: String? = null,
)
