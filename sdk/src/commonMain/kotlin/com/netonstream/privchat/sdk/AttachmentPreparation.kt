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
    /** 随附件一起发出的说明文字：发送时它就是消息正文。 */
    val caption: String?,
    val searchableWord: String,
    val video: AttachmentVideoMetadata?,
    val extensionJson: String?,
)

internal interface AttachmentPreparationPort {
    fun generateLocalMessageId(): ULong
    fun nowEpochMillis(): Long
    suspend fun createPlaceholder(input: LocalAttachmentPlaceholder): ULong
    fun targetDirectory(userId: ULong, messageId: ULong, createdAtMs: Long): String
    /**
     * 定稿并入队，**Core 侧一个事务**。
     *
     * 以前是 finalizePlaceholder + enqueue 两步：中间失败（或进程被杀）会留下
     * 一条已完成、界面上也已显示、却没有任何命令负责发送的附件。下面 catch 里
     * 的删除只是清理，崩溃时根本不会执行，不能当作一致性保证。
     */
    suspend fun finalizeAndEnqueue(
        messageId: ULong,
        localPath: String,
        thumbStatus: Int,
        routeKey: String,
    ): ULong
    suspend fun discardPlaceholder(messageId: ULong)
    fun clientEndpoint(): String
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
            caption = null,
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
        val queuedMessageId = port.finalizeAndEnqueue(
            messageId,
            materialized.localPath,
            0,
            port.clientEndpoint(),
        )
        delay(32)
        progress?.onPrepComplete()
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
    /**
     * 展示用文件名。缓存/临时文件的名字对用户没有意义——重发一份收到的附件时，
     * 磁盘上叫 `42.pdf`，但消息里该显示它本来的名字。为空则用源路径的文件名。
     */
    displayFileName: String? = null,
    /** 随附件一起发出的说明文字（「图片配一句话」）。 */
    caption: String? = null,
): Pair<ULong, AttachmentInfo> {
    val originalFileName = displayFileName?.trim()?.takeIf { it.isNotEmpty() }
        ?.substringAfterLast('/')?.substringAfterLast('\\')
        ?: platform.fileName(sourcePath)
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
            caption = caption?.trim()?.takeIf { it.isNotEmpty() },
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
        val queuedMessageId = port.finalizeAndEnqueue(
            messageId,
            materialized.localPath,
            thumbStatus,
            port.clientEndpoint(),
        )
        delay(32)
        progress?.onPrepComplete()
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

/**
 * 下载到缓存时用的文件名。
 *
 * 🔴 扩展名不是装饰：普通发送是从**文件名**推 MIME 和消息类型的（[guessAttachmentMime] /
 * [inferAttachmentMessageType]）。缓存名要是只写 file_id（`42`），一张图重新发出去就变成
 * `application/octet-stream` 的「文件」消息——对端看到的不再是图片。
 *
 * 所以名字优先跟源文件名走；源文件名没有扩展名时，用源 MIME 补一个。
 */
internal fun attachmentCacheFileName(
    fileId: String,
    fileName: String?,
    mimeType: String?,
): String {
    val base = fileId.trim()
        .filter { it in 'a'..'z' || it in 'A'..'Z' || it in '0'..'9' || it == '-' || it == '_' }
        .take(64)
        .ifEmpty { "attachment" }
    val sourceName = fileName?.trim().orEmpty()
    // 🔴 文件名来自远端消息，不可信。只认字母数字：`../` `/` `%00` 之类混进扩展名，
    // 拼出来就是缓存目录之外的路径。
    val sourceExtension = sourceName.substringAfterLast('.', "").lowercase()
    if (sourceExtension.isNotEmpty() &&
        sourceExtension.length <= 8 &&
        sourceExtension.all { it in 'a'..'z' || it in '0'..'9' }
    ) {
        return "$base.$sourceExtension"
    }
    // attachmentPayloadFileName 已经维护着 MIME→扩展名这张表，别抄第二份。
    // 🔴 但它认不出 MIME 时会**退回源文件名的扩展名**——那串刚被判为不可信，
    // 从这条路放回来就等于白校验。所以出口再过一次同一张白名单。
    val fromMime = attachmentPayloadFileName(mimeType.orEmpty(), sourceName).substringAfterLast('.', "")
    val safe = fromMime.takeIf {
        it.isNotEmpty() && it.length <= 8 && it.all { c -> c in 'a'..'z' || c in '0'..'9' }
    } ?: "bin"
    return "$base.$safe"
}
