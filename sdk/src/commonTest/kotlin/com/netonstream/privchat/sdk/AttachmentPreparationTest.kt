package com.netonstream.privchat.sdk

import com.netonstream.privchat.sdk.dto.ContentMessageType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AttachmentPreparationTest {
    @Test
    fun successfulPreparationUsesCanonicalLifecycle() = runTest {
        val calls = mutableListOf<String>()
        val progress = RecordingProgress()
        val port = FakePort(calls)
        val platform = object : AttachmentPlatformOps {
            override fun fileName(path: String) = "clip.mp4"
            override suspend fun inspectVideo(path: String) =
                AttachmentVideoMetadata(durationSeconds = 7u, width = 1920u, height = 1080u)

            override suspend fun materialize(
                sourcePath: String,
                targetDirectory: String,
                targetFileName: String,
                messageType: Int,
            ): MaterializedAttachment {
                calls += "materialize:$targetFileName"
                return MaterializedAttachment(
                    localPath = "$targetDirectory/$targetFileName",
                    sizeBytes = 42u,
                    video = AttachmentVideoMetadata(
                        durationSeconds = 7u,
                        width = 1920u,
                        height = 1080u,
                        thumbnailReady = true,
                    ),
                )
            }
            override suspend fun materializeBytes(
                data: ByteArray,
                targetDirectory: String,
                targetFileName: String,
            ) = MaterializedAttachment("$targetDirectory/$targetFileName", data.size.toULong())
        }

        val (messageId, info) = prepareAndEnqueueAttachment(
            channelId = 11u,
            channelType = 2,
            userId = 9u,
            sourcePath = "/tmp/clip.mp4",
            options = null,
            progress = progress,
            port = port,
            platform = platform,
        )

        assertEquals(77uL, messageId)
        assertEquals("video/mp4", info.mimeType)
        assertEquals(1920u, info.width)
        assertEquals(
            listOf(
                "create:video/mp4",
                "target:41",
                "materialize:payload.mp4",
                "finalize:41:1",
                "enqueue:41:/media/41/payload.mp4",
            ),
            calls,
        )
        assertTrue(progress.prepComplete)
        assertEquals(1uL to 1uL, progress.lastProgress)
        assertFalse(port.discarded)
    }

    @Test
    fun failedMaterializationDiscardsPlaceholder() = runTest {
        val port = FakePort(mutableListOf())
        val platform = object : AttachmentPlatformOps {
            override fun fileName(path: String) = "document.pdf"
            override suspend fun inspectVideo(path: String) = AttachmentVideoMetadata()
            override suspend fun materialize(
                sourcePath: String,
                targetDirectory: String,
                targetFileName: String,
                messageType: Int,
            ): MaterializedAttachment = error("disk full")
            override suspend fun materializeBytes(
                data: ByteArray,
                targetDirectory: String,
                targetFileName: String,
            ): MaterializedAttachment = error("disk full")
        }

        assertFailsWith<IllegalStateException> {
            prepareAndEnqueueAttachment(
                channelId = 11u,
                channelType = 1,
                userId = 9u,
                sourcePath = "/tmp/document.pdf",
                options = null,
                progress = null,
                port = port,
                platform = platform,
            )
        }
        assertTrue(port.discarded)
    }

    @Test
    fun typeAndMimeInferenceAreSharedAcrossPlatforms() {
        assertEquals(ContentMessageType.IMAGE.value, inferAttachmentMessageType("photo.HEIC", null))
        assertEquals(ContentMessageType.VIDEO.value, inferAttachmentMessageType("blob", "video/mp4"))
        assertEquals(ContentMessageType.FILE.value, inferAttachmentMessageType("archive.zip", null))
        assertEquals("audio/mp4", guessAttachmentMime("voice.m4a"))
        assertEquals("payload.m4a", attachmentPayloadFileName("audio/mp4", "voice.m4a"))
    }

    private class RecordingProgress : ProgressObserver {
        var prepComplete = false
        var lastProgress: Pair<ULong, ULong>? = null
        override fun onPrepComplete() { prepComplete = true }
        override fun onProgress(current: ULong, total: ULong?) {
            lastProgress = current to requireNotNull(total)
        }
    }

    private class FakePort(private val calls: MutableList<String>) : AttachmentPreparationPort {
        var discarded = false
        override fun generateLocalMessageId() = 123uL
        override fun nowEpochMillis() = 456L
        override suspend fun createPlaceholder(input: LocalAttachmentPlaceholder): ULong {
            calls += "create:${input.mimeType}"
            return 41uL
        }
        override fun targetDirectory(userId: ULong, messageId: ULong, createdAtMs: Long): String {
            calls += "target:$messageId"
            return "/media/$messageId"
        }
        override suspend fun finalizeAndEnqueue(
            messageId: ULong,
            localPath: String,
            thumbStatus: Int,
            routeKey: String,
        ): ULong {
            // 定稿与入队现在是 Core 的一个事务，测试替身也据此合成一次调用。
            calls += "finalize:$messageId:$thumbStatus"
            calls += "enqueue:$messageId:$localPath"
            return 77uL
        }
        override suspend fun discardPlaceholder(messageId: ULong) { discarded = true }
        override fun clientEndpoint() = "client"
    }
}

