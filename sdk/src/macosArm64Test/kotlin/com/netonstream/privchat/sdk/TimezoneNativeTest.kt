package com.netonstream.privchat.sdk

import com.netonstream.privchat.sdk.dto.LocalDateTimeParts
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv
import platform.posix.getpid
import platform.posix.system
import platform.posix.time
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * 时区 API 的**真实本地往返**：Kotlin wrapper → UniFFI → Rust core（chrono-tz）。
 *
 * 纯本地计算，不需要 server / connect（local-first）：create() 只初始化本地 core，
 * 时区配置与格式化全部在本地完成。macosArm64 在本机（darwin arm64）真实链接 FFI
 * 静态库并执行，故这是三端里 Kotlin 侧的非桩验证（Rust core / TS 各有独立单测）。
 *
 * format_conversation/separator 的「今天/昨天/周X」相对分支依赖 Rust 内部 Utc::now()
 * （wrapper 不暴露 now 注入），其确定性已在 Rust core 单测（注入 now）与 TS 单测覆盖；
 * 这里用固定历史瞬间（2000 年）锁定与 now 无关的「更早 / 跨年」分支，避免跨午夜抖动。
 */
@OptIn(ExperimentalForeignApi::class)
class TimezoneNativeTest {

    private fun env(name: String): String? = getenv(name)?.toKString()

    private fun tempDir(tag: String): String {
        val base = env("TMPDIR")?.trimEnd('/') ?: "/tmp"
        val dir = "$base/privchat-kt-tz-${getpid()}-${time(null)}-$tag"
        system("mkdir -p '$dir'")
        return dir
    }

    private fun newClient(tag: String): PrivchatClient =
        PrivchatClient.create(
            PrivchatConfig(
                dataDir = tempDir(tag),
                serverEndpoints = listOf(
                    ServerEndpoint(protocol = TransportProtocol.Tcp, host = "127.0.0.1", port = 1),
                ),
            ),
        ).getOrThrow()

    /** Howard Hinnant days_from_civil → UTC 毫秒（确定性夹具，不依赖平台日期库）。 */
    private fun utcMillis(y: Int, mo: Int, d: Int, h: Int, mi: Int, s: Int): Long {
        val yy = if (mo <= 2) y - 1 else y
        val era = (if (yy >= 0) yy else yy - 399) / 400
        val yoe = yy - era * 400
        val mp = (mo + 9) % 12
        val doy = (153 * mp + 2) / 5 + d - 1
        val doe = yoe * 365 + yoe / 4 - yoe / 100 + doy
        val days = era * 146097 + doe - 719468
        return days * 86_400_000L + h * 3_600_000L + mi * 60_000L + s * 1_000L
    }

    @Test
    fun atomUtcEpochAndShanghai() {
        val c = newClient("atomEpoch")
        assertEquals(LocalDateTimeParts(1970, 1, 1, 0, 0, 0, 4), c.epochMillisToLocalDateTime(0L, "UTC").getOrThrow())
        assertEquals(LocalDateTimeParts(1970, 1, 1, 8, 0, 0, 4), c.epochMillisToLocalDateTime(0L, "Asia/Shanghai").getOrThrow())
        c.close()
    }

    @Test
    fun atomNewYorkDstSummerVsWinter() {
        val c = newClient("atomNyDst")
        val summer = utcMillis(2026, 7, 1, 12, 0, 0)
        val winter = utcMillis(2026, 1, 1, 12, 0, 0)
        assertEquals(8, c.epochMillisToLocalDateTime(summer, "America/New_York").getOrThrow().hour, "DST 夏令时应为 UTC-4")
        assertEquals(7, c.epochMillisToLocalDateTime(winter, "America/New_York").getOrThrow().hour, "冬令时应为 UTC-5")
        c.close()
    }

    @Test
    fun atomHoChiMinhNoDst() {
        val c = newClient("atomHcm")
        val summer = utcMillis(2026, 7, 1, 12, 0, 0)
        val winter = utcMillis(2026, 1, 1, 12, 0, 0)
        assertEquals(19, c.epochMillisToLocalDateTime(summer, "Asia/Ho_Chi_Minh").getOrThrow().hour)
        assertEquals(19, c.epochMillisToLocalDateTime(winter, "Asia/Ho_Chi_Minh").getOrThrow().hour)
        c.close()
    }

