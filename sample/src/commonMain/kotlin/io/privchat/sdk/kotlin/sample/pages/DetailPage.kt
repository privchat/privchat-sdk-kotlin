package io.privchat.sdk.kotlin.sample.pages

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.directives.scrollToPosition
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import io.privchat.sdk.kotlin.sample.base.BasePager
import io.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import io.privchat.sdk.kotlin.sample.theme.ThemeManager
import io.privchat.sdk.kotlin.sample.util.TimeFormatter
import io.privchat.sdk.dto.MessageEntry
import io.privchat.sdk.dto.MessageStatus
import kotlinx.coroutines.runBlocking

/**
 * 详情页面 - 展示内容详情或会话消息列表
 * 当传入 channelId 时为会话详情，使用 getMessages 加载消息；否则为通用详情
 */
@Page("DetailPage")
internal class DetailPage : BasePager() {

    private var detailTitle: String = ""
    private var detailId: Int = 0
    
    // 会话模式参数
    private var channelId: ULong = 0uL
    private var channelType: Int = 0
    private var channelName: String = ""
    private var isChatMode: Boolean = false
    
    // 消息列表
    private var messages by observableList<MessageEntry>()
    private var messagesLoading by observable(false)
    private var messagesError by observable("")
    
    // 发送输入框
    private var inputText by observable("")
    private lateinit var inputRef: ViewRef<InputView>
    
    // 消息列表引用，用于加载后滚动到底部
    private var messageListRef: ViewRef<ListView<*, *>>? = null
    
    // 主题
    private var theme by observable(ThemeManager.getTheme())
    private lateinit var themeEventCallbackRef: CallbackRef
    private lateinit var spModule: SharedPreferencesModule
    private lateinit var notifyModule: NotifyModule

    override fun created() {
        super.created()
        
        spModule = acquireModule(SharedPreferencesModule.MODULE_NAME)
        notifyModule = acquireModule(NotifyModule.MODULE_NAME)
        
        val colorTheme = spModule.getString(ThemeManager.PREF_KEY_COLOR)
            .takeUnless { it.isEmpty() } ?: "light"
        ThemeManager.changeColorScheme(colorTheme)
        theme = ThemeManager.getTheme()
        
        themeEventCallbackRef = notifyModule.addNotify(ThemeManager.SKIN_CHANGED_EVENT) { _ ->
            theme = ThemeManager.getTheme()
        }
        
        // 从页面参数中获取数据
        detailId = pageData.params.optInt("id", 0)
        detailTitle = pageData.params.optString("title", "")
        
        // 会话模式：channelId + channelType + channelName
        val chIdStr = pageData.params.optString("channelId", "")
        if (chIdStr.isNotEmpty()) {
            channelId = chIdStr.toULongOrNull() ?: 0uL
            channelType = pageData.params.optInt("channelType", 0)
            channelName = pageData.params.optString("channelName", "")
            isChatMode = channelId > 0uL
        }
    }
    
    override fun viewDidLoad() {
        super.viewDidLoad()
        if (isChatMode) {
            loadMessages()
        }
    }
    
    override fun pageWillDestroy() {
        super.pageWillDestroy()
        notifyModule.removeNotify(ThemeManager.SKIN_CHANGED_EVENT, themeEventCallbackRef)
    }
    
    private fun loadMessages() {
        val client = PrivchatClientHolder.client
        if (client == null) {
            messagesError = "请先登录"
            return
        }
        messagesLoading = true
        messagesError = ""
        runBlocking {
            client.getMessages(channelId, 50u, null)
                .onSuccess { list ->
                    messages.clear()
                    // 按 message.id 升序，最新消息在最下面
                    messages.addAll(list.sortedBy { it.id })
                    messagesLoading = false
                    messagesError = ""
                    // 布局完成后滚动到底部，消息是从下往上看的
                    addTaskWhenPagerUpdateLayoutFinish {
                        if (messages.isNotEmpty()) {
                            messageListRef?.view?.scrollToPosition(messages.size - 1, animate = true)
                        }
                    }
                }
                .onFailure { e ->
                    messagesError = e.message ?: "加载消息失败"
                    messagesLoading = false
                }
        }
    }
    
