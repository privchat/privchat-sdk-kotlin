package com.netonstream.privchat.sdk

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.coroutines.test.runTest
import platform.posix.getenv
import platform.posix.getpid
import platform.posix.system
import platform.posix.time
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

/**
 * 隐私开关的**真实往返**：Kotlin → UniFFI → Rust Core → server → DB → 再读回来。
 *
 * 🔴 为什么非得是真链路：`allow_add_by_card` 曾经在这条链上被**静默丢弃**——
 * App 有开关、Kotlin 也发了这个字段，但 protocol 请求体、server 映射、两个响应
 * 都没有它。关掉它 UI 显示成功，重新读又是开着的。
 *
 * 这类缺口的特征就是**每一层单独测都是绿的**：Kotlin 侧断言「我发出去了」为真，
 * server 侧断言「我把收到的字段写进去了」也为真，丢失发生在两者中间没人看的地方。
 * 所以这里一个桩都不打，改完之后重新读一次，比对的是读回来的值。
 *
 * 需要本地 privchat-server。连不上就跳过而不是失败——没有服务端时红的是环境不是
 * 产品；但**跳过会打印出来**，不会悄悄变绿。
 */
@OptIn(ExperimentalForeignApi::class)
class PrivacySettingsRoundTripTest {

    private fun env(name: String): String? = getenv(name)?.toKString()

    private fun host() = env("PRIVCHAT_HOST") ?: "127.0.0.1"
    private fun port() = env("PRIVCHAT_TCP_PORT")?.toIntOrNull() ?: 9001

    private fun tempDir(tag: String): String {
        val base = env("TMPDIR")?.trimEnd('/') ?: "/tmp"
        val dir = "$base/privchat-kt-privacy-${getpid()}-${time(null)}-$tag"
        system("mkdir -p '$dir'")
        return dir
    }

    private fun uuidLike(): String {
        val hex = "0123456789abcdef"
        var seed = time(null) * 1_000_003L + getpid().toLong()
        fun next(): Char {
            seed = seed * 6364136223846793005L + 1442695040888963407L
            return hex[((seed ushr 33) and 0xFL).toInt()]
        }
        return buildString { repeat(32) { append(next()) } }
    }

    @Test
    fun everyPrivacySwitchSurvivesTheRoundTrip() = runTest(timeout = 120.seconds) {
        val stamp = time(null)
        val client = PrivchatClient.create(
            PrivchatConfig(
                dataDir = tempDir("privacy"),
                serverEndpoints = listOf(
                    ServerEndpoint(protocol = TransportProtocol.Tcp, host = host(), port = port()),
                ),
            ),
        ).getOrThrow()

        val connected = client.connect()
        if (connected.isFailure) {
            println(
                "[SKIP] PrivacySettingsRoundTripTest: no privchat-server at " +
                    "${host()}:${port()} (${connected.exceptionOrNull()})",
            )
            return@runTest
        }

        val auth = client.register("ktpv_$stamp", "Passw0rd!$stamp", uuidLike()).getOrThrow()
        client.authenticate(auth.userId, auth.token, auth.deviceId).getOrThrow()

        val initial = client.privacyGet().getOrElse { e ->
            throw AssertionError("privacyGet failed: $e / cause=${e.cause}", e)
        }
        assertTrue(initial.allowAddByCard, "新账号默认允许通过名片添加")

        // 一次只关一项，并逐项读回来——批量改再读一次的话，任何一项被丢掉都会
        // 被其它项的成功掩盖过去。
        client.privacyUpdate(PrivacySettingsPatch(allowAddByCard = false)).getOrElse { e ->
            throw AssertionError("privacyUpdate(allowAddByCard) failed: $e", e)
        }

        val afterCard = client.privacyGet().getOrThrow()
        assertEquals(
            false,
            afterCard.allowAddByCard,
            "名片添加必须真的关掉——读回来还是 true 就是这个开关形同虚设",
        )
        assertTrue(
            afterCard.allowAddByGroup,
            "只改一项时其它项不能被顺手覆盖成默认值",
        )

        client.privacyUpdate(PrivacySettingsPatch(allowSearchByPhone = false)).getOrThrow()
        val afterPhone = client.privacyGet().getOrThrow()
        assertEquals(false, afterPhone.allowSearchByPhone, "手机号搜索必须真的关掉")
        assertEquals(
            false,
            afterPhone.allowAddByCard,
            "上一次的修改不能被这一次覆盖回默认值",
        )
    }
}
