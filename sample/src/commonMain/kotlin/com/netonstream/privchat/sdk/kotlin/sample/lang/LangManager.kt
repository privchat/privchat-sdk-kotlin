package om.netonstream.privchat.sdk.kotlin.sample.lang

import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

object LangManager {
    private const val LANG_MANAGER_TAG = "LangManager"
    const val KEY_PREF_LANGUAGE = "lang"
    const val KEY_PARAM_SYSTEM_LANGUAGE = "sysLang"
    const val LANG_CHANGED_EVENT = "langChanged"
    
    val SUPPORTED_LANGUAGES = mapOf(
        "简体中文" to "zh-Hans",
        "繁體中文" to "zh-Hant",
        "English" to "en"
    )
    
    val SETTING_HINTS = mapOf(
        "zh-Hans" to "正在设置语言...",
        "zh-Hant" to "正在設置語言...",
        "en" to "Setting..."
    )

    // 当前语言和资源
    private val defaultLanguage = "zh-Hans"
    private val defaultResStrings = SimplifiedChinese
    private var language: String = defaultLanguage
    private var resStrings: ResStrings = defaultResStrings

    /**
     * 获取资源字符串
     */
    private fun getResString(lang: String): ResStrings {
        return when (lang) {
            "zh-Hans" -> SimplifiedChinese
            "zh-Hant" -> TraditionalChinese
            "en" -> ResStrings.fromJson(JSONObject(enJsonString))
            else -> defaultResStrings
        }
    }

    /**
     * 获取当前资源字符串
     */
    fun getCurrentResStrings(): ResStrings = resStrings

    /**
     * 获取当前语言
     */
    fun getCurrentLanguage(): String = language

    /**
     * 切换语言
     */
    fun changeLanguage(lang: String) {
        if (lang !in SUPPORTED_LANGUAGES.values) {
            language = defaultLanguage
            resStrings = defaultResStrings
            KLog.e(LANG_MANAGER_TAG, "Unsupported language: $lang")
            return
        }

        language = lang
        resStrings = getResString(lang)
        KLog.d(LANG_MANAGER_TAG, "Language changed to: $lang")
    }
}

