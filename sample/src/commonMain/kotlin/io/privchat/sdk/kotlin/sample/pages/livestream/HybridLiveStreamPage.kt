package io.privchat.sdk.kotlin.sample.pages.livestream

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.*
import io.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import io.privchat.sdk.kotlin.sample.LiveStreamBridge
import io.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 混合直播页面（调试版本）
 * 
 * 架构：
 * - 底层：原生 VideoView（全屏视频背景）
 * - 上层：本页面（透明背景 + 调试UI）
 * 
 * 显示内容：
 * 1. 测试提示框
 * 2. LiveKit 连接日志
 * 3. 关闭按钮
 */
@Page("HybridLiveStreamPage")
internal class HybridLiveStreamPage : MultiLingualPager() {
    
    companion object {
        // 当前活动的页面实例（用于原生层更新 UI）
        private var currentInstance: HybridLiveStreamPage? = null
        
        /**
         * 获取当前页面实例（公共 API，供原生层调用）
         */
        fun getCurrentPage(): HybridLiveStreamPage? = currentInstance
    }
    
    init {
        // 注册当前实例
        currentInstance = this
        println("📱 [HybridLiveStreamPage] 页面实例已创建并注册")
    }
    
    private var theme by observable(ThemeManager.getTheme())
    
    // 连接状态
    private var connectionStatus by observable("正在初始化...")
    private var roomInfo by observable("")
    private var participantsCount by observable(0)
    private var connectionQuality by observable("未知")  // 连接质量
    
    // 日志列表（最多显示 8 条）
    private var logs by observable<List<String>>(listOf(
        "📱 调试界面已加载",
        "🎥 视频背景由原生层提供",
        "📦 UI 由 KuiklyUI 渲染（跨平台）"
    ))
    
    // 日志计数器（用于简单排序）
    private var logCounter by observable(0)
    
    /**
     * 添加日志
     */
    fun addLog(message: String) {
        logCounter++
        val newLog = "[$logCounter] $message"
        logs = (listOf(newLog) + logs).take(8)
    }
    
    /**
     * 更新连接状态
     */
    fun updateConnectionStatus(status: String) {
        connectionStatus = status
        addLog("📡 $status")
    }
    
    /**
     * 更新房间信息
     */
    fun updateRoomInfo(info: String) {
        roomInfo = info
        addLog("🏠 $info")
    }
    
    /**
     * 更新参与者数量
     */
    fun updateParticipants(count: Int) {
        participantsCount = count
        addLog("👥 参与者: $count")
    }
    
    /**
     * 更新连接质量
     */
    fun updateConnectionQuality(quality: String) {
        connectionQuality = quality
        addLog("📊 连接质量: $quality")
    }
    
    /**
     * 顶部测试信息框
     */
    private fun testInfoBox(): ViewBuilder {
        return {
            View {
                attr {
                    marginTop(getPager().pageData.statusBarHeight + 20f)
                    marginLeft(16f)
                    marginRight(16f)
                    paddingLeft(16f)
                    paddingRight(16f)
                    paddingTop(12f)
                    paddingBottom(12f)
                    borderRadius(12f)
                    backgroundColor(Color(0x80000000))  // 半透明黑色
                }
                
                Text {
                    attr {
                        text("🎥 混合直播测试")
                        fontSize(16f)
                        color(Color.WHITE)
                        fontWeightBold()
                        marginBottom(8f)
                    }
                }
                
                Text {
                    attr {
                        text("底层：LiveKit 原生视频渲染")
                        fontSize(13f)
                        color(Color(0xFFCCCCCC))
                        marginBottom(4f)
                    }
                }
                
                Text {
                    attr {
                        text("上层：KuiklyUI 跨平台覆盖层")
                        fontSize(13f)
                        color(Color(0xFFCCCCCC))
                    }
                }
            }
        }
    }
    
