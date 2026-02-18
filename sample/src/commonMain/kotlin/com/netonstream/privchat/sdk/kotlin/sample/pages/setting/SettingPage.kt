package om.netonstream.privchat.sdk.kotlin.sample.pages.setting

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.timer.setTimeout
import com.tencent.kuikly.core.views.*
import om.netonstream.privchat.sdk.kotlin.sample.lang.LangManager
import om.netonstream.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 设置页面
 * 模仿抖音的设置页面风格
 */
@Page("SettingPage")
internal class SettingPage : MultiLingualPager() {

    private var theme by observable(ThemeManager.getTheme())
    private var lang by observable(LangManager.getCurrentLanguage())
    private var settingLangHint by observable("")
    private var showModal by observable(false)

    private lateinit var spModule: SharedPreferencesModule
    private lateinit var notifyModule: NotifyModule
    private lateinit var themeEventCallbackRef: com.tencent.kuikly.core.module.CallbackRef
    private lateinit var langEventCallbackRef: com.tencent.kuikly.core.module.CallbackRef

    override fun created() {
        super.created()
        spModule = acquireModule<SharedPreferencesModule>(SharedPreferencesModule.MODULE_NAME)
        notifyModule = acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)

        // 获取当前设置的主题
        val colorTheme = spModule.getString(ThemeManager.PREF_KEY_COLOR)
            .takeUnless { it.isEmpty() } ?: "light"
        ThemeManager.changeColorScheme(colorTheme)
        theme = ThemeManager.getTheme()

        // 获取当前设置的语言
        val savedLang = spModule.getString(LangManager.KEY_PREF_LANGUAGE)
        val currentLang = savedLang.takeUnless { it.isEmpty() } ?: LangManager.getCurrentLanguage()
        lang = currentLang
        
        // 调试：打印语言列表
        com.tencent.kuikly.core.log.KLog.d("SettingPage", "SUPPORTED_LANGUAGES size: ${LangManager.SUPPORTED_LANGUAGES.size}")
        LangManager.SUPPORTED_LANGUAGES.forEach { (name, code) ->
            com.tencent.kuikly.core.log.KLog.d("SettingPage", "Language: $name -> $code")
        }

