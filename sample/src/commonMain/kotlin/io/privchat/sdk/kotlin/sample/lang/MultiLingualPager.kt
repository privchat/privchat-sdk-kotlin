package io.privchat.sdk.kotlin.sample.lang

import com.tencent.kuikly.core.module.CallbackRef
import com.tencent.kuikly.core.module.NotifyModule
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.reactive.handler.observable
import io.privchat.sdk.kotlin.sample.base.BasePager

internal abstract class MultiLingualPager : BasePager() {
    var resStrings by observable(LangManager.getCurrentResStrings())
    private lateinit var langEventCallbackRef: CallbackRef
    private lateinit var spModule: SharedPreferencesModule
    private lateinit var notifyModule: NotifyModule

    override fun created() {
        super.created()

        // 在 created() 内 acquireModule（官方建议的方式）
        spModule = acquireModule(SharedPreferencesModule.MODULE_NAME)
        notifyModule = acquireModule(NotifyModule.MODULE_NAME)

        // 获取当前语言（只从 SharedPreferences 获取，不访问 pagerData.params）
        val savedLang = spModule.getString(LangManager.KEY_PREF_LANGUAGE)
        val lang = savedLang.takeUnless { it.isEmpty() } ?: LangManager.getCurrentLanguage()

        // 切换全局语言并更新当前页面的字符串资源
        LangManager.changeLanguage(lang)
        resStrings = LangManager.getCurrentResStrings()

        // 注册语言切换事件监听
        langEventCallbackRef = notifyModule.addNotify(LangManager.LANG_CHANGED_EVENT) { _ ->
            langEventCallbackFn()
        }
    }

    open fun langEventCallbackFn() {
        resStrings = LangManager.getCurrentResStrings()
    }

    override fun pageWillDestroy() {
        super.pageWillDestroy()
        notifyModule.removeNotify(LangManager.LANG_CHANGED_EVENT, langEventCallbackRef)
    }
}

