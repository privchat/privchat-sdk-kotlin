package om.netonstream.privchat.sdk.kotlin.sample.pages

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.utils.PlatformUtils
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import om.netonstream.privchat.sdk.kotlin.sample.base.BasePager
import om.netonstream.privchat.sdk.kotlin.sample.lang.LangManager
import om.netonstream.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import om.netonstream.privchat.sdk.kotlin.sample.pages.session.SessionsPage
import om.netonstream.privchat.sdk.kotlin.sample.pages.friend.FriendsPage
import om.netonstream.privchat.sdk.kotlin.sample.pages.profile.ProfilePage
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 主页面 - 实现底部导航栏
 * 类似微博的底部导航结构
 */
@Page("MainTabPage")
internal class MainTabPage : MultiLingualPager() {

    // 当前选中的 tab 索引
    private var selectedTabIndex: Int by observable(0)
    
    // Tab 标题列表（将从 resStrings 中获取）
    private var pageTitles by observableList<String>()
    
    // 主题
    var theme by observable(ThemeManager.getTheme())
    private lateinit var themeEventCallbackRef: CallbackRef
    private lateinit var spModule: SharedPreferencesModule
    private lateinit var notifyModule: NotifyModule
    
    // Tab 图标（未选中状态）- 会话、联系人、娱乐、我的
    private val pageIcons = listOf(
        "tabbar_entertainment.png",
        "tabbar_ranking.png",
        "tabbar_entertainment.png",
        "tabbar_profile.png"
    )
    
    // Tab 图标（选中状态）
    private val pageIconsHighlight = listOf(
        "tabbar_entertainment_highlighted.png",
        "tabbar_ranking_highlighted.png",
        "tabbar_entertainment_highlighted.png",
        "tabbar_profile_highlighted.png"
    )
    
    // PageList 引用，用于切换页面
    private var pageListRef: ViewRef<PageListView<*, *>>? = null

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
        
        // 初始化 tab 标题（从多语言资源获取）
        updatePageTitles()
    }
    
    override fun langEventCallbackFn() {
        super.langEventCallbackFn()
        updatePageTitles()
    }
    
    private fun updatePageTitles() {
        // 先添加新数据，再清空旧数据，避免在更新过程中访问空列表
        val newTitles = listOf(
            "会话",
            "联系人",
            resStrings.btmBarEntertainment,
            resStrings.btmBarMe
        )
        // 使用 set 方法批量更新，而不是 clear + addAll
        // 这样可以避免在更新过程中列表为空的状态
        if (pageTitles.isEmpty()) {
            pageTitles.addAll(newTitles)
        } else {
            // 如果列表不为空，逐个更新元素，保持列表大小不变
            for (i in newTitles.indices) {
                if (i < pageTitles.size) {
                    pageTitles[i] = newTitles[i]
                } else {
                    pageTitles.add(newTitles[i])
                }
            }
            // 如果新列表比旧列表短，移除多余元素
            while (pageTitles.size > newTitles.size) {
                pageTitles.removeAt(pageTitles.size - 1)
            }
        }
    }
    
    override fun pageWillDestroy() {
        super.pageWillDestroy()
        notifyModule.removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }

    /**
     * 底部导航栏（使用 Button 组件）
     */
    private fun tabBar(): ViewBuilder {
        val ctx = this
        val safeAreaBottom = pagerData.safeAreaInsets.bottom
        val totalHeight = TAB_BOTTOM_HEIGHT + safeAreaBottom
        return {
            View {
                attr {
                    height(totalHeight)
                    flexDirectionColumn()
                    backgroundColor(ctx.theme.colors.tabBarBackground)
                    absolutePosition(bottom = 0f, left = 0f, right = 0f)
                }
                // Tab 内容区域（不包含安全区域）
                View {
                    attr {
                        height(TAB_BOTTOM_HEIGHT)
                        flexDirectionRow()
                        width(pagerData.pageViewWidth)
                    }
                    for (i in 0 until ctx.pageTitles.size) {
                        View {
                            attr {
                                flex(1f)
                                flexDirectionColumn() // 图标和文本垂直排列
                                alignItemsCenter()
                                justifyContentCenter()
                            }
                            event {
                                click {
                                    // 点击切换 tab
                                    ctx.selectedTabIndex = i
                                    ctx.pageListRef?.view?.scrollToPageIndex(i)
                                }
                            }
                            Image {
                                attr {
                                    size(30f, 30f) // 推荐大小：30x30
                                    if (i == ctx.selectedTabIndex) {
                                        // 选中状态：使用高亮图标
                                        src(ImageUri.pageAssets("background/${ctx.pageIconsHighlight[i]}"))
                                        tintColor(ctx.theme.colors.tabBarIconFocused)
                                    } else {
                                        // 未选中状态：使用普通图标
                                        src(ImageUri.pageAssets("background/${ctx.pageIcons[i]}"))
                                        tintColor(ctx.theme.colors.tabBarIconUnfocused)
                                    }
                                }
                            }
                            Text {
                                attr {
                                    text(ctx.pageTitles[i])
                                    fontSize(12f)
                                    marginTop(4f)
                                    color(
                                        if (i == ctx.selectedTabIndex) 
                                            ctx.theme.colors.tabBarTextFocused
                                        else 
                                            ctx.theme.colors.tabBarTextUnfocused
                                    )
                                }
                            }
                        }
                    }
                }
                // 安全区域占位（触控条区域）
                if (safeAreaBottom > 0f) {
                    View {
                        attr {
                            height(safeAreaBottom)
                            width(pagerData.pageViewWidth)
                            backgroundColor(ctx.theme.colors.tabBarBackground)
                        }
                    }
                }
            }
        }
    }

    override fun body(): ViewBuilder {
        val ctx = this
        val safeAreaBottom = pagerData.safeAreaInsets.bottom
        val tabBottomHeight = TAB_BOTTOM_HEIGHT + safeAreaBottom

        return {
            // 状态栏占位（MainTabPage 没有导航栏，使用背景色）
            View {
                attr {
                    height(pagerData.statusBarHeight)
                    backgroundColor(ctx.theme.colors.background)
                }
            }

            // 主内容区域 - 使用 PageList 实现左右滑动切换
            PageList {
                ref {
                    ctx.pageListRef = it
                }
                attr {
                    flexDirectionRow() // 横向布局
                    pageItemWidth(pagerData.pageViewWidth)
                    pageItemHeight(
                        pagerData.pageViewHeight - pagerData.statusBarHeight - tabBottomHeight
                    )
                    defaultPageIndex(0) // 默认显示第一个页面
                    showScrollerIndicator(false) // 隐藏滚动指示器
                    scrollEnable(false) // 禁用滑动（通过点击切换）
                    keepItemAlive(true) // 保持页面存活
                }
                event {
                    pageIndexDidChanged {
                        // 页面切换时更新选中的 tab
                        val index = (it as com.tencent.kuikly.core.nvi.serialization.json.JSONObject)
                            .optInt("index")
                        ctx.selectedTabIndex = index
                    }
                }
                
                // 会话列表
                SessionsPage { }
                
                // 联系人列表
                FriendsPage { }
                
                EmptyPage {
                    attr {
                        title = if (ctx.pageTitles.size > 2) ctx.pageTitles[2] else ""
                    }
                }
                // 我的页面
                ProfilePage { }
            }
            
            // 底部导航栏
            ctx.tabBar().invoke(this)
        }
    }

    companion object {
        const val TAB_BOTTOM_HEIGHT = 60f
    }
}

