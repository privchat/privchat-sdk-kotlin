package om.netonstream.privchat.sdk.kotlin.sample.privchat

import kotlinx.coroutines.CoroutineDispatcher

/**
 * 用于登录等用户主动触发的 SDK 调用调度器。
 * - iOS：必须在 Kuikly context 队列（主线程）上触发 UI 相关状态更新，避免 Render 线程断言。
 * - Android：与 Default 一致即可。
 */
expect val LoginCoroutineDispatcher: CoroutineDispatcher
