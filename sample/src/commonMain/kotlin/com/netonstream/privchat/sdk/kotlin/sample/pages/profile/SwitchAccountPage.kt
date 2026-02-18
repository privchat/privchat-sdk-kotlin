package om.netonstream.privchat.sdk.kotlin.sample.pages.profile

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.timer.setTimeout
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import om.netonstream.privchat.sdk.PrivchatClient
import om.netonstream.privchat.sdk.PrivchatConfig
import om.netonstream.privchat.sdk.kotlin.sample.base.BasePager
import om.netonstream.privchat.sdk.kotlin.sample.privchat.LoginCoroutineDispatcher
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatPlatform
import om.netonstream.privchat.sdk.kotlin.sample.runOnKuiklyContext
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager
import om.netonstream.privchat.sdk.parseServerUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val OPEN_PAGE_DELAY_MS = 150
private const val AFTER_CONNECT_DELAY_MS = 300L

@Page("SwitchAccountPage")
internal class SwitchAccountPage : BasePager() {
    private companion object {
        const val PARAM_CURRENT_UID = "current_uid"
    }

    private var theme by observable(ThemeManager.getTheme())
    private var status by observable("")
    private var localAccountUids by observable(listOf<String>())
    private var currentUid by observable("")
    private var serverUrl by observable("quic://192.168.1.3:9001")

    override fun created() {
        super.created()
        refreshLocalAccounts()
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
                    text("切换账号")
                    fontSize(22f)
                    fontWeightBold()
                    marginTop(56f)
                    marginLeft(24f)
                    color(ctx.theme.colors.feedContentText)
                }
            }

            Text {
                attr {
                    text("选择一个已登录账号快速进入")
                    fontSize(13f)
                    marginTop(8f)
                    marginLeft(24f)
                    color(ctx.theme.colors.feedContentText)
                }
            }

            View {
                attr {
                    margin(24f)
                    flexDirectionColumn()
                }
                for (uid in ctx.localAccountUids) {
                    val isCurrent = uid == ctx.currentUid
                    Button {
                        attr {
                            height(44f)
                            marginTop(8f)
                            borderRadius(8f)
                            backgroundColor(if (isCurrent) Color(0xFFF5F5F5) else Color.WHITE)
                            titleAttr {
                                text(if (uid == ctx.currentUid) "当前账号 $uid" else "账号 $uid")
                                fontSize(15f)
                                color(if (isCurrent) Color(0xFF999999) else ctx.theme.colors.feedContentText)
                            }
                        }
                        event {
                            click {
                                if (isCurrent) {
                                    ctx.status = "已是当前账号"
                                    return@click
                                }
                                ctx.doQuickEnter(uid)
                            }
                        }
                    }
                }

                Button {
                    attr {
                        height(44f)
                        marginTop(16f)
                        borderRadius(8f)
                        backgroundColor(Color(0xFFF0F0F0))
                        titleAttr {
                            text("添加账户")
                            fontSize(15f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                    event {
                        click {
                            val pageData = JSONObject()
                            pageData.put("force_login_form", 1)
                            if (ctx.currentUid.isNotEmpty()) {
                                pageData.put("current_uid", ctx.currentUid)
                            }
                            getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                .openPage("LoginPage", pageData)
                        }
                    }
                }
            }

            Text {
                attr {
                    text(ctx.status)
                    fontSize(12f)
                    marginTop(8f)
                    marginLeft(24f)
                    color(Color(0xFF666666))
                }
            }
        }
    }

    override fun viewDidLoad() {
        super.viewDidLoad()
        refreshLocalAccounts()
    }

    private fun refreshLocalAccounts() {
        val fromDisk = PrivchatPlatform.localAccountUids().filter { it.isNotBlank() }
        val fromClient = PrivchatClientHolder.client?.currentUserId()?.toString()?.trim().orEmpty()
        val fromFile = PrivchatPlatform.currentUid()?.trim().orEmpty()
        val fromParam = pagerData.params.optString(PARAM_CURRENT_UID).trim()
        val merged = linkedSetOf<String>()
        listOf(fromClient, fromFile, fromParam).forEach {
            if (it.isNotEmpty()) merged.add(it)
        }
        fromDisk.forEach { merged.add(it) }
        localAccountUids = merged.toList()
        currentUid = when {
            fromClient.isNotEmpty() -> fromClient
            fromFile.isNotEmpty() -> fromFile
            fromParam.isNotEmpty() -> fromParam
            else -> localAccountUids.firstOrNull().orEmpty()
        }
        if (localAccountUids.isEmpty()) {
            status = "未找到本地账号: client=$fromClient file=$fromFile param=$fromParam users=${fromDisk.size}"
        } else if (status.startsWith("未找到本地账号")) {
            status = ""
        }
        println("[SwitchAccount] accounts=${localAccountUids.joinToString(",")} current=$currentUid fromDisk=${fromDisk.size} fromFile=$fromFile")
    }

    private fun doQuickEnter(uid: String) {
        val ep = parseServerUrl(serverUrl.trim())
        if (ep == null) {
            status = "服务器地址格式错误（支持 quic://、wss://、ws://、tcp://）"
            return
        }
        if (!PrivchatPlatform.setCurrentUid(uid)) {
            status = "切换账号失败：无法设置 current_user"
            return
        }
        status = "账号 $uid 进入中..."

        CoroutineScope(LoginCoroutineDispatcher).launch {
            var client: PrivchatClient? = null
            runCatching {
                val config = PrivchatConfig(
                    dataDir = PrivchatPlatform.dataDir(),
                    assetsDir = null,
                    serverEndpoints = listOf(ep),
                )
                client = PrivchatClient.create(config).getOrThrow()
                client.connect().getOrThrow()
                delay(AFTER_CONNECT_DELAY_MS)
                val restored = client.restoreLocalSession().getOrThrow()
                if (!restored) error("本地会话不存在，请使用添加账户登录")
                client.runBootstrapSync().getOrThrow()
                PrivchatClientHolder.setClient(client)

                if (PrivchatPlatform.navigateToMainTabPage()) {
                    return@runCatching
                }
                delay(OPEN_PAGE_DELAY_MS.toLong())
                runOnKuiklyContext {
                    setTimeout(pagerId, 0) {
                        getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                            .openPage("MainTabPage", JSONObject())
                    }
                }
            }.onFailure { e ->
                val msg = e.message?.ifBlank { null } ?: "未知错误"
                status = "失败: $msg"
                runCatching { client?.close() }
                refreshLocalAccounts()
            }
        }
    }
}

internal fun ViewContainer<*, *>.SwitchAccountPage(init: SwitchAccountPage.() -> Unit) {
    addChild(SwitchAccountPage(), init)
}
