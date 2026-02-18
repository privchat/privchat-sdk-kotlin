package om.netonstream.privchat.sdk.kotlin.sample.pages.livestream

import com.tencent.kuikly.core.render.android.scheduler.KuiklyRenderCoreContextScheduler

/**
 * Android 平台的 UI 更新调度实现
 * 
 * 使用 KuiklyRenderCoreContextScheduler 确保在正确的线程上更新 UI
 */
actual fun platformScheduleUIUpdate(action: () -> Unit) {
    // 使用 KuiklyUI 的上下文调度器，延迟 0ms 立即执行
    KuiklyRenderCoreContextScheduler.scheduleTask(0L) {
        action()
    }
}
