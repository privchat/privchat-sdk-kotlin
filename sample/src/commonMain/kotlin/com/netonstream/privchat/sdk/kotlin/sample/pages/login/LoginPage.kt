package om.netonstream.privchat.sdk.kotlin.sample.pages.login

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.timer.setTimeout
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import om.netonstream.privchat.sdk.kotlin.sample.base.BasePager
import om.netonstream.privchat.sdk.kotlin.sample.privchat.LoginCoroutineDispatcher
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatPlatform
import om.netonstream.privchat.sdk.kotlin.sample.runOnKuiklyContext
import om.netonstream.privchat.sdk.kotlin.sample.theme.ThemeManager
import om.netonstream.privchat.sdk.PrivchatClient
import om.netonstream.privchat.sdk.PrivchatConfig
import om.netonstream.privchat.sdk.parseServerUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** 登录成功后延迟再 openPage，避免与 Kuikly layout/异步释放竞态（仅 Kotlin Native SDK 集成时需规避） */
private const val OPEN_PAGE_DELAY_MS = 150
/** connect() 返回后、login/register 前等待，确保服务端 session 已就绪再发 RPC（FFI connect 会阻塞到连接建立） */
private const val AFTER_CONNECT_DELAY_MS = 300L
/** 调试：页面加载后自动触发一次登录（等价自动点击“登录”按钮） */
private const val AUTO_LOGIN_ON_LOAD = false
private const val AUTO_LOGIN_DELAY_MS = 120
private const val AUTO_RESTORE_DELAY_MS = 80L

/**
 * 登录/注册页面
 */
@Page("LoginPage")
internal class LoginPage : BasePager() {
    private companion object {
        const val PARAM_SHOW_ACCOUNTS = "show_accounts"
        const val PARAM_CURRENT_UID = "current_uid"
        const val PARAM_FORCE_LOGIN_FORM = "force_login_form"
    }

    private var theme by observable(ThemeManager.getTheme())
    private var serverUrl by observable("quic://192.168.1.3:9001")
    private var username by observable("demo")
    private var password by observable("123456")
    private var status by observable("")
    private var localAccountUids by observable(listOf<String>())
    private var showLoginForm by observable(true)

