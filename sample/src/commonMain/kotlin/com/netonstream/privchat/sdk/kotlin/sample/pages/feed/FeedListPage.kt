package om.netonstream.privchat.sdk.kotlin.sample.pages.feed

import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.directives.velse
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.RefreshView
import com.tencent.kuikly.core.views.RefreshViewState
import com.tencent.kuikly.core.views.FooterRefreshView
import com.tencent.kuikly.core.views.FooterRefreshState
import com.tencent.kuikly.core.views.FooterRefreshEndState
import om.netonstream.privchat.sdk.kotlin.sample.lang.LangManager
import om.netonstream.privchat.sdk.kotlin.sample.lang.MultiLingualPager
import om.netonstream.privchat.sdk.kotlin.sample.model.FeedDataManager
import om.netonstream.privchat.sdk.kotlin.sample.model.FeedType
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule

/**
 * 列表页面 - 实现下拉刷新和上拉加载更多
 * 类似微博的信息流列表
 */
internal class FeedListPage(
    private val feedType: String
) : MultiLingualPager() {

    // 数据列表
    private var feeds by observableList<FeedItem>()
    
    // 当前页码
    private var curPage by observable(0)
    
    // 下拉刷新组件引用
    private lateinit var refreshRef: ViewRef<RefreshView>
    
    // 上拉加载更多组件引用
    private lateinit var footerRefreshRef: ViewRef<FooterRefreshView>
    
    // 刷新状态文本
    private var refreshText by observable(LangManager.getCurrentResStrings().pullToRefresh)
    
    // 加载更多状态文本
    private var footerRefreshText by observable(LangManager.getCurrentResStrings().loadMore)
    
    // 是否已加载第一页
    private var didLoadFirstFeeds = false
    
    // 主题
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
    
    override fun pageWillDestroy() {
        super.pageWillDestroy()
        acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
            .removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }

    override fun langEventCallbackFn() {
        super.langEventCallbackFn()
        // 更新文本
        refreshText = resStrings.pullToRefresh
        footerRefreshText = resStrings.loadMore
    }

    /**
     * 加载第一页数据
     */
    internal fun loadFirstFeeds() {
        if (didLoadFirstFeeds) {
            return
        }
        didLoadFirstFeeds = true
        requestFeeds(curPage) {}
    }

    /**
     * 请求数据
     */
    private fun requestFeeds(page: Int, complete: () -> Unit) {
        // 将 feedType 字符串转换为 FeedType 枚举
        val feedTypeEnum = when (feedType) {
            resStrings.topBarFollow, "关注" -> FeedType.FOLLOW
            resStrings.topBarRecommend, "推荐" -> FeedType.RECOMMEND
            resStrings.topBarTrending, "热门" -> FeedType.TRENDING
            else -> FeedType.RECOMMEND
        }
        
        FeedDataManager.requestFeeds(feedTypeEnum, page) { feedList, error ->
            if (error.isEmpty()) {
                if (page == 0) {
                    // 刷新：清空旧数据
                    feeds.clear()
                }
                feeds.addAll(feedList)
            }
            complete()
        }
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                backgroundColor(ctx.theme.colors.background)
            }
            
            // 空状态
            vif({ ctx.feeds.isEmpty() }) {
                View {
                    attr {
                        flex(1f)
                        allCenter()
                    }
                    Text {
                        attr {
                            text(ctx.resStrings.loading)
                            fontSize(16f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                }
            }
            velse {
                // 列表
                List {
                    attr {
                        flex(1f)
                        firstContentLoadMaxIndex(4) // 首次加载最多4条
                    }
                    
                    // 下拉刷新组件
                    Refresh {
                        ref {
                            ctx.refreshRef = it
                        }
                        attr {
                            height(50f)
                            allCenter()
                        }
                        event {
                            refreshStateDidChange {
                                when (it) {
                                    RefreshViewState.REFRESHING -> {
                                        // 正在刷新
                                        ctx.refreshText = ctx.resStrings.refreshing
                                        ctx.curPage = 0
                                        ctx.requestFeeds(0) {
                                            ctx.refreshRef.view?.endRefresh()
                                            ctx.refreshText = ctx.resStrings.refreshDone
                                            ctx.footerRefreshRef.view?.resetRefreshState()
                                        }
                                    }
                                    RefreshViewState.IDLE -> {
                                        // 空闲状态
                                        ctx.refreshText = ctx.resStrings.pullToRefresh
                                    }
                                    RefreshViewState.PULLING -> {
                                        // 下拉中
                                        ctx.refreshText = ctx.resStrings.releaseToRefresh
                                    }
                                }
                            }
                        }
                        Text {
                            attr {
                                text(ctx.refreshText)
                                fontSize(14f)
                                color(ctx.theme.colors.feedContentText)
                            }
                        }
                    }
                    
                    // 列表项
                    vfor({ ctx.feeds }) { feed ->
                        FeedItemView {
                            attr {
                                item = feed
                            }
                        }
                    }

                    // 上拉加载更多组件
                    vif({ ctx.feeds.isNotEmpty() }) {
                        FooterRefresh {
                            ref {
                                ctx.footerRefreshRef = it
                            }
                            attr {
                                preloadDistance(600f) // 预加载距离
                                allCenter()
                                height(60f)
                            }
                            event {
                                refreshStateDidChange {
                                    when (it) {
                                        FooterRefreshState.REFRESHING -> {
                                            // 正在加载
                                            ctx.footerRefreshText = ctx.resStrings.loading
                                            ctx.curPage++
                                            ctx.requestFeeds(ctx.curPage) {
                                                // FeedDataManager 最多3页数据
                                                val state = if (ctx.curPage >= 2) {
                                                    FooterRefreshEndState.NONE_MORE_DATA
                                                } else {
                                                    FooterRefreshEndState.SUCCESS
                                                }
                                                ctx.footerRefreshRef.view?.endRefresh(state)
                                            }
                                        }
                                        FooterRefreshState.IDLE -> {
                                            // 空闲状态
                                            ctx.footerRefreshText = ctx.resStrings.loadMore
                                        }
                                        FooterRefreshState.NONE_MORE_DATA -> {
                                            // 没有更多数据
                                            ctx.footerRefreshText = ctx.resStrings.noMoreData
                                        }
                                        FooterRefreshState.FAILURE -> {
                                            // 加载失败
                                            ctx.footerRefreshText = ctx.resStrings.tapToRetry
                                        }
                                        else -> {}
                                    }
                                }
                                click {
                                    // 点击重试
                                    if (ctx.footerRefreshText == ctx.resStrings.tapToRetry) {
                                        ctx.footerRefreshRef.view?.beginRefresh()
                                    }
                                }
                            }
                            Text {
                                attr {
                                    text(ctx.footerRefreshText)
                                    fontSize(14f)
                                    color(ctx.theme.colors.feedContentText)
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
 * 数据模型
 */
data class FeedItem(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val avatar: String,
    val imageUrl: String
)

internal class FeedListPageAttr : ComposeAttr()
internal class FeedListPageEvent : ComposeEvent()

internal fun ViewContainer<*, *>.FeedListPage(
    feedType: String,
    init: FeedListPage.() -> Unit
) {
    addChild(FeedListPage(feedType), init)
}

