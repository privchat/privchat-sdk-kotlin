package com.netonstream.privchat.sdk

import com.netonstream.privchat.sdk.dto.MessageStatus
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fwrite
import platform.posix.getenv
import platform.posix.getpid
import platform.posix.system
import platform.posix.time
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

/**
 * 附件主链集成测试 —— **真的走 Kotlin → UniFFI → Rust Core → server**。
 *
 * `AttachmentPreparationTest` 用假 port 锁住 Kotlin 侧的调用顺序，Rust 侧的
 * `outbox-attachment-e2e` 锁住 Core 的行为。两者中间还夹着一段谁都没测到的
 * 东西：UniFFI 边界本身。`finalizeAndEnqueue` 的参数顺序、ULong/Int 的映射、
 * 可空字段的编解码——这些错了，上面两处测试**照样全绿**，只有真机上发不出图。
 *
 * 所以这里一个桩都不打：注册两个真账号，把一张真 PNG 通过
 * `PrivchatClient.sendAttachmentFromPath` 发出去，要求发送方收敛到 `Sent` 并
 * 拿到 server id、接收方看到同一条且只有一条。
 *
 * 需要本地 privchat-server。连不上就跳过而不是失败——没有服务端时红的是环境
 * 不是产品；但**跳过会打印出来**，不会悄悄变绿。
 */
@OptIn(ExperimentalForeignApi::class)
class AttachmentMainChainIntegrationTest {

    private fun env(name: String): String? = getenv(name)?.toKString()

    private fun host() = env("PRIVCHAT_HOST") ?: "127.0.0.1"
    private fun port() = env("PRIVCHAT_TCP_PORT")?.toIntOrNull() ?: 9001

    private fun endpoints() = listOf(
        ServerEndpoint(protocol = TransportProtocol.Tcp, host = host(), port = port()),
    )

    private var dirSeq = 0

    private fun tempDir(tag: String): String {
        val base = env("TMPDIR")?.trimEnd('/') ?: "/tmp"
        val dir = "$base/privchat-kt-itest-${getpid()}-${time(null)}-${dirSeq++}-$tag"
        system("mkdir -p '$dir'")
        return dir
    }

    /**
     * 96x64 的**合法** PNG（8-bit RGB，单色）。
     *
     * 必须是真图片：发送链路会解码它来生成缩略图并填 width/height，随机字节
     * 过不了那一步——而那一步正是要测的。内联成字面量是为了让这个测试不依赖
     * 仓库里的任何 fixture 文件。
     */
    private val sourcePngHex =
        "89504e470d0a1a0a0000000d49484452000000600000004008020000006a56e559000000694944415478daedd0310d00" +
            "000803b049421ad29088036e8e2655d0540f872810244890204182040942902041820409122408418204091224489020" +
            "04091224489020418204214890204182040912842041820409122448108204091224489020418210244890a07f16f87a" +
            "81d2cd829af10000000049454e44ae426082"