    private lateinit var serverUrlRef: ViewRef<InputView>
    private lateinit var usernameRef: ViewRef<InputView>
    private lateinit var passwordRef: ViewRef<InputView>
    private var autoLoginTriggered = false
    private var autoRestoreTriggered = false

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
                    text("Privchat")
                    fontSize(24f)
                    fontWeightBold()
                    marginTop(60f)
                    marginLeft(24f)
                    color(ctx.theme.colors.feedContentText)
                }
            }

            Text {
                attr {
                    text("登录或注册以开始")
                    fontSize(14f)
                    marginTop(8f)
                    marginLeft(24f)
                    color(ctx.theme.colors.feedContentText)
                }
            }

            if (ctx.localAccountUids.isNotEmpty() && !ctx.showLoginForm) {
                View {
                    attr {
                        margin(24f)
                        flexDirectionColumn()
                    }
                    Text {
                        attr {
                            text("选择账号快速进入")
                            fontSize(14f)
                            marginBottom(12f)
                            color(ctx.theme.colors.feedContentText)
                        }
                    }
                    for (uid in ctx.localAccountUids) {
                        Button {
                            attr {
                                height(44f)
                                marginTop(8f)
                                borderRadius(8f)
                                backgroundColor(Color.WHITE)
                                titleAttr {
                                    text("账号 $uid")
                                    fontSize(15f)
                                    color(ctx.theme.colors.feedContentText)
                                }
                            }
                            event {
                                click {
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
                                ctx.showLoginForm = true
                            }
                        }
                    }
                }
            } else {
                View {
                    attr {
                        margin(24f)
                        flexDirectionColumn()
                    }
                    View {
                        attr {
                            height(48f)
                            flexDirectionRow()
                            backgroundColor(Color.WHITE)
                            borderRadius(8f)
                        }
                        Input {
                            ref { ctx.serverUrlRef = it }
                            attr {
                                flex(1f)
                                fontSize(15f)
                                marginLeft(12f)
                                marginRight(12f)
                                color(ctx.theme.colors.feedContentText)
                                placeholder("服务器地址")
                                placeholderColor(Color(0xFF999999))
                            }
                            event {
                                textDidChange { ctx.serverUrl = it.text }
                            }
                        }
                    }

                    View {
                        attr {
                            height(48f)
                            marginTop(16f)
                            flexDirectionRow()
                            backgroundColor(Color.WHITE)
                            borderRadius(8f)
                        }
                        Input {
                            ref { ctx.usernameRef = it }
                            attr {
                                flex(1f)
                                fontSize(15f)
                                marginLeft(12f)
                                marginRight(12f)
                                color(ctx.theme.colors.feedContentText)
                                placeholder("账号")
                                placeholderColor(Color(0xFF999999))
                            }
                            event {
                                textDidChange { ctx.username = it.text }
                            }
                        }
                    }

                    View {
                        attr {
                            height(48f)
                            marginTop(16f)
                            flexDirectionRow()
                            backgroundColor(Color.WHITE)
                            borderRadius(8f)
                        }
                        Input {
                            ref { ctx.passwordRef = it }
                            attr {
                                flex(1f)
                                fontSize(15f)
                                marginLeft(12f)
                                marginRight(12f)
                                color(ctx.theme.colors.feedContentText)
                                placeholder("密码")
                                placeholderColor(Color(0xFF999999))
                            }
                            event {
                                textDidChange { ctx.password = it.text }
                            }
                        }
                    }

                    View {
                        attr {
                            flexDirectionRow()
                            marginTop(24f)
                        }
                        Button {
                            attr {
                                flex(1f)
                                height(48f)
                                borderRadius(8f)
                                backgroundColor(Color(0xFF23D3FD))
                                titleAttr {
                                    text("登录")
                                    fontSize(16f)
                                    color(Color.WHITE)
                                }
                            }
                            event {
                                click {
                                    ctx.doLogin()
                                }
                            }
                        }
                        View { attr { width(12f) } }
                        Button {
                            attr {
                                flex(1f)
                                height(48f)
                                borderRadius(8f)
                                backgroundColor(Color(0xFFF0F0F0))
                                titleAttr {
                                    text("注册")
                                    fontSize(16f)
                                    color(ctx.theme.colors.feedContentText)
                                }
                            }
                            event {
                                click {
                                    ctx.doRegister()
                                }
                            }
                        }
                    }
                }
            }

            Text {
                attr {
                    text(ctx.status)
                    fontSize(12f)
                    marginTop(16f)
                    marginLeft(24f)
                    color(Color(0xFF666666))
                }
            }
        }
    }

    override fun viewDidLoad() {
        super.viewDidLoad()
        refreshLocalAccounts()
        val forceShowAccounts = pagerData.params.optInt(PARAM_SHOW_ACCOUNTS, 0) == 1
        val forceLoginForm = pagerData.params.optInt(PARAM_FORCE_LOGIN_FORM, 0) == 1
        showLoginForm = when {
            forceLoginForm -> true
            forceShowAccounts -> false
            else -> localAccountUids.isEmpty()
        }
        try {
            serverUrlRef.view?.setText(serverUrl)
            usernameRef.view?.setText(username)
            passwordRef.view?.setText(password)
        } catch (_: Exception) {}
        val shouldTryAutoRestore = !forceShowAccounts && !forceLoginForm
        if (shouldTryAutoRestore && !autoRestoreTriggered) {
            autoRestoreTriggered = true
            CoroutineScope(LoginCoroutineDispatcher).launch {
                delay(AUTO_RESTORE_DELAY_MS)
                val preferredUid = PrivchatPlatform.currentUid()?.trim().orEmpty()
                val uid = if (preferredUid.isNotEmpty()) preferredUid else localAccountUids.firstOrNull().orEmpty()
                if (uid.isNotEmpty()) {
                    runOnKuiklyContext {
                        doQuickEnter(uid, silentUi = true)
                    }
                }
            }
        }
        if (AUTO_LOGIN_ON_LOAD && !autoLoginTriggered) {
            autoLoginTriggered = true
            CoroutineScope(LoginCoroutineDispatcher).launch {
                delay(AUTO_LOGIN_DELAY_MS.toLong())
                println("[LoginFlow] auto login trigger")
                runOnKuiklyContext {
                    doLogin()
                }
            }
        }
    }

    private fun doLogin() {
        doAuth(isRegister = false)
    }

    private fun doRegister() {
        doAuth(isRegister = true)
    }

    private fun refreshLocalAccounts() {
        val fromDisk = PrivchatPlatform.localAccountUids().filter { it.isNotBlank() }
        val currentUid = pagerData.params.optString(PARAM_CURRENT_UID).trim()
        localAccountUids = if (currentUid.isNotEmpty() && fromDisk.none { it == currentUid }) {
            listOf(currentUid) + fromDisk
        } else {
            fromDisk
        }
    }

    private fun doQuickEnter(uid: String, silentUi: Boolean = false) {
        val url = serverUrl.trim()
        val ep = parseServerUrl(url)
        if (ep == null) {
            if (!silentUi) {
                status = "服务器地址格式错误（支持 quic://、wss://、ws://、tcp://）"
            }
            return
        }
        if (!PrivchatPlatform.setCurrentUid(uid)) {
            if (!silentUi) {
                status = "切换账号失败：无法设置 current_user"
            }
            return
        }
        if (!silentUi) {
            status = "账号 $uid 进入中..."
        }
        CoroutineScope(LoginCoroutineDispatcher).launch {
            var client: PrivchatClient? = null
            runCatching {
                val config = PrivchatConfig(
                    dataDir = PrivchatPlatform.dataDir(),
                    assetsDir = null,
                    serverEndpoints = listOf(ep),
                )
                val createResult = PrivchatClient.create(config)
                client = createResult.getOrThrow()
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
                        acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                            .openPage("MainTabPage", JSONObject())
                    }
                }
            }.onFailure { e ->
                val msg = e.message?.ifBlank { null } ?: "未知错误"
                if (!silentUi) {
                    status = "失败: $msg"
                }
                runCatching { client?.close() }
                refreshLocalAccounts()
                if (!silentUi) {
                    showLoginForm = true
                }
            }
        }
    }

    private fun doAuth(isRegister: Boolean) {
        val u = username.trim()
        val p = password.trim()
        val url = serverUrl.trim()
        if (u.isEmpty() || p.isEmpty()) {
            status = "请输入账号和密码"
            return
        }
        val ep = parseServerUrl(url)
        if (ep == null) {
            status = "服务器地址格式错误（支持 quic://、wss://、ws://、tcp://）"
            return
        }
        status = if (isRegister) "注册中..." else "登录中..."
        println("[LoginFlow] doAuth start (isRegister=$isRegister)")

        CoroutineScope(LoginCoroutineDispatcher).launch {
            var client: PrivchatClient? = null
            runCatching {
                val config = PrivchatConfig(
                    dataDir = PrivchatPlatform.dataDir(),
                    assetsDir = null,  // SDK 使用内置 embedded migrations，无需外部 SQL
                    serverEndpoints = listOf(ep),
                )
                println("[LoginFlow] create() start")
                val createResult = PrivchatClient.create(config)
                client = createResult.getOrThrow()
                println("[LoginFlow] create() ok")

                println("[LoginFlow] connect() start")
                client.connect().getOrThrow()
                println("[LoginFlow] connect() ok")
                // connect() 在 Rust 侧会阻塞到连接建立；短延迟再 login 确保服务端 session 已就绪再发 RPC
                delay(AFTER_CONNECT_DELAY_MS)
                println("[LoginFlow] ${if (isRegister) "register()" else "login()"} start")
                val authResult = if (isRegister) {
                    client.register(u, p, PrivchatPlatform.deviceId())
                } else {
                    client.login(u, p, PrivchatPlatform.deviceId())
                }
                val result = authResult.getOrThrow()
                println("[LoginFlow] ${if (isRegister) "register()" else "login()"} ok, userId=${result.userId}")

                println("[LoginFlow] authenticate() start")
                client.authenticate(result.userId, result.token, PrivchatPlatform.deviceId()).getOrThrow()
                println("[LoginFlow] authenticate() ok")
                PrivchatPlatform.setCurrentUid(result.userId.toString())

                // 必须同步完成 runBootstrapSync 再进入后续流程：本地 DB、资料库等在此初始化，未完成则后续操作不成立
                // Android Kuikly 在该阶段更新 reactive 文本会触发 callNative 断言，这里仅输出日志。
                println("[LoginFlow] runBootstrapSync() start")
                client.runBootstrapSync().getOrThrow()
                println("[LoginFlow] runBootstrapSync() ok")

                println("[LoginFlow] setClient()")
                PrivchatClientHolder.setClient(client)
                refreshLocalAccounts()
                showLoginForm = localAccountUids.isEmpty()
                // Android 走平台侧原生跳转，避免 Kuikly context 线程断言。
                if (PrivchatPlatform.navigateToMainTabPage()) {
                    println("[LoginFlow] platform navigation done")
                    return@runCatching
                }

                // iOS/其他平台走 Kuikly Router。
                println("[LoginFlow] schedule openPage in ${OPEN_PAGE_DELAY_MS}ms")
                delay(OPEN_PAGE_DELAY_MS.toLong())
                runOnKuiklyContext {
                    setTimeout(pagerId, 0) {
                        println("[LoginFlow] openPage(MainTabPage) executing")
                        acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                            .openPage("MainTabPage", JSONObject())
                    }
                }
                println("[LoginFlow] doAuth success path done (openPage enqueued)")
            }.onFailure { e ->
                val raw = listOfNotNull(
                    e.message?.takeIf { it.isNotBlank() }?.trim()?.take(300),
                    e.cause?.message?.takeIf { it.isNotBlank() }?.trim()?.take(300),
                    e.toString().takeIf { it.isNotBlank() }?.trim()?.take(300),
                    e::class.simpleName?.takeIf { it.isNotBlank() },
                    e::class.qualifiedName?.takeIf { it.isNotBlank() }
                ).firstOrNull() ?: "未知错误"
                val detail = raw.replace(Regex("\\s+"), " ")
                    .trim()
                    .removePrefix("parameter")
                    .trim()
                    .filter { c -> c.code in 32..0xFFFF }
                val fallback = "未知错误"
                val toShow = if (detail.isEmpty()) fallback else detail
                println("[LoginFlow] onFailure: " + toShow)
                val msg = "失败: " + (if (toShow.isEmpty()) fallback else toShow)
                // Android Kuikly 当前在失败态更新 reactive 文本会触发 callNative 断言，先只打日志避免进程崩溃
                println("[LoginFlow] status update skipped: $msg")
            }
        }
    }
}
