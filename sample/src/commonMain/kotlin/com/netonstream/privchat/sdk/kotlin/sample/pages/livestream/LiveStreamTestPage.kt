package com.netonstream.privchat.sdk.kotlin.sample.pages.livestream

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.utils.PlatformUtils
import com.tencent.kuikly.core.views.*
import com.netonstream.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import com.netonstream.privchat.sdk.kotlin.sample.LiveStreamBridge
import com.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 混合直播测试页面
 *
 * 统一使用混合直播模式（视频背景 + KuiklyUI 覆盖层）
 * iOS 和 Android 共享相同的 UI 代码
 */
@Page("LiveStreamTestPage")
internal class LiveStreamTestPage : MultiLingualPager() {

    private var theme by observable(ThemeManager.getTheme())

    // LiveKit 服务器地址
    private var serverUrl by observable("wss://turn.deviceadmin.net")

    // 选中的用户（默认 user001）
    private var selectedUser by observable("user001")

    // 用户选项列表 (10 个用户)
    private val userOptions = listOf(
        "user001" to "用户 1",
        "user002" to "用户 2",
        "user003" to "用户 3",
        "user004" to "用户 4",
        "user005" to "用户 5",
        "user006" to "用户 6",
        "user007" to "用户 7",
        "user008" to "用户 8",
        "user009" to "用户 9",
        "user010" to "用户 10"
    )

    // 连接状态
    private var connectionStatus by observable("选择用户后点击按钮启动直播")
    private var errorMessage by observable<String?>(null)

    /**
     * 根据选中的用户获取对应的 Token
     * 所有用户都在同一个房间: test-room
     * Token 有效期: 24000 小时 (约 1000 天)
     */
    private fun getAccessToken(): String {
        return when (selectedUser) {
            "user001" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NDM1NTQsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDAxIiwibmJmIjoxNzY5MjQzNTU0LCJzdWIiOiJ1c2VyMDAxIiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.UslzFns7aIb1-2vHtPagQtVWqFQRl2ngFHzzEu7AXZg"
            "user002" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NDM1NjUsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDAyIiwibmJmIjoxNzY5MjQzNTY1LCJzdWIiOiJ1c2VyMDAyIiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.q_6Vw3lWXP_LT98Ict308469pvcMUseFFjSuKYhWfQE"
            "user003" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYyODksImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDAzIiwibmJmIjoxNzY5MjU2Mjg5LCJzdWIiOiJ1c2VyMDAzIiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0._KYvy47YCDDkY3gz9g7KO2zIpI4XudVSq_86F5xFm8Y"
            "user004" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYyOTgsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA0IiwibmJmIjoxNzY5MjU2Mjk4LCJzdWIiOiJ1c2VyMDA0IiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.68k7Qxi-21zvmdGlK1RxrUzkDHjop_hv3YO6C86Rp7M"
            "user005" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMDMsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA1IiwibmJmIjoxNzY5MjU2MzAzLCJzdWIiOiJ1c2VyMDA1IiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.0NOr-NA2bhxvoWivGjjP6uVqYJtNeBG6NtIX-UbPkmQ"
            "user006" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMDgsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA2IiwibmJmIjoxNzY5MjU2MzA4LCJzdWIiOiJ1c2VyMDA2IiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.D-xZV5r7Mz2bz-4KZLDyRu3cE1y_BDlPAv9M2iG_mmY"
            "user007" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMTcsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA3IiwibmJmIjoxNzY5MjU2MzE3LCJzdWIiOiJ1c2VyMDA3IiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.jzdhuEiEt4Xp28Ya1ASKbzdmx8lyNyEIo08eihFzbag"
            "user008" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMjQsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA4IiwibmJmIjoxNzY5MjU2MzI0LCJzdWIiOiJ1c2VyMDA4IiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.fAvniJ-BHD9cYpYTSGYylrxObLjTuW-2h-_QhZsOfWU"
            "user009" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMjgsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDA5ICIsIm5iZiI6MTc2OTI1NjMyOCwic3ViIjoidXNlcjAwOSAiLCJ2aWRlbyI6eyJyb29tIjoidGVzdC1yb29tIiwicm9vbUpvaW4iOnRydWV9fQ.oPUdCPA7c2nCFhZQOHuLAR1B52C2OTtXC4fCfYoDsFo"
            "user010" -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NTU2NTYzMzUsImlzcyI6ImFwcGtleSIsIm5hbWUiOiJ1c2VyMDEwIiwibmJmIjoxNzY5MjU2MzM1LCJzdWIiOiJ1c2VyMDEwIiwidmlkZW8iOnsicm9vbSI6InRlc3Qtcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.ZecUJ1Et-L4xy9gxPnnyJMEA8aEG-d1qnNswaqdRam0"
            else -> ""
        }
    }

