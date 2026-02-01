package io.privchat.sdk.kotlin.sample.pages.livestream

/**
 * HybridLiveStreamPage 的公共桥接（供原生层调用）
 * 
 * 由于 HybridLiveStreamPage 是 internal 的，原生层无法直接访问，
 * 因此创建这个公共对象来提供 UI 更新接口
 * 
 * 这些方法可以在任何线程上安全调用
 */
object HybridLiveStreamPageBridge {
    
    /**
     * 更新连接状态（线程安全）
     */
    fun updateConnectionStatus(status: String) {
        scheduleUIUpdate {
            HybridLiveStreamPage.getCurrentPage()?.updateConnectionStatus(status)
        }
    }
    
    /**
     * 更新房间信息（线程安全）
     */
    fun updateRoomInfo(info: String) {
        scheduleUIUpdate {
            HybridLiveStreamPage.getCurrentPage()?.updateRoomInfo(info)
        }
    }
    
    /**
     * 更新参与者数量（线程安全）
     */
    fun updateParticipants(count: Int) {
        scheduleUIUpdate {
            HybridLiveStreamPage.getCurrentPage()?.updateParticipants(count)
        }
    }
    
    /**
     * 更新连接质量（线程安全）
     */
    fun updateConnectionQuality(quality: String) {
        scheduleUIUpdate {
            HybridLiveStreamPage.getCurrentPage()?.updateConnectionQuality(quality)
        }
    }
    
    /**
     * 添加日志（线程安全）
     */
    fun addLog(message: String) {
        scheduleUIUpdate {
            HybridLiveStreamPage.getCurrentPage()?.addLog(message)
        }
    }
    
    /**
     * 调度 UI 更新到 KuiklyUI 的渲染线程
     */
    private fun scheduleUIUpdate(action: () -> Unit) {
        try {
            // Android/iOS 平台会有各自的实现
            platformScheduleUIUpdate(action)
        } catch (e: Exception) {
            // 降级：直接执行（可能会有线程问题，但至少不会崩溃）
            try {
                action()
            } catch (ex: Exception) {
                // 忽略
            }
        }
    }
}

/**
 * 平台特定的 UI 更新调度（expect 声明）
 */
expect fun platformScheduleUIUpdate(action: () -> Unit)
