package io.privchat.sdk.kotlin.sample.pages.livestream

import io.privchat.sdk.kotlin.sample.KuiklyContextScheduler

/**
 * iOS 平台的 UI 更新调度实现
 * 
 * 使用 KuiklyRenderThreadManager 确保在正确的线程上更新 UI
 * 调度器由 Swift 层通过 setIOSScheduler() 注册
 */

// 可配置的 iOS 调度器（由 Swift 层注入）
private var iosScheduler: ((action: () -> Unit) -> Unit)? = null

/**
 * 设置 iOS 调度器（从 Swift 层调用）。
 * 同时写入 KuiklyContextScheduler，供 runOnKuiklyContext / LoginPage 等使用。
 */
fun setIOSScheduler(scheduler: (action: () -> Unit) -> Unit) {
    iosScheduler = scheduler
    KuiklyContextScheduler.scheduler = scheduler
}

/**
 * iOS 平台的 UI 更新调度
 */
actual fun platformScheduleUIUpdate(action: () -> Unit) {
    val scheduler = iosScheduler
    if (scheduler != null) {
        // 使用注册的调度器（KuiklyRenderThreadManager.performOnContextQueue）
        scheduler(action)
    } else {
        // 降级：直接执行（首次调用时可能还未注册）
        try {
            action()
        } catch (e: Exception) {
            // 忽略错误
        }
    }
}
