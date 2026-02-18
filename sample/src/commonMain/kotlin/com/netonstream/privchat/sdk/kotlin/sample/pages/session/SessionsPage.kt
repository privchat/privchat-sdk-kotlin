package om.netonstream.privchat.sdk.kotlin.sample.pages.session

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.*
import om.netonstream.privchat.sdk.kotlin.sample.base.BasePager
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager
import om.netonstream.privchat.sdk.kotlin.sample.util.TimeFormatter
import om.netonstream.privchat.sdk.dto.ChannelListEntry
import kotlinx.coroutines.runBlocking

/**
 * 会话列表页 - 整合 Privchat SDK getChannels
 */
@Page("SessionsPage")
internal class SessionsPage : BasePager() {
    private var theme by observable(ThemeManager.getTheme())
    private var channels by observableList<ChannelListEntry>()
    private var loading by observable(true)
    private var errorMsg by observable("")

    override fun viewDidLoad() {
        super.viewDidLoad()
        loadChannels()
    }

    private fun loadChannels() {
        val client = PrivchatClientHolder.client
        if (client == null) {
            loading = false
            errorMsg = "请先登录"
            return
        }
        loading = true
        errorMsg = ""
        runBlocking {
            client.getChannels(50u, 0u)
                .onSuccess { list ->
                    println("SessionsPage loadChannels: 收到 ${list.size} 条会话")
                    list.forEachIndexed { i, ch ->
                        println("SessionsPage [$i] channelId=${ch.channelId}, channelType=${ch.channelType}, name=${ch.name}")
                    }
                    channels.clear()
                    channels.addAll(list)
                    loading = false
                    errorMsg = ""
                }
                .onFailure { e ->
                    errorMsg = e.message ?: "加载失败"
                    loading = false
                }
        }
    }

    private fun onChannelClick(ch: ChannelListEntry) {
        val params = JSONObject().apply {
            put("channelId", ch.channelId.toString())
            put("channelType", ch.channelType)
            put("channelName", ch.name)
        }
        acquireModule<RouterModule>(RouterModule.MODULE_NAME)
            .openPage("DetailPage", params)
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                flexDirectionColumn()
                backgroundColor(ctx.theme.colors.background)
            }
            Text {
                attr {
                    text("会话列表")
                    fontSize(20f)
                    marginTop(24f)
                    marginLeft(20f)
                    marginBottom(12f)
                    color(ctx.theme.colors.feedContentText)
                }
            }
            vif({ ctx.loading }) {
                View {
                    attr { flex(1f); allCenter() }
                    Text {
                        attr {
                            text("加载中...")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                }
            }
            vif({ !ctx.loading && ctx.errorMsg.isNotEmpty() }) {
                View {
                    attr { flex(1f); allCenter(); padding(20f) }
                    Text {
                        attr {
                            text(ctx.errorMsg)
                            fontSize(14f)
                            color(Color(0xFFE53935))
                        }
                    }
                }
            }
            vif({ !ctx.loading && ctx.errorMsg.isEmpty() && ctx.channels.isEmpty() }) {
                View {
                    attr { flex(1f); allCenter() }
                    Text {
                        attr {
                            text("暂无会话")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                }
            }
            vif({ !ctx.loading && ctx.errorMsg.isEmpty() && ctx.channels.isNotEmpty() }) {
                List {
                    attr { flex(1f) }
                    vfor({ ctx.channels }) { ch ->
                        View {
                            attr {
                                flexDirectionRow()
                                alignItemsCenter()
                                padding(12f, 14f)
                                backgroundColor(Color.WHITE)
                                marginLeft(12f)
                                marginRight(12f)
                                marginBottom(1f)
                            }
                            event {
                                click { ctx.onChannelClick(ch) }
                            }
                            // 左侧头像
                            View {
                                attr {
                                    size(44f, 44f)
                                    borderRadius(22f)
                                    backgroundColor(Color(0xFFE0E0E0))
                                    marginRight(12f)
                                    allCenter()
                                }
                                when {
                                    ch.avatarUrl != null && ch.avatarUrl!!.isNotEmpty() -> {
                                        Image {
                                            attr {
                                                size(44f, 44f)
                                                borderRadius(22f)
                                                src(ch.avatarUrl!!)
                                            }
                                        }
                                    }
                                    else -> {
                                        Text {
                                            attr {
                                                text(ch.name.firstOrNull()?.toString() ?: "?")
                                                fontSize(18f)
                                                color(Color(0xFF757575))
                                            }
                                        }
                                    }
                                }
                            }
                            // 中间：会话名 + 最后消息
                            View {
                                attr {
                                    flex(1f)
                                    flexDirectionColumn()
                                }
                                Text {
                                    attr {
                                        text(ch.name)
                                        fontSize(16f)
                                        fontWeightSemisolid()
                                        color(ctx.theme.colors.feedContentText)
                                    }
                                }
                                Text {
                                    attr {
                                        text(ch.latestEvent?.content?.take(30)?.let { if (it.length >= 30) "$it..." else it } ?: "暂无消息")
                                        fontSize(14f)
                                        marginTop(4f)
                                        color(Color(0xFF999999))
                                    }
                                }
                            }
                            // 右侧：时间 + 未读数
                            View {
                                attr {
                                    flexDirectionColumn()
                                    alignItemsCenter()
                                    marginLeft(8f)
                                }
                                Text {
                                    attr {
                                        text(TimeFormatter.formatChannelTime(ch.lastTs.toLong()))
                                        fontSize(12f)
                                        color(Color(0xFF999999))
                                    }
                                }
                                if (ch.messages > 0u) {
                                    View {
                                        attr {
                                            marginTop(6f)
                                            flexDirectionRow()
                                            alignItemsCenter()
                                            minWidth(18f)
                                            height(18f)
                                            borderRadius(9f)
                                            backgroundColor(Color(0xFFFA5151))
                                            paddingLeft(6f)
                                            paddingRight(6f)
                                        }
                                        Text {
                                            attr {
                                                text(if (ch.messages > 99u) "99+" else ch.messages.toString())
                                                fontSize(11f)
                                                color(Color.WHITE)
                                                fontWeightSemisolid()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
