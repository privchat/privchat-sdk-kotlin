package com.netonstream.privchat.sdk

import com.netonstream.privchat.sdk.dto.AttachmentInfo
import com.netonstream.privchat.sdk.dto.ContentMessageType
import com.netonstream.privchat.sdk.dto.SendMessageOptions
import kotlinx.coroutines.delay

internal data class AttachmentVideoMetadata(
    val durationSeconds: UInt? = null,
    val width: UInt? = null,
    val height: UInt? = null,
    val thumbnailWidth: UInt? = null,
    val thumbnailHeight: UInt? = null,
    val thumbnailReady: Boolean = false,
)

internal data class MaterializedAttachment(
    val localPath: String,
    val sizeBytes: ULong,
    val video: AttachmentVideoMetadata? = null,
)

internal data class LocalAttachmentPlaceholder(
    val channelId: ULong,
    val channelType: Int,
    val fromUid: ULong,
    val messageType: Int,
    val localMessageId: ULong,
    val fileName: String,
    val mimeType: String,
    val searchableWord: String,
    val video: AttachmentVideoMetadata?,
    val extensionJson: String?,
)

internal interface AttachmentPreparationPort {
    fun generateLocalMessageId(): ULong
    fun nowEpochMillis(): Long
    suspend fun createPlaceholder(input: LocalAttachmentPlaceholder): ULong
    fun targetDirectory(userId: ULong, messageId: ULong, createdAtMs: Long): String
    suspend fun finalizePlaceholder(messageId: ULong, localPath: String, thumbStatus: Int)
    suspend fun discardPlaceholder(messageId: ULong)
    fun clientEndpoint(): String
    suspend fun enqueue(messageId: ULong, routeKey: String, localPath: String): ULong
}

internal suspend fun prepareAndEnqueueAttachmentBytes(
    channelId: ULong,
    channelType: Int,
    userId: ULong,
    fileName: String,
    mimeType: String,
    data: ByteArray,
    options: SendMessageOptions?,
    progress: ProgressObserver?,
    port: AttachmentPreparationPort,
    platform: AttachmentPlatformOps,
): Pair<ULong, AttachmentInfo> {
    val createdAtMs = port.nowEpochMillis()
    val messageId = port.createPlaceholder(
        LocalAttachmentPlaceholder(
            channelId = channelId,
            channelType = channelType,
            fromUid = userId,
            messageType = inferAttachmentMessageType(fileName, mimeType),
            localMessageId = port.generateLocalMessageId(),
            fileName = fileName,
            mimeType = mimeType,
            searchableWord = fileName,
            video = null,
            extensionJson = options?.extraJson,
        ),
    )
    try {
        val materialized = platform.materializeBytes(
            data = data,
            targetDirectory = port.targetDirectory(userId, messageId, createdAtMs),
            targetFileName = attachmentPayloadFileName(mimeType, fileName),
        )
        port.finalizePlaceholder(messageId, materialized.localPath, 0)
        delay(32)
        progress?.onPrepComplete()
        val queuedMessageId = port.enqueue(messageId, port.clientEndpoint(), materialized.localPath)
        progress?.onProgress(data.size.toULong(), data.size.toULong())
        return queuedMessageId to AttachmentInfo(
            url = materialized.localPath,
            mimeType = mimeType,
            size = materialized.sizeBytes,
            thumbnailUrl = null,
            filename = fileName,
            fileId = null,
            width = null,
            height = null,
            duration = null,
        )
    } catch (failure: Throwable) {
        runCatching { port.discardPlaceholder(messageId) }
        throw failure
    }
}

internal interface AttachmentPlatformOps {
    fun fileName(path: String): String
    suspend fun inspectVideo(path: String): AttachmentVideoMetadata
    suspend fun materialize(
        sourcePath: String,
        targetDirectory: String,
        targetFileName: String,
        messageType: Int,
    ): MaterializedAttachment
    suspend fun materializeBytes(
        data: ByteArray,
        targetDirectory: String,
        targetFileName: String,
    ): MaterializedAttachment
}