/**
 * 缓存文件名决定了这份内容再发出去时是「图片」还是「文件」——普通发送就是按文件名推 MIME 的。
 */
class AttachmentCacheFileNameTest {

    @Test
    fun the_source_extension_is_kept() {
        assertEquals("42.jpg", attachmentCacheFileName("42", "IMG_0001.JPG", "image/jpeg"))
    }

    /** 🔴 服务端不一定回文件名。只写 file_id 的话扩展名没了，图片会被重发成「文件」消息。 */
    @Test
    fun a_nameless_file_falls_back_to_its_mime() {
        val name = attachmentCacheFileName("42", null, "image/jpeg")
        assertEquals("42.jpg", name)
        assertEquals("image/jpeg", guessAttachmentMime(name))
        assertEquals(ContentMessageType.IMAGE.value, inferAttachmentMessageType(name, guessAttachmentMime(name)))
    }

    @Test
    fun a_video_keeps_its_media_type_through_the_cache_name() {
        val name = attachmentCacheFileName("7", "", "video/mp4")
        assertEquals("7.mp4", name)
        assertEquals(ContentMessageType.VIDEO.value, inferAttachmentMessageType(name, guessAttachmentMime(name)))
    }

    /** 类型认不出来时退回 .bin：发成「文件」是对的，猜一个扩展名才是错的。 */
    @Test
    fun an_unknown_type_stays_a_file() {
        assertEquals("9.bin", attachmentCacheFileName("9", null, null))
    }

    @Test
    fun an_empty_id_still_produces_a_usable_name() {
        assertEquals("attachment.pdf", attachmentCacheFileName("", "contract.pdf", "application/pdf"))
    }
}

/** 文件名来自远端消息：任何一段都不能直接拼进本地路径。 */
class AttachmentCacheFileNameSafetyTest {

    @Test
    fun a_path_traversal_extension_is_refused() {
        val name = attachmentCacheFileName("42", "photo../../../etc/passwd", "image/jpeg")
        assertEquals("42.jpg", name)
    }

    @Test
    fun a_separator_in_the_extension_is_refused() {
        assertEquals("42.jpg", attachmentCacheFileName("42", "x.jp/g", "image/jpeg"))
        assertEquals("42.jpg", attachmentCacheFileName("42", "x.jp g", "image/jpeg"))
    }

    @Test
    fun a_hostile_file_id_cannot_escape_the_cache_directory() {
        val name = attachmentCacheFileName("../../etc/passwd", "a.jpg", "image/jpeg")
        assertEquals("etcpasswd.jpg", name)
    }
}