    @Test
    fun atomRejectsUnknownZoneAndFixedOffset() {
        val c = newClient("atomBadZone")
        assertTrue(c.epochMillisToLocalDateTime(0L, "Mars/Olympus").isFailure)
        // 固定 offset 不是 IANA ID，必须拒绝（spec §1 禁止固定 offset）。
        assertTrue(c.epochMillisToLocalDateTime(0L, "+8").isFailure)
        assertTrue(c.epochMillisToLocalDateTime(0L, "+08:00").isFailure)
        c.close()
    }

    @Test
    fun displayTimezoneSetGetResetAndInvalidKeepsOld() {
        val c = newClient("tzConfig")
        assertTrue(c.setDisplayTimezone("UTC").isSuccess)
        assertEquals("UTC", c.getDisplayTimezone().getOrThrow())
        assertTrue(c.setDisplayTimezone("Asia/Ho_Chi_Minh").isSuccess)
        assertEquals("Asia/Ho_Chi_Minh", c.getDisplayTimezone().getOrThrow())
        // 非法 ID 拒绝且保留原配置。
        assertTrue(c.setDisplayTimezone("Not/AZone").isFailure)
        assertEquals("Asia/Ho_Chi_Minh", c.getDisplayTimezone().getOrThrow())
        // reset → 系统默认。
        assertTrue(c.resetDisplayTimezone().isSuccess)
        assertEquals(c.systemDefaultTimezoneId().getOrThrow(), c.getDisplayTimezone().getOrThrow())
        c.close()
    }

    @Test
    fun systemDefaultIsValidIana() {
        val c = newClient("sysDefault")
        val id = c.systemDefaultTimezoneId().getOrThrow()
        assertTrue(id.isNotEmpty())
        // 合法 IANA：能被 atom 接受。
        assertTrue(c.epochMillisToLocalDateTime(0L, id).isSuccess)
        c.close()
    }

    @Test
    fun messageTimeHhMm() {
        val c = newClient("msgTime")
        val ts = utcMillis(2026, 11, 12, 14, 5, 0)
        c.setDisplayTimezone("UTC").getOrThrow()
        assertEquals("14:05", c.formatMessageTime(ts).getOrThrow())
        c.setDisplayTimezone("Asia/Shanghai").getOrThrow()
        assertEquals("22:05", c.formatMessageTime(ts).getOrThrow())
        c.setDisplayTimezone("America/New_York").getOrThrow()
        assertEquals("09:05", c.formatMessageTime(ts).getOrThrow()) // EST -5
        c.close()
    }

    @Test
    fun nonPositiveReturnsEmpty() {
        val c = newClient("nonPositive")
        c.setDisplayTimezone("UTC").getOrThrow()
        assertEquals("", c.formatMessageTime(0L).getOrThrow())
        assertEquals("", c.formatMessageTime(-1L).getOrThrow())
        assertEquals("", c.formatConversationTime(0L).getOrThrow())
        assertEquals("", c.formatSeparatorTime(-5L).getOrThrow())
        c.close()
    }

    @Test
    fun conversationAndSeparatorOlderBranchesAreNowIndependent() {
        val c = newClient("olderBranches")
        c.setDisplayTimezone("UTC").getOrThrow()
        // 固定历史瞬间（2000-01-01 00:00 UTC）：相对真实 now 永远 >7 天且跨年。
        val old = utcMillis(2000, 1, 1, 0, 0, 0)
        assertEquals("01-01", c.formatConversationTime(old).getOrThrow())
        assertEquals("2000年01月01日 00:00", c.formatSeparatorTime(old).getOrThrow())
        c.close()
    }

    @Test
    fun dataModelTimestampsStayUtc() {
        val c = newClient("stayUtc")
        val millis = utcMillis(2026, 11, 12, 14, 5, 0)
        val sh = c.epochMillisToLocalDateTime(millis, "Asia/Shanghai").getOrThrow()
        // 上海分量回推 UTC 应正好比原值多 8h，证明转换是纯函数、未污染原始 UTC。
        val reconstructed = utcMillis(sh.year, sh.month, sh.day, sh.hour, sh.minute, sh.second)
        assertEquals(8 * 3_600_000L, reconstructed - millis)
        c.close()
    }
}