    private fun writeSourcePng(path: String) {
        val bytes = ByteArray(sourcePngHex.length / 2) { i ->
            sourcePngHex.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
        val f = fopen(path, "wb") ?: error("cannot open $path for writing")
        try {
            bytes.usePinned { pinned ->
                fwrite(pinned.addressOf(0), 1.convert(), bytes.size.convert(), f)
            }
        } finally {
            fclose(f)
        }
    }

    private fun newClient(tag: String): PrivchatClient =
        PrivchatClient.create(
            PrivchatConfig(dataDir = tempDir(tag), serverEndpoints = endpoints()),
        ).getOrThrow()

    @Test
    fun attachmentReachesThePeerThroughTheRealFfiBoundary() = runTest(timeout = 180.seconds) {
        val stamp = time(null)

        val sender = newClient("sender")
        val connected = sender.connect()
        if (connected.isFailure) {
            println(
                "[SKIP] AttachmentMainChainIntegrationTest: no privchat-server at " +
                    "${host()}:${port()} (${connected.exceptionOrNull()})",
            )
            return@runTest
        }
        val receiver = newClient("receiver")
        receiver.connect().getOrThrow()

        val senderAuth = sender
            .register("ktit_s_$stamp", "Passw0rd!$stamp", uuidLike())
            .getOrThrow()
        sender.authenticate(senderAuth.userId, senderAuth.token, senderAuth.deviceId).getOrThrow()
        sender.runBootstrapSync().getOrThrow()

        val receiverAuth = receiver
            .register("ktit_r_$stamp", "Passw0rd!$stamp", uuidLike())
            .getOrThrow()
        receiver.authenticate(receiverAuth.userId, receiverAuth.token, receiverAuth.deviceId)
            .getOrThrow()
        receiver.runBootstrapSync().getOrThrow()

        val channel = sender.getOrCreateDirectChannel(receiverAuth.userId).getOrThrow()

        val source = "${tempDir("src")}/attachment.png"
        writeSourcePng(source)

        // === 被测的那一步：App 发图只有这一个入口 ===
        val sendResult =
            sender.sendAttachmentFromPath(channel.channelId, source, options = null, progress = null)
        val (messageId, info) = sendResult.getOrElse { e ->
            // 默认的 message 会把 Rust 侧的原因吃掉，只剩一句 "failed"。
            throw AssertionError("sendAttachmentFromPath failed: $e / cause=${e.cause}", e)
        }

        assertTrue(messageId > 0uL, "sendAttachmentFromPath must return a local message id")
        assertEquals("image/png", info.mimeType, "mime must survive the FFI boundary")
        assertEquals(
            sourcePngHex.length.toULong() / 2uL,
            info.size,
            "materialized size must round-trip through the FFI boundary",
        )
        // 这里**不**断言 width/height：入队时图片还没被解码。native 的
        // `materialize` 只给 video 提尺寸，图片尺寸是 Rust drain 上传前解码原图
        // 才得到的，所以此刻为 null 是契约本身，不是缺陷。发出去之后的尺寸由
        // Rust 侧 `outbox-attachment-e2e` 在接收端的 typed metadata 上断言。

        // 发送方收敛：状态机和 server id 都要跨过 FFI 回到 Kotlin。
        var sentServerId: ULong? = null
        for (attempt in 0 until 120) {
            val rows = sender.getMessages(channel.channelId, limit = 50u, beforeSeq = null)
                .getOrThrow()
            val row = rows.firstOrNull { it.id == messageId }
            if (row != null && row.status == MessageStatus.Sent && row.serverMessageId != null) {
                sentServerId = row.serverMessageId
                break
            }
            delay(250)
        }
        val serverId = assertNotNull(
            sentServerId,
            "attachment never reached Sent through the Kotlin API",
        )

        // 接收方：同一条，且只有一条。
        var seen = 0
        for (attempt in 0 until 60) {
            val rows = receiver.getMessages(channel.channelId, limit = 50u, beforeSeq = null)
                .getOrThrow()
            seen = rows.count { it.serverMessageId == serverId }
            if (seen >= 1) break
            delay(250)
        }
        assertEquals(1, seen, "peer must see the attachment exactly once")
    }

    /** 服务端要求 device_id 是 UUID 形态。 */
    private fun uuidLike(): String {
        val hex = "0123456789abcdef"
        var seed = time(null) * 1_000_003L + getpid().toLong()
        fun next(): Char {
            seed = seed * 6364136223846793005L + 1442695040888963407L
            return hex[((seed ushr 33) and 0xFL).toInt()]
        }
        val sb = StringBuilder()
        repeat(8) { sb.append(next()) }
        sb.append('-')
        repeat(4) { sb.append(next()) }
        sb.append("-4")
        repeat(3) { sb.append(next()) }
        sb.append("-a")
        repeat(3) { sb.append(next()) }
        sb.append('-')
        repeat(12) { sb.append(next()) }
        return sb.toString()
    }
}