/**
 * 展示文件名：重发一份收到的附件时，磁盘上是 `42.pdf`，消息里该显示 `合同.pdf`。
 */
class AttachmentDisplayFileNameTest {

    /** 记下占位符——文件名最终就写在它上面。 */
    private class RecordingPort : AttachmentPreparationPort {
        var placeholder: LocalAttachmentPlaceholder? = null
        override fun generateLocalMessageId() = 1uL
        override fun nowEpochMillis() = 0L
        override suspend fun createPlaceholder(input: LocalAttachmentPlaceholder): ULong {
            placeholder = input
            return 1uL
        }
        override fun targetDirectory(userId: ULong, messageId: ULong, createdAtMs: Long) = "/media/1"
        override suspend fun finalizeAndEnqueue(
            messageId: ULong,
            localPath: String,
            thumbStatus: Int,
            routeKey: String,
        ) = 2uL
        override suspend fun discardPlaceholder(messageId: ULong) {}
        override fun clientEndpoint() = "client"
    }

    private class FakePlatform : AttachmentPlatformOps {
        override fun fileName(path: String) = path.substringAfterLast('/')
        override suspend fun inspectVideo(path: String) = AttachmentVideoMetadata()
        override suspend fun materialize(
            sourcePath: String,
            targetDirectory: String,
            targetFileName: String,
            messageType: Int,
        ) = MaterializedAttachment("$targetDirectory/$targetFileName", 8u)
        override suspend fun materializeBytes(
            data: ByteArray,
            targetDirectory: String,
            targetFileName: String,
        ) = MaterializedAttachment("$targetDirectory/$targetFileName", data.size.toULong())
    }

    @Test
    fun the_display_name_replaces_the_path_name() = runTest {
        val port = RecordingPort()
        prepareAndEnqueueAttachment(
            channelId = 1uL,
            channelType = 1,
            userId = 7uL,
            sourcePath = "/cache/42.pdf",
            options = null,
            progress = null,
            port = port,
            platform = FakePlatform(),
            displayFileName = "合同.pdf",
        )
        assertEquals("合同.pdf", port.placeholder?.fileName)
    }

    @Test
    fun without_one_the_path_name_is_used() = runTest {
        val port = RecordingPort()
        prepareAndEnqueueAttachment(
            channelId = 1uL,
            channelType = 1,
            userId = 7uL,
            sourcePath = "/cache/42.pdf",
            options = null,
            progress = null,
            port = port,
            platform = FakePlatform(),
        )
        assertEquals("42.pdf", port.placeholder?.fileName)
    }

    /** 🔴 名字来自远端消息：带路径分隔符时只取最后一段，绝不让它决定写到哪儿。 */
    @Test
    fun a_display_name_cannot_carry_a_path() = runTest {
        val port = RecordingPort()
        prepareAndEnqueueAttachment(
            channelId = 1uL,
            channelType = 1,
            userId = 7uL,
            sourcePath = "/cache/42.pdf",
            options = null,
            progress = null,
            port = port,
            platform = FakePlatform(),
            displayFileName = "../../etc/passwd",
        )
        assertEquals("passwd", port.placeholder?.fileName)
    }
}

/** 🔴 MIME 认不出来时，扩展名不能从「源文件名」这条旁路绕回来。 */
class AttachmentCacheFileNameMimeFallbackTest {

    @Test
    fun an_unknown_mime_does_not_reinstate_the_rejected_extension() {
        // 扩展名带分隔符 → 直接判定拒了；MIME 又认不出 → 不许从源文件名再取一次。
        assertEquals("42.bin", attachmentCacheFileName("42", "x.jp/g", "application/x-unknown"))
        assertEquals("42.bin", attachmentCacheFileName("42", "x.../../etc/passwd", null))
    }

    @Test
    fun an_overlong_extension_falls_back_to_bin() {
        assertEquals("42.bin", attachmentCacheFileName("42", "x.abcdefghijkl", "application/x-unknown"))
    }
}
