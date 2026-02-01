package io.privchat.sdk.kotlin.sample

import platform.Foundation.*
import platform.darwin.*

/**
 * 直播桥接（iOS 实现）
 * 
 * 提供 Kotlin 层对原生 LiveStream 控制器的访问
 */
actual object LiveStreamBridge {
    
    /**
     * 显示混合直播视图
     */
    actual fun showLiveStream(url: String, token: String) {
        NSLog("[LiveStreamBridge] 启动混合直播视图")
        NSLog("[LiveStreamBridge] URL: $url")
        
        // 调用注册的启动器
        HybridLiveStreamLauncherRegistry.launch(url, token)
    }
    
    /**
     * 设置静音状态
     */
    actual fun setMuted(muted: Boolean) {
        NSLog("[LiveStreamBridge] 设置静音: $muted")
        HybridLiveStreamControllerRegistry.setMuted(muted)
    }
    
    /**
     * 切换摄像头
     */
    actual fun switchCamera() {
        NSLog("[LiveStreamBridge] 切换摄像头")
        HybridLiveStreamControllerRegistry.switchCamera()
    }
    
    /**
     * 关闭直播
     */
    actual fun closeStream() {
        NSLog("[LiveStreamBridge] 关闭直播")
        HybridLiveStreamControllerRegistry.closeStream()
    }
}

/**
 * 混合直播启动器注册表
 * 
 * 此类用于与 Swift 交互，提供回调注册
 */
object HybridLiveStreamLauncherRegistry {
    private var launchCallback: ((String, String) -> Unit)? = null
    
    /**
     * 注册启动回调（由 Swift 调用）
     */
    fun register(callback: (String, String) -> Unit) {
        this.launchCallback = callback
        NSLog("[LauncherRegistry] 回调已注册")
    }
    
    /**
     * 启动混合视图（由 Kotlin 调用）
     */
    fun launch(url: String, token: String) {
        NSLog("[LauncherRegistry] 启动混合视图: $url")
        dispatch_async(dispatch_get_main_queue()) {
            launchCallback?.invoke(url, token) ?: run {
                NSLog("⚠️ [LauncherRegistry] 回调未注册")
            }
        }
    }
}

/**
 * 混合直播控制器注册表
 */
object HybridLiveStreamControllerRegistry {
    private var mutedCallback: ((Boolean) -> Unit)? = null
    private var switchCameraCallback: (() -> Unit)? = null
    private var closeStreamCallback: (() -> Unit)? = null
    
    fun registerMuted(callback: (Boolean) -> Unit) {
        mutedCallback = callback
        NSLog("[ControllerRegistry] setMuted 回调已注册")
    }
    
    fun registerSwitchCamera(callback: () -> Unit) {
        switchCameraCallback = callback
        NSLog("[ControllerRegistry] switchCamera 回调已注册")
    }
    
    fun registerCloseStream(callback: () -> Unit) {
        closeStreamCallback = callback
        NSLog("[ControllerRegistry] closeStream 回调已注册")
    }
    
    fun setMuted(muted: Boolean) {
        dispatch_async(dispatch_get_main_queue()) {
            mutedCallback?.invoke(muted) ?: NSLog("⚠️ [ControllerRegistry] setMuted 回调未注册")
        }
    }
    
    fun switchCamera() {
        dispatch_async(dispatch_get_main_queue()) {
            switchCameraCallback?.invoke() ?: NSLog("⚠️ [ControllerRegistry] switchCamera 回调未注册")
        }
    }
    
    fun closeStream() {
        dispatch_async(dispatch_get_main_queue()) {
            closeStreamCallback?.invoke() ?: NSLog("⚠️ [ControllerRegistry] closeStream 回调未注册")
        }
    }
}
