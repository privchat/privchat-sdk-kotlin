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
        override suspend fun finalizePlaceholder(messageId: ULong, localPath: String, thumbStatus: Int) {
            calls += "finalize:$messageId:$thumbStatus"
        }
        override suspend fun discardPlaceholder(messageId: ULong) { discarded = true }
        override fun clientEndpoint() = "client"
        override suspend fun enqueue(messageId: ULong, routeKey: String, localPath: String): ULong {
            calls += "enqueue:$messageId:$localPath"
            return 77uL
        }
    }
}
