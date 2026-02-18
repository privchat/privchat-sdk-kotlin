package om.netonstream.privchat.sdk.kotlin.sample

/**
 * 直播桥接（跨平台）
 * 
 * 提供统一的直播控制接口
 */
expect object LiveStreamBridge {
    /**
     * 显示混合直播视图
     */
    fun showLiveStream(url: String, token: String)
    
    /**
     * 设置静音状态
     */
    fun setMuted(muted: Boolean)
    
    /**
     * 切换摄像头
     */
    fun switchCamera()
    
    /**
     * 关闭直播
     */
    fun closeStream()
}
