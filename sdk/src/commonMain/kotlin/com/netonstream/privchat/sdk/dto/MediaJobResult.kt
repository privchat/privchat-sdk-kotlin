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
    /**
     * 媒体的**显示**宽高（已按旋转摆正）。宿主拿得到就填——它手上有原始 asset，比
     * Core 从首帧图反推准确（首帧会被缩到 maxDim，只保得住比例）。
     *
     * 缺省则 Core 回退到解首帧（Spec §3.8.3 的取值次序）。
     */
    val width: UInt? = null,
    val height: UInt? = null,
)
