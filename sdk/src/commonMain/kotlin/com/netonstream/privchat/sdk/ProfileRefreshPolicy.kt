package com.netonstream.privchat.sdk

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 资料拉取的准入与合流：**TTL + 失败退避 + 真·singleflight**。
 *
 * 原实现只要本地有缓存就每次调用都 `launch` 一次后台刷新，没有任何节流：
 * 生产上单个 target 每天被刷 77 次、全局 17.8 万次/天（2026-07-26 日志）。
 * 只把来源修对不解决量 —— 请求会从「被闸口便宜地拒掉」变成「成功但昂贵」，
 * 所以这一层必须与来源修正同批上线。
 *
 * 三个必须点（评审返工）：
 * 1. **线程安全**：Android 后台协程 + K/N 新内存模型都可能并发调用，用 [Mutex] 串行化状态。
 * 2. **cache miss 也要合流**：miss 路径需要同步结果，只返回「不许发」没用；后来者要能
 *    `await` 同一个在途请求，否则要么重复打服务器、要么白白失败。
 * 3. **按 (userId, scope) 隔离**：不同来源的权限结果不同（search 可能解锁 username、
 *    friend 与 group 字段权限不同、conversation 的 can_add_friend 取决于频道）。
 *    一个 friend 的错误请求不该把同一用户的合法 group 请求退避 30 分钟。
 */
class ProfileRefreshPolicy(
    private val ttlMs: Long = DEFAULT_TTL_MS,
    private val initialBackoffMs: Long = DEFAULT_INITIAL_BACKOFF_MS,
    private val maxBackoffMs: Long = DEFAULT_MAX_BACKOFF_MS,
    private val nowMs: () -> Long,
) {
    /** 刷新键：同一用户在不同来源下是**不同**的权限结果，不能互相污染。 */
    data class Key(val userId: ULong, val scope: String)

    private data class Entry(
        /** null = 从未成功过；用 null 而不是 0 作哨兵，否则「时间戳恰好为 0」会被当成没刷过。 */
        val lastSuccessAt: Long? = null,
        val failureCount: Int = 0,
        val nextAttemptAt: Long = 0,
        /** 非 null = 有一个请求在飞，后来者应 await 它而不是重复发。 */
        val inFlight: CompletableDeferred<Unit>? = null,
    )

    private val mutex = Mutex()
    private val entries = mutableMapOf<Key, Entry>()

    sealed interface Admission {
        /** 调用方应发起请求，结束后必须调用 [onSuccess] / [onFailure]。 */
        data object Proceed : Admission

        /** 已有同键请求在飞；[join] 挂起直到它结束（不保证成功，仅表示「不必重复发」）。 */
        data class JoinInFlight(private val signal: CompletableDeferred<Unit>) : Admission {
            suspend fun join() = signal.join()
        }

        /** TTL 内或处于退避窗口，本次不该发请求。 */
        data object Skip : Admission
    }

    fun key(userId: ULong, context: ProfileAccessContext): Key =
        Key(userId, context.wireSource ?: "unknown")

    suspend fun admit(key: Key): Admission = mutex.withLock {
        val now = nowMs()
        val e = entries[key] ?: Entry()
        e.inFlight?.let { return@withLock Admission.JoinInFlight(it) }
        if (e.failureCount > 0 && now < e.nextAttemptAt) return@withLock Admission.Skip
        val last = e.lastSuccessAt
        if (e.failureCount == 0 && last != null && now - last < ttlMs) return@withLock Admission.Skip
        entries[key] = e.copy(inFlight = CompletableDeferred())
        Admission.Proceed
    }

    suspend fun onSuccess(key: Key) {
        val signal = mutex.withLock {
            val prev = entries[key]
            entries[key] = Entry(lastSuccessAt = nowMs(), failureCount = 0, nextAttemptAt = 0, inFlight = null)
            prev?.inFlight
        }
        signal?.complete(Unit)
    }

    /** 失败按指数退避，封顶 [maxBackoffMs]；避免被拒绝的请求变成死循环。 */
    suspend fun onFailure(key: Key) {
        val signal = mutex.withLock {
            val e = entries[key] ?: Entry()
            val failures = e.failureCount + 1
            var delay = initialBackoffMs
            repeat(failures - 1) { delay = (delay * 2).coerceAtMost(maxBackoffMs) }
            entries[key] = e.copy(
                failureCount = failures,
                nextAttemptAt = nowMs() + delay.coerceAtMost(maxBackoffMs),
                inFlight = null,
            )
            e.inFlight
        }
        signal?.complete(Unit)
    }

    /**
     * 资料被外部事件（实体增量同步 / 资料变更推送）更新后，允许立刻重新拉取。
     * 清掉该用户在**所有来源**下的节流状态。
     */
    suspend fun invalidate(userId: ULong) {
        val signals = mutex.withLock {
            val hit = entries.keys.filter { it.userId == userId }
            val pending = hit.mapNotNull { entries[it]?.inFlight }
            hit.forEach { entries.remove(it) }
            pending
        }
        signals.forEach { it.complete(Unit) }
    }

    companion object {
        const val DEFAULT_TTL_MS: Long = 5 * 60 * 1000
        const val DEFAULT_INITIAL_BACKOFF_MS: Long = 30 * 1000
        const val DEFAULT_MAX_BACKOFF_MS: Long = 30 * 60 * 1000
    }
}