    /** 去重追加单条消息并滚动到底部（发送后使用，避免整表重载导致往上跳） */
    private fun appendMessageAndScrollToBottom(newMsg: MessageEntry) {
        if (messages.any { it.id == newMsg.id }) return
        messages.add(newMsg)
        messages.sortBy { it.id }
        addTaskWhenPagerUpdateLayoutFinish {
            messageListRef?.view?.scrollToPosition(messages.size - 1, animate = true)
        }
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(ctx.theme.colors.background)
            }
            
            // 导航栏
            View {
                attr {
                    paddingTop(pagerData.statusBarHeight)
                    backgroundColor(ctx.theme.colors.topBarBackground)
                }
                View {
                    attr {
                        height(44f)
                        flexDirectionRow()
                        alignItemsCenter()
                        borderBottom(Border(0.5f, BorderStyle.SOLID, ctx.theme.colors.feedContentDivider))
                    }
                    
                    // 返回按钮
                    View {
                        attr {
                            size(44f, 44f)
                            allCenter()
                        }
                        event {
                            click {
                                // 返回上一页
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
                            text(if (ctx.isChatMode) ctx.channelName.ifEmpty { "会话" } else "详情")
                            fontSize(17f)
                            fontWeightSemisolid()
                            color(ctx.theme.colors.topBarTextFocused)
                            flex(1f)
                            textAlignCenter()
                        }
                    }
                    
                    // 占位（保持标题居中）
                    View {
                        attr {
                            size(44f, 44f)
                        }
                    }
                }
            }
            
            // 内容区域
            View {
                attr {
                    flex(1f)
                    flexDirectionColumn()
                }
                
                vif({ ctx.isChatMode }) {
                    // 会话模式：消息列表
                    vif({ ctx.messagesLoading }) {
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
                    vif({ !ctx.messagesLoading && ctx.messagesError.isNotEmpty() }) {
                        View {
                            attr { flex(1f); allCenter(); padding(20f) }
                            Text {
                                attr {
                                    text(ctx.messagesError)
                                    fontSize(14f)
                                    color(Color(0xFFE53935))
                                }
                            }
                        }
                    }
                    vif({ !ctx.messagesLoading && ctx.messagesError.isEmpty() && ctx.messages.isEmpty() }) {
                        View {
                            attr { flex(1f); allCenter() }
                            Text {
                                attr {
                                    text("暂无消息")
                                    fontSize(14f)
                                    color(ctx.theme.colors.feedContentText)
                                }
                            }
                        }
                    }
                    vif({ !ctx.messagesLoading && ctx.messagesError.isEmpty() && ctx.messages.isNotEmpty() }) {
                        List {
                            ref { this@DetailPage.messageListRef = it }
                            attr {
                                flex(1f)
                                padding(12f)
                            }
                            vfor({ ctx.messages }) { msg ->
                                View {
                                    attr {
                                        flexDirectionRow()
                                        marginBottom(12f)
                                        if (msg.fromUid == PrivchatClientHolder.client?.currentUserId()) {
                                            justifyContentFlexEnd()
                                        } else {
                                            justifyContentFlexStart()
                                        }
                                    }
                                    View {
                                        attr {
                                            maxWidth(pagerData.pageViewWidth * 0.72f)
                                            padding(12f, 10f)
                                            borderRadius(12f)
                                            backgroundColor(
                                                if (msg.fromUid == PrivchatClientHolder.client?.currentUserId())
                                                    Color(0xFFE8E8E8)  // 自己发的：灰色靠右
                                                else
                                                    Color(0xFF5B9BD5)  // 别人发的：蓝色靠左
                                            )
                                        }
                                        Text {
                                            attr {
                                                text(msg.content)
                                                fontSize(16f)
                                                color(if (msg.fromUid == PrivchatClientHolder.client?.currentUserId())
                                                    ctx.theme.colors.feedContentText
                                                else
                                                    Color.WHITE)
                                            }
                                        }
                                        Text {
                                            attr {
                                                text(TimeFormatter.formatChannelTime(msg.timestamp.toLong()))
                                                fontSize(11f)
                                                color(if (msg.fromUid == PrivchatClientHolder.client?.currentUserId())
                                                    Color(0xFF999999)
                                                else
                                                    Color(0xFFE0E0E0))
                                                marginTop(4f)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                    // 会话模式：底部输入框 + 发送按钮
                    vif({ ctx.isChatMode }) {
                        View {
                            attr {
                                flexDirectionRow()
                                padding(12f)
                                paddingBottom(24f)
                                alignItemsCenter()
                                backgroundColor(ctx.theme.colors.background)
                                borderTop(com.tencent.kuikly.core.base.Border(0.5f, com.tencent.kuikly.core.base.BorderStyle.SOLID, ctx.theme.colors.feedContentDivider))
                            }
                            View {
                                attr {
                                    flex(1f)
                                    height(40f)
                                    backgroundColor(Color(0xFFF5F5F5))
                                    borderRadius(8f)
                                    marginRight(10f)
                                }
                                Input {
                                    ref { ctx.inputRef = it }
                                    attr {
                                        flex(1f)
                                        fontSize(16f)
                                        marginLeft(12f)
                                        marginRight(12f)
                                        color(ctx.theme.colors.feedContentText)
                                        placeholder("输入消息...")
                                        placeholderColor(Color(0xFF999999))
                                    }
                                    event {
                                        textDidChange { ctx.inputText = it.text }
                                    }
                                }
                            }
                            Button {
                                attr {
                                    padding(12f, 16f)
                                    backgroundColor(Color(0xFF5B9BD5))
                                    borderRadius(8f)
                                    titleAttr {
                                        text("发送")
                                        fontSize(16f)
                                        color(Color.WHITE)
                                    }
                                }
                                event {
                                    click {
                                        val txt = ctx.inputText.trim()
                                        if (txt.isEmpty()) return@click
                                        val client = PrivchatClientHolder.client
                                        if (client == null) return@click
                                        runBlocking {
                                            client.sendText(ctx.channelId, ctx.channelType, txt)
                                                .onSuccess { messageId ->
                                                    ctx.inputText = ""
                                                    ctx.inputRef.view?.setText("")
                                                    val uid = client.currentUserId() ?: 0uL
                                                    val newMsg = MessageEntry(
                                                        id = messageId,
                                                        serverMessageId = messageId,
                                                        channelId = ctx.channelId,
                                                        channelType = ctx.channelType,
                                                        fromUid = uid,
                                                        content = txt,
                                                        status = MessageStatus.Sent,
                                                        timestamp = TimeFormatter.currentTimeMillis().toULong()
                                                    )
                                                    ctx.appendMessageAndScrollToBottom(newMsg)
                                                }
                                                .onFailure { e ->
                                                    ctx.messagesError = e.message ?: "发送失败"
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                vif({ !ctx.isChatMode }) {
                    // 通用详情模式（Feed 等）
                    View {
                        attr {
                            flex(1f)
                            padding(20f)
                        }
                        Text {
                            attr {
                                text(ctx.detailTitle)
                                fontSize(22f)
                                fontWeightSemisolid()
                                color(ctx.theme.colors.feedContentText)
                                marginBottom(20f)
                            }
                        }
                        Text {
                            attr {
                                text("这是 ID 为 ${ctx.detailId} 的内容详情页面。\n\n在这里可以展示完整的内容、图片、评论等信息。")
                                fontSize(16f)
                                color(ctx.theme.colors.feedContentText)
                                lineHeight(24f)
                            }
                        }
                    }
                }
            }
        }
    }
