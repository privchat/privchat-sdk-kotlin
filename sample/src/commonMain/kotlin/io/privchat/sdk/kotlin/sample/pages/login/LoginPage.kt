package io.privchat.sdk.kotlin.sample.pages.login

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import com.tencent.kuikly.core.timer.setTimeout
import io.privchat.sdk.kotlin.sample.base.BasePager
import io.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import io.privchat.sdk.kotlin.sample.privchat.PrivchatPlatform
import io.privchat.sdk.kotlin.sample.theme.ThemeManager
import io.privchat.sdk.PrivchatClient
import io.privchat.sdk.PrivchatConfig
import io.privchat.sdk.parseServerUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 登录/注册页面
 */
@Page("LoginPage")
internal class LoginPage : BasePager() {
    private var theme by observable(ThemeManager.getTheme())
    private var serverUrl by observable("quic://127.0.0.1:8082")
    private var username by observable("")
    private var password by observable("")
    private var status by observable("")

    private lateinit var serverUrlRef: ViewRef<InputView>
    private lateinit var usernameRef: ViewRef<InputView>
    private lateinit var passwordRef: ViewRef<InputView>

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
        try {
            serverUrlRef.view?.setText(serverUrl)
        } catch (_: Exception) {}
    }

    private fun doLogin() {
        doAuth(isRegister = false)
    }

    private fun doRegister() {
        doAuth(isRegister = true)
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

        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                val config = PrivchatConfig(
                    dataDir = PrivchatPlatform.dataDir(),
                    assetsDir = null,  // SDK 使用内置 embedded migrations，无需外部 SQL
                    serverEndpoints = listOf(ep),
                )
                val createResult = PrivchatClient.create(config)
                val client = createResult.getOrThrow()
                client.connect().getOrThrow()
                val authResult = if (isRegister) {
                    client.register(u, p, PrivchatPlatform.deviceId())
                } else {
                    client.login(u, p, PrivchatPlatform.deviceId())
                }
                val result = authResult.getOrThrow()
                client.authenticate(result.userId, result.token, PrivchatPlatform.deviceId()).getOrThrow()
                client.runBootstrapSync().getOrThrow()
                PrivchatClientHolder.setClient(client)
                // 使用 setTimeout 在 Kuikly 渲染上下文执行 openPage，避免 runOnMain + callNative AssertionError
                setTimeout(pagerId, 0) {
                    acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                        .openPage("MainTabPage", JSONObject())
                }
            }.onFailure { e ->
                val msg = "失败: ${e.message ?: e.toString()}"
                // 使用 Kuikly setTimeout 在渲染上下文更新 UI，避免 runOnMain + callNative AssertionError
                setTimeout(pagerId, 0) { status = msg }
            }
        }
    }
}
