package com.netonstream.privchat.sdk.dto

data class SdkEventEnvelope(
    val sequenceId: ULong,
    val timestampMs: Long,
    val event: SdkEventPayload,
)

data class SdkEventPayload(
    val type: String,
    val fromState: String? = null,
    val toState: String? = null,
    val fromNetworkHint: String? = null,
    val toNetworkHint: String? = null,
    val userId: ULong? = null,
    val entityType: String? = null,
    val entityId: String? = null,
    val scope: String? = null,
    val deleted: Boolean? = null,
    val channelId: ULong? = null,
    val channelType: Int? = null,
    val messageId: ULong? = null,
    val reason: String? = null,
    val status: Int? = null,
    val serverMessageId: ULong? = null,
    val isRead: Boolean? = null,
    val isTyping: Boolean? = null,
    val kind: String? = null,
    val action: String? = null,
    val queueIndex: ULong? = null,
    val queued: ULong? = null,
    val applied: ULong? = null,
    val droppedDuplicates: ULong? = null,
    val entityTypesSynced: ULong? = null,
    val channelsScanned: ULong? = null,
    val channelsApplied: ULong? = null,
    val channelFailures: ULong? = null,
    val classification: String? = null,
    val errorCode: UInt? = null,
    val topic: String? = null,
    val publisher: String? = null,
    val payload: ByteArray? = null,
    val timestamp: ULong? = null,
    val readerId: ULong? = null,
    val readPts: ULong? = null,
    val deliveredAt: ULong? = null,
    /** One of "idle" | "downloading" | "paused" | "done" | "failed" (only for media_download_state_changed). */
    val mediaDownloadStateKind: String? = null,
    val mediaDownloadBytes: ULong? = null,
    val mediaDownloadTotal: ULong? = null,
    val mediaDownloadPath: String? = null,
    /** Plan 2 media job (only for media_job_requested). */
    val jobId: String? = null,
    val jobKind: String? = null,
    val sourcePath: String? = null,
    val outputPath: String? = null,
    val mimeType: String? = null,
    val timeoutMs: ULong? = null,
    /**
     * 强制登出相关字段（仅 `forced_logout` 事件使用）。
     * - [code] 对应 `privchat_protocol::ErrorCode` 的 u32 码，和 [com.netonstream.privchat.sdk.dto.TerminalReason.code] 对齐；0 表示未携带码。
     * - [source] 取值 `"ConnectAuth"` / `"RpcAuth"` / `"Manual"`；Manual 表示宿主主动触发 authenticate，UI 不应再弹错误提示。
     */
    val code: UInt? = null,
    val source: String? = null,
)