/**
 * 空页面占位符
 */
internal class EmptyPage : ComposeView<EmptyPageAttr, ComposeEvent>() {
    // 主题
    private var theme by observable(ThemeManager.getTheme())
    private var themeEventCallbackRef: CallbackRef? = null
    
    override fun created() {
        super.created()
        // 延迟注册主题变化监听，确保父 Pager 的模块已初始化
        // 使用 try-catch 防止模块未初始化时出错
        try {
            themeEventCallbackRef = acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
                .addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
                    theme = ThemeManager.getTheme()
                }
        } catch (e: Exception) {
            // 如果模块未初始化，在 viewDidLoad 中重试
        }
    }
    
    override fun viewDidLoad() {
        super.viewDidLoad()
        // 如果 created() 中注册失败，在这里重试
        if (themeEventCallbackRef == null) {
            try {
                themeEventCallbackRef = acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
                    .addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
                        theme = ThemeManager.getTheme()
                    }
            } catch (e: Exception) {
                // 如果仍然失败，忽略（模块可能还未初始化）
            }
        }
    }
    
    override fun viewWillUnload() {
        super.viewWillUnload()
        themeEventCallbackRef?.let {
            try {
                acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
                    .removeNotify(ThemeManager.SKIN_CHANGED_EVENT, it)
            } catch (e: Exception) {
                // 忽略错误
            }
        }
    }
    
    override fun createAttr(): EmptyPageAttr = EmptyPageAttr()
    override fun createEvent(): ComposeEvent = ComposeEvent()

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                allCenter()
                backgroundColor(ctx.theme.colors.background)
            }
            Text {
                attr {
                    text(ctx.attr.title)
                    fontSize(18f)
                    color(ctx.theme.colors.feedContentText)
                }
            }
        }
    }
}

internal class EmptyPageAttr : ComposeAttr() {
    var title: String by observable("")
}

internal fun ViewContainer<*, *>.EmptyPage(init: EmptyPage.() -> Unit) {
    addChild(EmptyPage(), init)
}

internal fun ViewContainer<*, *>.SessionsPage(init: om.netonstream.privchat.sdk.kotlin.sample.pages.session.SessionsPage.() -> Unit = {}): om.netonstream.privchat.sdk.kotlin.sample.pages.session.SessionsPage {
    val p = om.netonstream.privchat.sdk.kotlin.sample.pages.session.SessionsPage()
    addChild(p, init)
    return p
}

internal fun ViewContainer<*, *>.FriendsPage(init: om.netonstream.privchat.sdk.kotlin.sample.pages.friend.FriendsPage.() -> Unit = {}): om.netonstream.privchat.sdk.kotlin.sample.pages.friend.FriendsPage {
    val p = om.netonstream.privchat.sdk.kotlin.sample.pages.friend.FriendsPage()
    addChild(p, init)
    return p
}