        // 注册主题变化监听
        themeEventCallbackRef = notifyModule.addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
            theme = ThemeManager.getTheme()
        }
        
        // 注册语言变化监听
        langEventCallbackRef = notifyModule.addNotify(LangManager.LANG_CHANGED_EVENT) { _ ->
            lang = LangManager.getCurrentLanguage()
        }
    }

    override fun pageWillDestroy() {
        super.pageWillDestroy()
        notifyModule.removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
        notifyModule.removeNotify(LangManager.LANG_CHANGED_EVENT, langEventCallbackRef)
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
                                text(ctx.resStrings.setting)
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
     * 主题选择视图
     */
    private fun themeChooseView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    backgroundColor(ctx.theme.colors.feedBackground)
                    marginTop(12f)
                }
                
                // 标题
                Text {
                    attr {
                        margin(16f)
                        fontSize(16f)
                        fontWeightBold()
                        text(ctx.resStrings.themeHint)
                        color(ctx.theme.colors.feedContentText)
                    }
                }

                // 主题选择
                View {
                    attr {
                        flexDirectionRow()
                        paddingLeft(16f)
                        paddingRight(16f)
                        paddingBottom(16f)
                    }
                    for ((name, themeColors) in ThemeManager.COLOR_SCHEME_MAP) {
                        View {
                            attr {
                                flex(1f)
                                height(80f)
                                marginLeft(if (name == "light") 0f else 12f)
                                borderRadius(8f)
                                backgroundColor(themeColors.primary)
                                border(
                                    Border(
                                        2f,
                                        BorderStyle.SOLID,
                                        if (ctx.theme.colors == themeColors) themeColors.tabBarTextFocused else ctx.theme.colors.feedContentDivider
                                    )
                                )
                                allCenter()
                            }
                            vif({ ctx.theme.colors == themeColors }) {
                                Text {
                                    attr {
                                        text(if (name == "light") "浅色" else "深色")
                                        fontSize(14f)
                                        color(themeColors.tabBarTextFocused)
                                        fontWeightBold()
                                    }
                                }
                            }
                            event {
                                click {
                                    if (themeColors != ctx.theme.colors) {
                                        ThemeManager.changeColorScheme(name)
                                        ctx.theme = ThemeManager.getTheme()
                                        // 持久化缓存后通知所有页面
                                        ctx.spModule.setString(ThemeManager.PREF_KEY_COLOR, name)
                                        ctx.notifyModule.postNotify(ThemeManager.SKIN_CHANGED_EVENT, JSONObject())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 语言选择视图
     */
    private fun langChooseView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    backgroundColor(ctx.theme.colors.feedBackground)
                    marginTop(12f)
                }
                
                // 标题
                Text {
                    attr {
                        margin(16f)
                        fontSize(16f)
                        fontWeightBold()
                        text(ctx.resStrings.languageHint)
                        color(ctx.theme.colors.feedContentText)
                    }
                }

                List {
                    attr {
                        width(pagerData.pageViewWidth)
                        height(150f) // 明确设置高度：3个语言选项 * 50f = 150f
                        flexDirectionColumn()
                        backgroundColor(ctx.theme.colors.feedBackground)
                        scrollEnable(false) // 禁用滚动（因为列表项数量固定且较少）
                        bouncesEnable(false) // 禁用回弹效果
                        justifyContentFlexStart()
                    }
                    for ((displayName, langCode) in LangManager.SUPPORTED_LANGUAGES) {
                        View {
                            attr {
                                height(50f)
                                flexDirectionRow()
                                alignItemsCenter()
                                backgroundColor(ctx.theme.colors.feedBackground)
                            }
                            Text {
                                attr {
                                    marginLeft(16f)
                                    flex(1f)
                                    text(displayName)
                                    fontSize(16f)
                                    color(ctx.theme.colors.feedContentText)
                                }
                            }
                            vif({ ctx.lang == langCode }) {
                                Text {
                                    attr {
                                        marginRight(16f)
                                        text("✓")
                                        fontSize(20f)
                                        color(ctx.theme.colors.tabBarTextFocused)
                                    }
                                }
                            }
                            event {
                                click {
                                    if (LangManager.getCurrentLanguage() != langCode) {
                                        // 显示加载提示
                                        ctx.settingLangHint = LangManager.SETTING_HINTS[langCode] ?: "Setting..."
                                        ctx.showModal = true
                                        
                                        // 切换语言
                                        LangManager.changeLanguage(langCode)
                                        
                                        // 持久化保存
                                        ctx.spModule.setString(LangManager.KEY_PREF_LANGUAGE, langCode)
                                        
                                        // 延迟更新 UI（给用户一个视觉反馈）
                                        setTimeout(500) {
                                            // 更新当前页面的语言状态
                                            ctx.lang = LangManager.getCurrentLanguage()
                                            // 通知所有页面语言已更改
                                            ctx.notifyModule.postNotify(LangManager.LANG_CHANGED_EVENT, JSONObject())
                                            // 关闭加载提示
                                            ctx.showModal = false
                                        }
                                    }
                                }
                            }
                        }
                        View {
                            attr {
                                marginLeft(16f)
                                height(0.5f)
                                backgroundColor(ctx.theme.colors.feedContentDivider)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 加载提示弹窗
     */
    private fun modalView(): ViewBuilder {
        val ctx = this
        return {
            vif({ ctx.showModal }) {
                Modal(true) {
                    attr {
                        allCenter()
                    }
                    View {
                        attr {
                            absolutePositionAllZero()
                            backgroundColor(Color(0x88000000))
                        }
                    }
                    View {
                        attr {
                            padding(30f)
                            size(160f, 120f)
                            borderRadius(16f)
                            backgroundColor(ctx.theme.colors.feedBackground)
                            flexDirectionColumn()
                            justifyContentCenter()
                            alignItemsCenter()
                        }
                        ActivityIndicator {
                            attr {
                                isGrayStyle(false)
                            }
                        }
                        Text {
                            attr {
                                marginTop(20f)
                                text(ctx.settingLangHint)
                                fontSize(15f)
                                color(ctx.theme.colors.feedContentText)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 开发者选项
     */
    private fun developerOptionsView(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    backgroundColor(ctx.theme.colors.feedBackground)
                    marginTop(12f)
                }
                
                // 标题
                Text {
                    attr {
                        margin(16f)
                        fontSize(16f)
                        fontWeightBold()
                        text("🧪 开发者选项")
                        color(ctx.theme.colors.feedContentText)
                    }
                }
                
                // 直播测试入口
                View {
                    attr {
                        height(50f)
                        flexDirectionRow()
                        alignItemsCenter()
                        paddingLeft(16f)
                        paddingRight(16f)
                    }
                    event {
                        click {
                            // 跳转到直播测试页面
                            getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                .openPage("LiveStreamTestPage", com.tencent.kuikly.core.nvi.serialization.json.JSONObject())
                        }
                    }
                    
                    Text {
                        attr {
                            text("📹 直播测试")
                            fontSize(16f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                    
                    View {
                        attr {
                            absolutePosition(right = 16f, top = 0f, bottom = 0f)
                            width(20f)
                            allCenter()
                        }
                        Text {
                            attr {
                                text(">")
                                fontSize(18f)
                                color(ctx.theme.colors.feedContentText)
                            }
                        }
                    }
                }
                
                // 分割线
                View {
                    attr {
                        marginLeft(16f)
                        height(0.5f)
                        backgroundColor(ctx.theme.colors.feedContentDivider)
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
                backgroundColor(ctx.theme.colors.background)
            }
            
            // 顶部导航栏
            ctx.topNavBar().invoke(this)

            // 内容区域
            List {
                attr {
                    flex(1f)
                    flexDirectionColumn()
                    backgroundColor(ctx.theme.colors.background)
                }
                
                View {
                    ctx.themeChooseView().invoke(this)
                }
                
                View {
                    ctx.langChooseView().invoke(this)
                }
                
                View {
                    ctx.developerOptionsView().invoke(this)
                }
            }

            // 加载提示弹窗
            ctx.modalView().invoke(this)
        }
    }
}