    /**
     * 连接状态显示
     */
    private fun connectionStatusView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    marginTop(16f)
                    marginLeft(16f)
                    marginRight(16f)
                    paddingLeft(16f)
                    paddingRight(16f)
                    paddingTop(10f)
                    paddingBottom(10f)
                    borderRadius(8f)
                    backgroundColor(Color(0x80000000))
                }
                
                // 连接状态
                Text {
                    attr {
                        text(ctx.connectionStatus)
                        fontSize(14f)
                        color(Color(0xFF4CAF50))
                        fontWeightBold()
                        marginBottom(6f)
                    }
                }
                
                // 房间信息
                if (ctx.roomInfo.isNotEmpty()) {
                    Text {
                        attr {
                            text(ctx.roomInfo)
                            fontSize(12f)
                            color(Color(0xFFCCCCCC))
                            marginBottom(4f)
                        }
                    }
                }
                
                // 参与者信息
                Text {
                    attr {
                        text("参与者: ${ctx.participantsCount}")
                        fontSize(12f)
                        color(Color(0xFFCCCCCC))
                        marginBottom(4f)
                    }
                }
                
                // 连接质量
                Text {
                    attr {
                        text("连接质量: ${ctx.connectionQuality}")
                        fontSize(12f)
                        color(when {
                            ctx.connectionQuality.contains("优秀") || ctx.connectionQuality.contains("EXCELLENT") -> Color(0xFF4CAF50)
                            ctx.connectionQuality.contains("良好") || ctx.connectionQuality.contains("GOOD") -> Color(0xFF8BC34A)
                            ctx.connectionQuality.contains("较差") || ctx.connectionQuality.contains("POOR") -> Color(0xFFFF9800)
                            ctx.connectionQuality.contains("断开") || ctx.connectionQuality.contains("LOST") -> Color(0xFFF44336)
                            else -> Color(0xFFCCCCCC)
                        })
                    }
                }
            }
        }
    }
    
    /**
     * 日志显示区域
     */
    private fun logView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    marginTop(16f)
                    marginLeft(16f)
                    marginRight(16f)
                    paddingLeft(12f)
                    paddingRight(12f)
                    paddingTop(10f)
                    paddingBottom(10f)
                    borderRadius(8f)
                    backgroundColor(Color(0x80000000))
                    maxHeight(200f)
                }
                
                Text {
                    attr {
                        text("📋 实时日志")
                        fontSize(13f)
                        color(Color.WHITE)
                        fontWeightBold()
                        marginBottom(8f)
                    }
                }
                
                // 日志列表
                for (log in ctx.logs) {
                    Text {
                        attr {
                            text(log)
                            fontSize(11f)
                            color(Color(0xFFCCCCCC))
                            marginBottom(4f)
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 控制按钮（翻转摄像头 + 关闭）
     */
    private fun controlButtons(): ViewBuilder {
        return {
            View {
                attr {
                    flexDirectionRow()
                    justifyContentCenter()
                    marginTop(20f)
                    marginBottom(40f)
                }
                
                // 翻转摄像头按钮
                View {
                    attr {
                        width(120f)
                        height(44f)
                        borderRadius(22f)
                        backgroundColor(Color(0xCC2196F3))  // 半透明蓝色
                        allCenter()
                        marginRight(12f)
                    }
                    event {
                        click {
                            LiveStreamBridge.switchCamera()
                        }
                    }
                    
                    Text {
                        attr {
                            text("🔄 翻转")
                            fontSize(16f)
                            color(Color.WHITE)
                            fontWeightBold()
                        }
                    }
                }
                
                // 关闭按钮
                View {
                    attr {
                        width(120f)
                        height(44f)
                        borderRadius(22f)
                        backgroundColor(Color(0xCCF44336))  // 半透明红色
                        allCenter()
                    }
                    event {
                        click {
                            LiveStreamBridge.closeStream()
                        }
                    }
                    
                    Text {
                        attr {
                            text("❌ 关闭")
                            fontSize(16f)
                            color(Color.WHITE)
                            fontWeightBold()
                        }
                    }
                }
            }
        }
    }
    
    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                backgroundColor(Color.TRANSPARENT)  // 完全透明
                flexDirectionColumn()
            }
            
            // 顶部测试提示框
            ctx.testInfoBox().invoke(this)
            
            // 连接状态
            ctx.connectionStatusView().invoke(this)
            
            // 日志显示
            ctx.logView().invoke(this)
            
            // 占位空间（将关闭按钮推到底部）
            View {
                attr {
                    flex(1f)
                }
            }
            
            // 底部控制按钮
            ctx.controlButtons().invoke(this)
        }
    }
}
