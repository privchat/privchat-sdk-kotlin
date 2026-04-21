package com.netonstream.privchat.sdk.kotlin.sample.pages.feed

import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.utils.urlParams
import com.tencent.kuikly.core.views.*
import com.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule

/**
 * 列表项组件 - 实现点击跳转到详情页
 */
internal class FeedItemView : ComposeView<FeedItemViewAttr, FeedItemViewEvent>() {

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

    override fun viewWillUnload() {
        super.viewWillUnload()
        acquireModule<NotifyModule>(NotifyModule.MODULE_NAME)
            .removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }

    override fun createAttr(): FeedItemViewAttr = FeedItemViewAttr()
    override fun createEvent(): FeedItemViewEvent = FeedItemViewEvent()

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(ctx.theme.colors.feedBackground)
                marginBottom(10f)
            }

            // 整个 item 可点击
            View {
                attr {
                    padding(15f)
                }
                event {
                    click {
                        // 点击跳转到详情页
                        val params = urlParams("id=${ctx.attr.item.id}&title=${ctx.attr.item.title}")
                        val pageData = JSONObject()
                        params.forEach {
                            pageData.put(it.key, it.value)
                        }

                        // 使用 RouterModule 跳转
                        getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                            .openPage("DetailPage", pageData)
                    }
                }

                // 作者信息
                View {
                    attr {
                        flexDirectionRow()
                        alignItemsCenter()
                        marginBottom(10f)
                    }

                    // 头像
                    View {
                        attr {
                            size(40f, 40f)
                            borderRadius(20f)
                            backgroundColor(ctx.theme.colors.feedContentDivider)
                        }
                        // 如果有头像 URL，可以使用 Image 组件
                        // Image {
                        //     attr {
                        //         src(ctx.attr.item.avatar)
                        //         size(40f, 40f)
                        //         borderRadius(20f)
                        //     }
                        // }
                    }

                    // 作者名
                    Text {
                        attr {
                            text(ctx.attr.item.author)
                            fontSize(15f)
                            fontWeightSemisolid()
                            color(ctx.theme.colors.feedContentText)
                            marginLeft(10f)
                        }
                    }
                }

                // 标题
                Text {
                    attr {
                        text(ctx.attr.item.title)
                        fontSize(17f)
                        fontWeightSemisolid()
                        color(ctx.theme.colors.feedContentText)
                        marginBottom(8f)
                    }
                }

                // 内容
                Text {
                    attr {
                        text(ctx.attr.item.content)
                        fontSize(15f)
                        color(ctx.theme.colors.feedContentText)
                        marginBottom(10f)
                        // 注意：KuiklyUI 的 Text 组件不支持 maxLines，文本会自动换行
                    }
                }

                // 图片（如果有）
                if (ctx.attr.item.imageUrl.isNotEmpty()) {
                    Image {
                        attr {
                            src(ctx.attr.item.imageUrl)
                            width(pagerData.pageViewWidth - 60f)
                            height((pagerData.pageViewWidth - 60f) * 0.6f)
                            borderRadius(8f)
                            marginBottom(10f)
                        }
                    }
                }

                // 底部操作栏
                View {
                    attr {
                        flexDirectionRow()
                        justifyContentSpaceBetween()
                        marginTop(10f)
                        paddingTop(10f)
                        borderTop(Border(0.5f, BorderStyle.SOLID, ctx.theme.colors.feedContentDivider))
                    }

                    // 点赞
                    View {
                        attr {
                            flexDirectionRow()
                            alignItemsCenter()
                        }
                        Text {
                            attr {
                                text("👍")
                                fontSize(16f)
                                marginRight(5f)
                            }
                        }
                        Text {
                            attr {
                                text("点赞")
                                fontSize(14f)
                                color(ctx.theme.colors.feedContentText)
                            }
                        }
                    }

                    // 评论
                    View {
                        attr {
                            flexDirectionRow()
                            alignItemsCenter()
                        }
                        Text {
                            attr {
                                text("💬")
                                fontSize(16f)
                                marginRight(5f)
                            }
                        }
                        Text {
                            attr {
                                text("评论")
                                fontSize(14f)
                                color(ctx.theme.colors.feedContentText)
                            }
                        }
                    }

                    // 转发
                    View {
                        attr {
                            flexDirectionRow()
                            alignItemsCenter()
                        }
                        Text {
                            attr {
                                text("🔄")
                                fontSize(16f)
                                marginRight(5f)
                            }
                        }
                        Text {
                            attr {
                                text("转发")
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

internal class FeedItemViewAttr : ComposeAttr() {
    lateinit var item: FeedItem
}

internal class FeedItemViewEvent : ComposeEvent()

internal fun ViewContainer<*, *>.FeedItemView(init: FeedItemView.() -> Unit) {
    addChild(FeedItemView(), init)
}
