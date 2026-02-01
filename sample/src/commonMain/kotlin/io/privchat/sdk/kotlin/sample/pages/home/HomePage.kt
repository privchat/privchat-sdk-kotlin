package io.privchat.sdk.kotlin.sample.pages.home

import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.utils.PlatformUtils
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.PageListView
import com.tencent.kuikly.core.views.ScrollParams
import com.tencent.kuikly.core.views.ios.SegmentedControlIOS
import io.privchat.sdk.kotlin.sample.pages.feed.FeedListPage
import io.privchat.sdk.kotlin.sample.theme.ThemeManager

/**
 * 首页 - 实现顶部 Tabs 和左右滑动切换
 * 类似微博的顶部标签栏，可以左右滑动切换不同的内容流
 */
internal class HomePage : ComposeView<HomePageAttr, HomePageEvent>() {

    // 当前选中的 tab 索引
    private var curIndex: Int by observable(0)
    
    // Tab 标题列表
    private var titles by observableList<String>()
    
    // PageList 的滚动参数，用于与 Tabs 联动
    private var scrollParams: ScrollParams? by observable(null)
    
    // PageList 引用
    private var pageListRef: ViewRef<PageListView<*, *>>? = null
    
    // Tab 头部宽度
    private var tabHeaderWidth by observable(300f)
    
    // 主题
    private var theme by observable(ThemeManager.getTheme())
    private lateinit var themeEventCallbackRef: CallbackRef

    override fun created() {
        super.created()
        // 初始化 tab 标题
        titles.addAll(listOf("关注", "推荐", "热门"))
        
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

    override fun createAttr(): HomePageAttr = HomePageAttr()
    override fun createEvent(): HomePageEvent = HomePageEvent()

    /**
     * 顶部 Tabs 组件
     */
    private fun tabsHeader(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(ctx.theme.colors.topBarBackground)
            }
            
            // iOS 平台临时禁用 SegmentedControlIOS，避免崩溃
            // TODO: 等待 KuiklyUI 框架修复 SegmentedControlIOS 的 titles 类型问题
            if (false && PlatformUtils.isIOS() && PlatformUtils.isLiquidGlassSupported()) {
                SegmentedControlIOS {
                    attr {
                        height(TAB_HEADER_HEIGHT * 0.8f)
                        width(ctx.tabHeaderWidth * 0.5f)
                        margin(
                            top = TAB_HEADER_HEIGHT * 0.1f,
                            bottom = TAB_HEADER_HEIGHT * 0.1f
                        )
                        titles(ctx.titles.toList())
                        selectedIndex(ctx.curIndex)
                        alignSelfCenter()
                    }
                    event {
                        onValueChanged {
                            // Tab 切换时，同步切换 PageList 页面
                            ctx.pageListRef?.view?.scrollToPageIndex(it.index, true)
                        }
                    }
                }
            } else {
                // Android/通用平台使用 Tabs 组件
                Tabs {
                    attr {
                        height(TAB_HEADER_HEIGHT)
                        width(ctx.tabHeaderWidth)
                        defaultInitIndex(ctx.curIndex)
                        alignSelfCenter()
                        // 指示器样式
                        indicatorInTabItem {
                            View {
                                attr {
                                    height(3f)
                                    absolutePosition(left = 2f, right = 2f, bottom = 5f)
                                    borderRadius(2f)
                                    backgroundColor(ctx.theme.colors.topBarIndicator) // 指示器颜色
                                }
                            }
                        }
                        // 与 PageList 联动
                        ctx.scrollParams?.also {
                            scrollParams(it)
                        }
                    }
                    event {
                        contentSizeChanged { width, _ ->
                            ctx.tabHeaderWidth = width
                        }
                    }
                    
                    // 遍历创建 TabItem
                    for (i in 0 until ctx.titles.size) {
                        TabItem { state ->
                            attr {
                                marginLeft(10f)
                                marginRight(10f)
                                allCenter()
                            }
                            event {
                                click {
                                    // 点击 Tab 时切换页面
                                    ctx.pageListRef?.view?.scrollToPageIndex(i, true)
                                }
                            }
                            Text {
                                attr {
                                    text(ctx.titles[i])
                                    fontSize(17f)
                                    if (state.selected) {
                                        fontWeightBold()
                                        color(ctx.theme.colors.topBarTextFocused) // 选中颜色
                                    } else {
                                        color(ctx.theme.colors.topBarTextUnfocused) // 未选中颜色
                                    }
                                }
                            }
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
            }
            
            // 状态栏占位（使用与导航栏一致的颜色）
            View {
                attr {
                    height(pagerData.statusBarHeight)
                    backgroundColor(ctx.theme.colors.topBarBackground)
                }
            }
            
            // 顶部 Tabs
            ctx.tabsHeader().invoke(this)

            // 内容区域 - 使用 PageList 实现左右滑动切换
            PageList {
                ref {
                    ctx.pageListRef = it
                }
                attr {
                    flexDirectionRow() // 横向布局
                    pageItemWidth(pagerData.pageViewWidth)
                    pageItemHeight(
                        pagerData.pageViewHeight - pagerData.statusBarHeight - TAB_HEADER_HEIGHT
                    )
                    defaultPageIndex(ctx.curIndex)
                    showScrollerIndicator(false)
                }
                event {
                    scroll {
                        // 记录滚动参数，用于与 Tabs 联动
                        ctx.scrollParams = it
                    }
                    pageIndexDidChanged {
                        // 页面切换时更新选中的 tab
                        val index = (it as JSONObject).optInt("index")
                        ctx.curIndex = index
                    }
                }
                
                // 关注流
                FeedListPage("关注") { }
                
                // 推荐流
                FeedListPage("推荐") { }
                
                // 热门流
                FeedListPage("热门") { }
            }
        }
    }

    companion object {
        const val TAB_HEADER_HEIGHT = 50f
    }
}

internal class HomePageAttr : ComposeAttr()
internal class HomePageEvent : ComposeEvent()

internal fun ViewContainer<*, *>.HomePage(init: HomePage.() -> Unit) {
    addChild(HomePage(), init)
}