internal suspend fun prepareAndEnqueueAttachment(
    channelId: ULong,
    channelType: Int,
    userId: ULong,
    sourcePath: String,
    options: SendMessageOptions?,
    progress: ProgressObserver?,
    port: AttachmentPreparationPort,
    platform: AttachmentPlatformOps,
    forcedMessageType: Int? = null,
    forcedDurationSeconds: UInt? = null,
): Pair<ULong, AttachmentInfo> {
    val originalFileName = platform.fileName(sourcePath)
    val mimeType = guessAttachmentMime(originalFileName)
    val messageType = forcedMessageType ?: inferAttachmentMessageType(originalFileName, mimeType)
    val canonicalFileName = attachmentPayloadFileName(mimeType, originalFileName)
    val initialVideo = when {
        forcedDurationSeconds != null -> AttachmentVideoMetadata(durationSeconds = forcedDurationSeconds)
        messageType == ContentMessageType.VIDEO.value -> platform.inspectVideo(sourcePath)
        else -> null
    }
    val localMessageId = port.generateLocalMessageId()
    val createdAtMs = port.nowEpochMillis()
    val messageId = port.createPlaceholder(
        LocalAttachmentPlaceholder(
            channelId = channelId,
            channelType = channelType,
            fromUid = userId,
            messageType = messageType,
            localMessageId = localMessageId,
            fileName = originalFileName,
            mimeType = mimeType,
            searchableWord = originalFileName,
            video = initialVideo,
            extensionJson = options?.extraJson,
        ),
    )

    try {
        val targetDirectory = port.targetDirectory(userId, messageId, createdAtMs)
        val materialized = platform.materialize(
            sourcePath = sourcePath,
            targetDirectory = targetDirectory,
            targetFileName = canonicalFileName,
            messageType = messageType,
        )
        val thumbStatus = when {
            messageType != ContentMessageType.VIDEO.value -> 0
            materialized.video?.thumbnailReady == true -> 1
            else -> 3
        }
        port.finalizePlaceholder(messageId, materialized.localPath, thumbStatus)
        delay(32)
        progress?.onPrepComplete()
        val queuedMessageId = port.enqueue(messageId, port.clientEndpoint(), materialized.localPath)
        progress?.onProgress(1uL, 1uL)
        val video = materialized.video ?: initialVideo
        return queuedMessageId to AttachmentInfo(
            url = materialized.localPath,
            mimeType = mimeType,
            size = materialized.sizeBytes,
            thumbnailUrl = null,
            filename = originalFileName,
            fileId = null,
            width = video?.width,
            height = video?.height,
            duration = video?.durationSeconds,
        )
    } catch (failure: Throwable) {
        runCatching { port.discardPlaceholder(messageId) }
        throw failure
    }
}

internal fun inferAttachmentMessageType(fileName: String, mimeType: String?): Int {
    val mime = mimeType.orEmpty().lowercase()
    val extension = fileName.substringAfterLast('.', "").lowercase()
    return when {
        mime.startsWith("image/") || extension in setOf("jpg", "jpeg", "png", "gif", "webp", "bmp", "heic") -> ContentMessageType.IMAGE.value
        mime.startsWith("video/") || extension in setOf("mp4", "mov", "mkv", "avi", "webm") -> ContentMessageType.VIDEO.value
        else -> ContentMessageType.FILE.value
    }
}

internal fun guessAttachmentMime(fileName: String): String = when (fileName.substringAfterLast('.', "").lowercase()) {
    "jpg", "jpeg" -> "image/jpeg"
    "png" -> "image/png"
    "gif" -> "image/gif"
    "webp" -> "image/webp"
    "heic" -> "image/heic"
    "mp4" -> "video/mp4"
    "mov" -> "video/quicktime"
    "mkv" -> "video/x-matroska"
    "webm" -> "video/webm"
    "m4a", "mp4a" -> "audio/mp4"
    "aac" -> "audio/aac"
    "mp3" -> "audio/mpeg"
    "wav" -> "audio/wav"
    "pdf" -> "application/pdf"
    else -> "application/octet-stream"
}

internal fun attachmentPayloadFileName(mimeType: String, originalFileName: String): String {
    val extension = when (mimeType.lowercase()) {
        "image/jpeg" -> "jpg"
        "image/png" -> "png"
        "image/gif" -> "gif"
        "image/webp" -> "webp"
        "image/heic" -> "heic"
        "video/mp4" -> "mp4"
        "video/quicktime" -> "mov"
        "video/x-matroska" -> "mkv"
        "video/webm" -> "webm"
        "audio/mp4" -> "m4a"
        "audio/aac" -> "aac"
        "audio/mpeg" -> "mp3"
        "audio/wav" -> "wav"
        "application/pdf" -> "pdf"
        else -> originalFileName.substringAfterLast('.', "bin").lowercase().ifBlank { "bin" }
    }
    return "payload.$extension"
}
