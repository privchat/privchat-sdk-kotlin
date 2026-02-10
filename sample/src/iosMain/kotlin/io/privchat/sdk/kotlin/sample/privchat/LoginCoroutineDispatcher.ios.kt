package io.privchat.sdk.kotlin.sample.privchat

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import kotlin.coroutines.CoroutineContext

/**
 * 登录流程（connect/login 等）放在 QOS_CLASS_DEFAULT，避免「User-interactive 等 Default」的优先级反转：
 * Rust connect() 内部会 block_on/recv，若本协程跑在 USER_INITIATED，会形成高 QoS 等低 QoS 的 Rust 工作线程。
 * 使用 Default 后，阻塞发生在 Default 线程上，与 Rust 侧一致。
 */
private object DefaultQosDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        // QOS_CLASS_DEFAULT = 0x15 (21)
        dispatch_async(dispatch_get_global_queue(21, 0u)) {
            block.run()
        }
    }
}

actual val LoginCoroutineDispatcher: CoroutineDispatcher = DefaultQosDispatcher
