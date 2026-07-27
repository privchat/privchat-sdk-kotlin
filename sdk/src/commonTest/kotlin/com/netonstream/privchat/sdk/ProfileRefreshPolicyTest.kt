package com.netonstream.privchat.sdk

import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * C 批：资料刷新的 TTL / 退避 / 单飞。
 *
 * 生产背景（2026-07-26）：`getUserProfileLocalFirst` 只要本地有缓存，每次调用都会
 * 无条件起一次后台刷新——单个 target 每天 77 次、全局 17.8 万次/天。**只把来源修对
 * 不解决量**：请求会从「被闸口便宜地拒掉」变成「成功但昂贵」，所以必须同批上线。
 */
class ProfileRefreshPolicyTest {

    private class Clock(var now: Long = 0) { fun tick(ms: Long) { now += ms } }

    private fun policy(clock: Clock, ttl: Long = 1000, backoff: Long = 500, maxBackoff: Long = 4000) =
        ProfileRefreshPolicy(ttlMs = ttl, initialBackoffMs = backoff, maxBackoffMs = maxBackoff, nowMs = { clock.now })

    private fun key(id: ULong, ctx: ProfileAccessContext = ProfileAccessContext.Friend) =
        ProfileRefreshPolicy.Key(id, ctx.wireSource ?: "unknown")

    @Test
    fun `first refresh is admitted`() = runTest {
        val c = Clock(); val p = policy(c)
        assertTrue(p.admit(key(1u)) is ProfileRefreshPolicy.Admission.Proceed)
    }

    @Test
    fun `second refresh within ttl is skipped`() = runTest {
        val c = Clock(); val p = policy(c)
        p.admit(key(1u)); p.onSuccess(key(1u))
        c.tick(999)
        assertTrue(p.admit(key(1u)) is ProfileRefreshPolicy.Admission.Skip, "TTL 内不得重复刷新")
        c.tick(2)
        assertTrue(p.admit(key(1u)) is ProfileRefreshPolicy.Admission.Proceed)
    }

    @Test
    fun `concurrent callers join the in-flight request instead of duplicating it`() = runTest {
        val c = Clock(); val p = policy(c)
        val first = p.admit(key(7u))
        assertTrue(first is ProfileRefreshPolicy.Admission.Proceed)

        // cache miss 的后来者必须能等，而不是被简单拒绝（否则要么重复打服务器、要么白失败）
        val second = p.admit(key(7u))
        assertTrue(second is ProfileRefreshPolicy.Admission.JoinInFlight)
        val waiter = async { (second as ProfileRefreshPolicy.Admission.JoinInFlight).join(); true }
        p.onSuccess(key(7u))
        assertTrue(waiter.await(), "在途请求结束后，等待者必须被唤醒")
    }

    @Test
    fun `waiters are released when the in-flight request fails`() = runTest {
        val c = Clock(); val p = policy(c)
        p.admit(key(8u))
        val joined = p.admit(key(8u)) as ProfileRefreshPolicy.Admission.JoinInFlight
        val waiter = async { joined.join(); true }
        p.onFailure(key(8u))
        assertTrue(waiter.await(), "失败也必须唤醒等待者，否则会永久挂起")
    }

    @Test
    fun `different users do not block each other`() = runTest {
        val c = Clock(); val p = policy(c)
        assertTrue(p.admit(key(1u)) is ProfileRefreshPolicy.Admission.Proceed)
        assertTrue(p.admit(key(2u)) is ProfileRefreshPolicy.Admission.Proceed)
    }

    @Test
    fun `a failure in one source does not throttle another source`() = runTest {
        val c = Clock(); val p = policy(c)
        val friend = key(3u, ProfileAccessContext.Friend)
        val group = key(3u, ProfileAccessContext.Group(99u))
        p.admit(friend); p.onFailure(friend)
        assertTrue(p.admit(friend) is ProfileRefreshPolicy.Admission.Skip, "同来源应退避")
        assertTrue(
            p.admit(group) is ProfileRefreshPolicy.Admission.Proceed,
            "一个 friend 的错误请求不该把合法的 group 请求一起退避",
        )
    }

    @Test
    fun `failure backs off exponentially and is capped`() = runTest {
        val c = Clock(); val p = policy(c)
        val k = key(1u)
        p.admit(k); p.onFailure(k)
        assertTrue(p.admit(k) is ProfileRefreshPolicy.Admission.Skip)
        c.tick(500); assertTrue(p.admit(k) is ProfileRefreshPolicy.Admission.Proceed)
        p.onFailure(k)
        c.tick(999); assertTrue(p.admit(k) is ProfileRefreshPolicy.Admission.Skip)
        c.tick(1); assertTrue(p.admit(k) is ProfileRefreshPolicy.Admission.Proceed)
        repeat(10) {
            p.onFailure(k); c.tick(4000)
            assertTrue(p.admit(k) is ProfileRefreshPolicy.Admission.Proceed, "退避必须封顶，不能无限增长")
        }
    }

    @Test
    fun `invalidate clears every source of that user`() = runTest {
        val c = Clock(); val p = policy(c)
        val friend = key(1u, ProfileAccessContext.Friend)
        val conv = key(1u, ProfileAccessContext.Conversation(5u))
        p.admit(friend); p.onSuccess(friend)
        p.admit(conv); p.onSuccess(conv)
        assertTrue(p.admit(friend) is ProfileRefreshPolicy.Admission.Skip)
        p.invalidate(1u)
        assertTrue(p.admit(friend) is ProfileRefreshPolicy.Admission.Proceed, "资料变更后应能立即重取")
        assertTrue(p.admit(conv) is ProfileRefreshPolicy.Admission.Proceed)
    }

    @Test
    fun `unknown context has no wire source so no request can be made`() {
        assertNull(ProfileAccessContext.Unknown.wireSource)
        assertNull(ProfileAccessContext.Unknown.wireSourceId(42u))
    }

    @Test
    fun `typed contexts map to the server source matrix`() {
        assertEquals("conversation", ProfileAccessContext.Conversation(9u).wireSource)
        assertEquals("9", ProfileAccessContext.Conversation(9u).wireSourceId(42u))
        assertEquals("group", ProfileAccessContext.Group(5u).wireSource)
        assertEquals("friend", ProfileAccessContext.Friend.wireSource)
        assertEquals("search", ProfileAccessContext.Search("sess-1").wireSource)
        assertEquals("sess-1", ProfileAccessContext.Search("sess-1").wireSourceId(42u))
        // self 是 additive 新增的协议来源（DetailSourceType::SelfProfile）
        assertEquals("self", ProfileAccessContext.SelfLookup.wireSource)
        assertEquals("42", ProfileAccessContext.SelfLookup.wireSourceId(42u))
    }
}