    /**
     * 顶部导航栏
     */
    private fun topNavBar(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    paddingTop(getPager().pageData.statusBarHeight)
                    backgroundColor(ctx.theme.colors.topBarBackground)
                }
                View {
                    attr {
                        height(44f)
                        flexDirectionRow()
                        alignItemsCenter()
                    }

                    // 返回按钮
                    View {
                        attr {
                            width(44f)
                            height(44f)
                            allCenter()
                        }
                        event {
                            click {
                                getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME).closePage()
                            }
                        }
                        Text {
                            attr {
                                text("←")
                                fontSize(20f)
                                color(ctx.theme.colors.topBarTextFocused)
                            }
                        }
                    }

                    // 标题
                    Text {
                        attr {
                            flex(1f)
                            text("混合直播测试")
                            color(ctx.theme.colors.topBarTextFocused)
                            fontSize(18f)
                            fontWeightBold()
                            textAlignCenter()
                        }
                    }

                    // 占位（保持居中）
                    View {
                        attr {
                            width(44f)
                            height(44f)
                        }
                    }
                }
            }
        }
    }

    /**
     * 输入表单
     */
    private fun inputFormView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    paddingLeft(20f)
                    paddingRight(20f)
                    paddingTop(20f)
                }

                // 服务器地址输入框
                View {
                    attr {
                        marginBottom(16f)
                    }
                    Text {
                        attr {
                            text("服务器地址")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                            marginBottom(8f)
                        }
                    }
                    View {
                        attr {
                            height(44f)
                            paddingLeft(12f)
                            paddingRight(12f)
                            borderRadius(8f)
                            backgroundColor(ctx.theme.colors.feedBackground)
                            flexDirectionRow()
                            alignItemsCenter()
                        }
                        Text {
                            attr {
                                text(ctx.serverUrl)
                                fontSize(14f)
                                color(ctx.theme.colors.feedContentText)
                                flex(1f)
                            }
                        }
                    }
                }

                // 当前用户信息
                View {
                    attr {
                        marginBottom(16f)
                    }
                    Text {
                        attr {
                            text("当前用户")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                            marginBottom(8f)
                        }
                    }
                    View {
                        attr {
                            height(44f)
                            paddingLeft(12f)
                            paddingRight(12f)
                            borderRadius(8f)
                            backgroundColor(ctx.theme.colors.feedBackground)
                            flexDirectionRow()
                            alignItemsCenter()
                        }
                        Text {
                            attr {
                                text("当前用户: ${ctx.selectedUser}")
                                fontSize(14f)
                                color(ctx.theme.colors.feedContentText)
                                fontWeightBold()
                            }
                        }
                    }
                }

                // Access Token (折叠显示)
                View {
                    attr {
                        marginBottom(16f)
                    }
                    Text {
                        attr {
                            text("Access Token")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                            marginBottom(8f)
                        }
                    }
                    View {
                        attr {
                            minHeight(44f)
                            paddingLeft(12f)
                            paddingRight(12f)
                            paddingTop(12f)
                            paddingBottom(12f)
                            borderRadius(8f)
                            backgroundColor(ctx.theme.colors.feedBackground)
                        }
                        Text {
                            attr {
                                text("${ctx.getAccessToken().take(50)}...")
                                fontSize(12f)
                                color(Color(0xFF999999))
                                lineHeight(18f)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 状态显示
     */
    private fun statusView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    paddingLeft(20f)
                    paddingRight(20f)
                    paddingTop(10f)
                    paddingBottom(10f)
                }

                View {
                    attr {
                        paddingLeft(16f)
                        paddingRight(16f)
                        paddingTop(12f)
                        paddingBottom(12f)
                        borderRadius(8f)
                        backgroundColor(
                            if (ctx.errorMessage != null) {
                                Color(0xFFFFEBEE)
                            } else {
                                Color(0xFFE3F2FD)
                            }
                        )
                    }

                    Text {
                        attr {
                            text(ctx.connectionStatus)
                            fontSize(14f)
                            color(
                                if (ctx.errorMessage != null) {
                                    Color(0xFFD32F2F)
                                } else {
                                    Color(0xFF1976D2)
                                }
                            )
                            lineHeight(20f)
                        }
                    }
                }
            }
        }
    }

    /**
     * 操作按钮区域
     */
    private fun actionButtonsView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    paddingLeft(20f)
                    paddingRight(20f)
                    paddingTop(20f)
                    flexDirectionColumn()
                }

                // 用户选择区域
                View {
                    attr {
                        marginBottom(16f)
                        paddingLeft(16f)
                        paddingRight(16f)
                        paddingTop(12f)
                        paddingBottom(12f)
                        borderRadius(12f)
                        backgroundColor(Color(0x20000000))
                    }

                    // 标题
                    Text {
                        attr {
                            text("选择测试用户：")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                            marginBottom(12f)
                        }
                    }

                    // 用户选项按钮（5 列 x 2 行网格布局）
                    View {
                        attr {
                            flexDirectionColumn()
                        }

                        // 第一行：user001 - user005
                        View {
                            attr {
                                flexDirectionRow()
                                justifyContentCenter()
                                marginBottom(6f)
                            }

                            ctx.userOptions.take(5).forEachIndexed { index, (userId, userName) ->
                                View {
                                    attr {
                                        height(36f)
                                        width(60f)
                                        borderRadius(18f)
                                        allCenter()
                                        marginRight(if (index == 4) 0f else 6f)
                                        backgroundColor(
                                            if (ctx.selectedUser == userId) Color(0xFF2196F3)
                                            else Color(0x30000000)
                                        )
                                    }
                                    event {
                                        click {
                                            ctx.selectedUser = userId
                                            println("🔑 [LiveStreamTestPage] 切换用户: $userId")
                                        }
                                    }
                                    Text {
                                        attr {
                                            text(userName)
                                            fontSize(13f)
                                            color(
                                                if (ctx.selectedUser == userId) Color.WHITE
                                                else ctx.theme.colors.feedContentText
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // 第二行：user006 - user010
                        View {
                            attr {
                                flexDirectionRow()
                                justifyContentCenter()
                            }

                            ctx.userOptions.drop(5).forEachIndexed { index, (userId, userName) ->
                                View {
                                    attr {
                                        height(36f)
                                        width(60f)
                                        borderRadius(18f)
                                        allCenter()
                                        marginRight(if (index == 4) 0f else 6f)
                                        backgroundColor(
                                            if (ctx.selectedUser == userId) Color(0xFF2196F3)
                                            else Color(0x30000000)
                                        )
                                    }
                                    event {
                                        click {
                                            ctx.selectedUser = userId
                                            println("🔑 [LiveStreamTestPage] 切换用户: $userId")
                                        }
                                    }
                                    Text {
                                        attr {
                                            text(userName)
                                            fontSize(13f)
                                            color(
                                                if (ctx.selectedUser == userId) Color.WHITE
                                                else ctx.theme.colors.feedContentText
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 当前选中用户提示
                    Text {
                        attr {
                            text("当前: ${ctx.selectedUser}")
                            fontSize(12f)
                            color(Color(0xFF999999))
                            marginTop(8f)
                        }
                    }
                }

                // 启动混合直播按钮
                View {
                    attr {
                        height(56f)
                        borderRadius(28f)
                        backgroundColor(Color(0xFF2196F3))
                        allCenter()
                    }
                    event {
                        click {
                            ctx.launchHybridLiveStream()
                        }
                    }
                    Text {
                        attr {
                            text("🎬 启动混合直播")
                            fontSize(18f)
                            color(Color.WHITE)
                            fontWeightBold()
                        }
                    }
                }
            }
        }
    }

    /**
     * 使用说明
     */
    private fun instructionsView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    paddingLeft(20f)
                    paddingRight(20f)
                    paddingTop(30f)
                }

                View {
                    attr {
                        paddingLeft(16f)
                        paddingRight(16f)
                        paddingTop(16f)
                        paddingBottom(16f)
                        borderRadius(12f)
                        backgroundColor(Color(0xFFF5F5F5))
                    }

                    Text {
                        attr {
                            text("""
                                📝 使用说明

                                ✅ 架构特点：
                                • 底层：LiveKit 原生视频渲染
                                • 上层：KuiklyUI 透明覆盖层
                                • UI 代码跨平台共享

                                🎯 功能特点：
                                • 自动连接 LiveKit 服务器
                                • 摄像头和麦克风自动启用
                                • 支持前后摄像头切换
                                • 支持静音控制

                                🔄 测试用户：
                                • iOS: user001
                                • Android: user002
                                • 房间: test-room

                                💡 提示：
                                在真机上测试以查看完整的视频效果
                            """.trimIndent())
                            fontSize(13f)
                            color(Color(0xFF666666))
                            lineHeight(22f)
                        }
                    }
                }
            }
        }
    }

    /**
     * 启动混合直播
     */
    private fun launchHybridLiveStream() {
        // 获取当前选中用户的 token
        val accessToken = getAccessToken()

        if (serverUrl.isEmpty() || accessToken.isEmpty()) {
            connectionStatus = "❌ 服务器地址或令牌为空"
            errorMessage = "参数错误"
            return
        }

        if (serverUrl.length < 6 || !serverUrl.startsWith("wss://")) {
            connectionStatus = "❌ 服务器地址格式错误（需要 wss:// 开头）"
            errorMessage = "格式错误"
            return
        }

        errorMessage = null
        connectionStatus = "🚀 正在启动混合直播 (${selectedUser})..."

        println("🎬 [LiveStreamTestPage] 启动混合直播")
        println("   用户: $selectedUser")
        println("   服务器: $serverUrl")
        println("   Token: ${accessToken.take(50)}...")

        // 启动混合直播视图
        LiveStreamBridge.showLiveStream(
            url = serverUrl,
            token = accessToken
        )
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                backgroundColor(ctx.theme.colors.background)
                flexDirectionColumn()
            }

            // 顶部导航栏
            ctx.topNavBar().invoke(this)

            // 内容区域
            View {
                attr {
                    flex(1f)
                    flexDirectionColumn()
                }

                // 输入表单
                ctx.inputFormView().invoke(this)

                // 状态显示
                ctx.statusView().invoke(this)

                // 操作按钮
                ctx.actionButtonsView().invoke(this)

                // 使用说明
                ctx.instructionsView().invoke(this)
            }
        }
    }
}
