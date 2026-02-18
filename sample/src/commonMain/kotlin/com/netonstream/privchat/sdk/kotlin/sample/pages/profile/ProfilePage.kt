package om.netonstream.privchat.sdk.kotlin.sample.pages.profile

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View
import om.netonstream.privchat.sdk.kotlin.sample.base.BasePager
import om.netonstream.privchat.sdk.kotlin.sample.lang.LangManager
import om.netonstream.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 个人中心页面（我的页面）
 * 类似抖音的个人中心
 */
@Page("ProfilePage")
internal class ProfilePage : MultiLingualPager() {
    
    var theme by observable(ThemeManager.getTheme())
    private lateinit var themeEventCallbackRef: CallbackRef
    private lateinit var spModule: SharedPreferencesModule
    private lateinit var notifyModule: NotifyModule

    override fun created() {
        super.created()
        
        // 在 created() 内 acquireModule（官方建议的方式）
        spModule = acquireModule(SharedPreferencesModule.MODULE_NAME)
        notifyModule = acquireModule(NotifyModule.MODULE_NAME)
        
        // 从 SharedPreferences 读取保存的主题
        val colorTheme = spModule.getString(ThemeManager.PREF_KEY_COLOR)
            .takeUnless { it.isEmpty() } ?: "light"
        ThemeManager.changeColorScheme(colorTheme)
        theme = ThemeManager.getTheme()
        
        // 注册主题变化监听
        themeEventCallbackRef = notifyModule.addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
            theme = ThemeManager.getTheme()
        }
    }
    
    override fun pageWillDestroy() {
        super.pageWillDestroy()
        notifyModule.removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                backgroundColor(ctx.theme.colors.background)
            }
            
            // 顶部区域
            View {
                attr {
                    padding(20f)
                    backgroundColor(ctx.theme.colors.feedBackground)
                }
                
                // 头像区域
                View {
                    attr {
                        size(80f, 80f)
                        borderRadius(40f)
                        backgroundColor(ctx.theme.colors.feedContentDivider)
                        marginBottom(16f)
                    }
                    // 这里可以添加头像 Image 组件
                }
                
                // 用户名
                Text {
                    attr {
                        text("用户名")
                        fontSize(24f)
                        fontWeightBold()
                        color(ctx.theme.colors.feedContentText)
                        marginBottom(8f)
                    }
                }
                
                // 用户简介
                Text {
                    attr {
                        text("这个人很懒，什么都没留下")
                        fontSize(14f)
                        color(ctx.theme.colors.feedContentText)
                    }
                }
            }
            
            // 设置列表
            View {
                attr {
                    flex(1f)
                    marginTop(12f)
                }
                
                List {
                    attr {
                        flex(1f)
                        flexDirectionColumn()
                        backgroundColor(ctx.theme.colors.feedBackground)
                    }
                    
                    // 设置项
                    SettingItem {
                        attr {
                            title = ctx.resStrings.setting
                            showArrow = true
                        }
                        event {
                            click {
                                // 跳转到设置页面
                                getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                    .openPage("SettingPage", JSONObject())
                            }
                        }
                    }

                    SettingItem {
                        attr {
                            title = "切换账号"
                            showArrow = true
                        }
                        event {
                            click {
                                val pageData = JSONObject()
                                PrivchatClientHolder.client?.currentUserId()?.let {
                                    pageData.put("current_uid", it.toString())
                                }
                                getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                    .openPage("SwitchAccountPage", pageData)
                            }
                        }
                    }
                    
                    // 分割线
                    View {
                        attr {
                            height(12f)
                            backgroundColor(ctx.theme.colors.background)
                        }
                    }
                    
                    // 其他设置项可以在这里添加
                    // 例如：账号与安全、隐私设置、关于我们等
                }
            }
        }
    }
}

/**
 * 设置项组件
 */
internal class SettingItem : ComposeView<SettingItemAttr, ComposeEvent>() {
    private var theme by observable(ThemeManager.getTheme())
    private lateinit var themeEventCallbackRef: CallbackRef
    
    override fun created() {
        super.created()
        // 注册主题变化监听
        themeEventCallbackRef = acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
            .addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
                theme = ThemeManager.getTheme()
            }
    }
    
    override fun viewWillUnload() {
        super.viewWillUnload()
        acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
            .removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }
    
    override fun createAttr(): SettingItemAttr = SettingItemAttr()
    override fun createEvent(): ComposeEvent = ComposeEvent()
    
    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                height(50f)
                flexDirectionRow()
                alignItemsCenter()
                backgroundColor(ctx.theme.colors.feedBackground)
                paddingLeft(16f)
                paddingRight(16f)
            }
            
            Text {
                attr {
                    text(ctx.attr.title)
                    fontSize(16f)
                    color(ctx.theme.colors.feedContentText)
                }
            }
            
            vif({ ctx.attr.showArrow }) {
                View {
                    attr {
                        absolutePosition(right = 16f, top = 0f, bottom = 0f)
                        width(20f)
                        allCenter()
                    }
                    // 右箭头图标（可以用 Image 或 Text 显示 >）
                    Text {
                        attr {
                            text(">")
                            fontSize(18f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                }
            }
        }
    }
}

internal class SettingItemAttr : ComposeAttr() {
    var title: String by observable("")
    var showArrow: Boolean by observable(false)
}

internal fun ViewContainer<*, *>.SettingItem(init: SettingItem.() -> Unit) {
    addChild(SettingItem(), init)
}

internal fun ViewContainer<*, *>.ProfilePage(init: ProfilePage.() -> Unit) {
    addChild(ProfilePage(), init)
}
