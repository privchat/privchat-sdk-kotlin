package io.privchat.sdk.kotlin.sample.privchat

import kotlinx.coroutines.CoroutineDispatcher

/**
 * 用于登录等用户主动触发的阻塞型 SDK 调用的协程调度器。
 * - iOS：使用 QOS_CLASS_USER_INITIATED 的 GCD 队列，避免 User-initiated 线程等待 Default 线程导致的优先级反转。
 * - Android：与 Default 一致即可。
 */
expect val LoginCoroutineDispatcher: CoroutineDispatcher
