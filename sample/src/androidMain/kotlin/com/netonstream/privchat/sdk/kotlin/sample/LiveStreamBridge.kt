package om.netonstream.privchat.sdk.kotlin.sample

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log

/**
 * 直播桥接（Android 实现）
 * 
 * 提供 Android 混合直播功能
 */
actual object LiveStreamBridge {
    
    @SuppressLint("StaticFieldLeak")
    private var applicationContext: Application? = null
    private var currentActivity: Activity? = null
    
    // 控制回调
    private var mutedCallback: ((Boolean) -> Unit)? = null
    private var switchCameraCallback: (() -> Unit)? = null
    private var closeStreamCallback: (() -> Unit)? = null
    
    /**
     * 初始化 - 由 Application 调用
     */
    fun initialize(app: Application) {
        applicationContext = app
        Log.i("LiveStreamBridge", "✅ Android LiveStreamBridge 已初始化")
    }
    
    /**
     * 设置当前 Activity - 由 MainActivity 调用
     */
    fun setCurrentActivity(activity: Activity?) {
        currentActivity = activity
    }
    
    /**
     * 注册控制回调 - 由 LiveStreamActivity 调用
     */
    fun registerMuted(callback: (Boolean) -> Unit) {
        mutedCallback = callback
        Log.i("LiveStreamBridge", "✅ setMuted 回调已注册")
    }
    
    fun registerSwitchCamera(callback: () -> Unit) {
        switchCameraCallback = callback
        Log.i("LiveStreamBridge", "✅ switchCamera 回调已注册")
    }
    
    fun registerCloseStream(callback: () -> Unit) {
        closeStreamCallback = callback
        Log.i("LiveStreamBridge", "✅ closeStream 回调已注册")
    }
    
    /**
     * 显示混合直播视图
     */
    actual fun showLiveStream(url: String, token: String) {
        Log.i("LiveStreamBridge", "========================================")
        Log.i("LiveStreamBridge", "Android 混合直播启动")
        Log.i("LiveStreamBridge", "URL: $url")
        Log.i("LiveStreamBridge", "Token: ${token.take(50)}...")
        Log.i("LiveStreamBridge", "========================================")
        
        try {
            val context = currentActivity ?: applicationContext
            if (context == null) {
                Log.e("LiveStreamBridge", "❌ 无法获取 Context")
                return
            }
            
            // 启动 LiveStreamActivity
            val intent = Intent(context, Class.forName("om.netonstream.privchat.sdk.kotlin.sample.LiveStreamActivity")).apply {
                putExtra("url", url)
                putExtra("token", token)
                if (context == applicationContext) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            context.startActivity(intent)
            Log.i("LiveStreamBridge", "✅ LiveStreamActivity 启动成功")
            
        } catch (e: Exception) {
            Log.e("LiveStreamBridge", "❌ 启动 LiveStreamActivity 失败", e)
        }
    }
    
    /**
     * 设置静音
     */
    actual fun setMuted(muted: Boolean) {
        Log.i("LiveStreamBridge", "设置静音: $muted")
        mutedCallback?.invoke(muted) ?: Log.w("LiveStreamBridge", "⚠️ setMuted 回调未注册")
    }
    
    /**
     * 切换摄像头
     */
    actual fun switchCamera() {
        Log.i("LiveStreamBridge", "切换摄像头")
        switchCameraCallback?.invoke() ?: Log.w("LiveStreamBridge", "⚠️ switchCamera 回调未注册")
    }
    
    /**
     * 关闭直播
     */
    actual fun closeStream() {
        Log.i("LiveStreamBridge", "关闭直播")
        closeStreamCallback?.invoke() ?: Log.w("LiveStreamBridge", "⚠️ closeStream 回调未注册")
    }
}
