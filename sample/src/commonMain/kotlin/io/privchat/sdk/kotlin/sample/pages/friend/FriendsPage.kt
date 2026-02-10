package io.privchat.sdk.kotlin.sample.pages.friend

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.*
import io.privchat.sdk.kotlin.sample.base.BasePager
import io.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import io.privchat.sdk.kotlin.sample.theme.ThemeManager
import io.privchat.sdk.dto.FriendEntry
import kotlinx.coroutines.runBlocking

/**
 * 联系人列表页 - 整合 Privchat SDK getFriends
 */
@Page("FriendsPage")
internal class FriendsPage : BasePager() {
    private var theme by observable(ThemeManager.getTheme())
    private var friends by observableList<FriendEntry>()
    private var loading by observable(true)
    private var errorMsg by observable("")

    override fun viewDidLoad() {
        super.viewDidLoad()
        loadFriends()
    }

    private fun loadFriends() {
        val client = PrivchatClientHolder.client
        if (client == null) {
            loading = false
            errorMsg = "请先登录"
            return
        }
        loading = true
        errorMsg = ""
        runBlocking {
            client.getFriends(100u, 0u)
                .onSuccess { list ->
                    friends.clear()
                    friends.addAll(list)
                    loading = false
                    errorMsg = ""
                }
                .onFailure { e ->
                    errorMsg = e.message ?: "加载失败"
                    loading = false
                }
        }
    }

    private fun onFriendClick(f: FriendEntry) {
        val client = PrivchatClientHolder.client ?: return
        runBlocking {
            client.getOrCreateDirectChannel(f.userId)
                .onSuccess { result ->
                    val params = JSONObject().apply {
                        put("channelId", result.channelId.toString())
                        put("channelType", 1)
                        put("channelName", f.nickname ?: f.username)
                    }
                    acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                        .openPage("DetailPage", params)
                }
        }
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
                    text("联系人列表")
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
            vif({ !ctx.loading && ctx.errorMsg.isEmpty() && ctx.friends.isEmpty() }) {
                View {
                    attr { flex(1f); allCenter() }
                    Text {
                        attr {
                            text("暂无联系人")
                            fontSize(14f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                }
            }
            vif({ !ctx.loading && ctx.errorMsg.isEmpty() && ctx.friends.isNotEmpty() }) {
                List {
                    attr { flex(1f) }
                    vfor({ ctx.friends }) { f ->
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
                                click { ctx.onFriendClick(f) }
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
                                    f.avatarUrl != null && f.avatarUrl!!.isNotEmpty() -> {
                                        Image {
                                            attr {
                                                size(44f, 44f)
                                                borderRadius(22f)
                                                src(f.avatarUrl!!)
                                            }
                                        }
                                    }
                                    else -> {
                                        Text {
                                            attr {
                                                text((f.nickname ?: f.username).firstOrNull()?.toString() ?: "?")
                                                fontSize(18f)
                                                color(Color(0xFF757575))
                                            }
                                        }
                                    }
                                }
                            }
                            // 联系人名称（备注优先）
                            Text {
                                attr {
                                    text(f.remark?.takeIf { it.isNotEmpty() } ?: (f.nickname ?: f.username))
                                    fontSize(16f)
                                    flex(1f)
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
