package io.privchat.sdk.kotlin.sample.theme

import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

data class Theme(
    var colors: ThemeColors,
    var asset: String
)

object ThemeManager {
    // 主题配置
    private val theme: Theme = Theme(
        colors = lightColorScheme,
        asset = "default"
    )

    // 主题常量
    private const val ASSET_PATH_PREFIX = "themes/"
    val COLOR_SCHEME_MAP = mapOf(
        "light" to lightColorScheme,
        "dark" to darkColorScheme
    )
    val ASSET_SCHEME_LIST = listOf("default")
    const val SKIN_CHANGED_EVENT = "skinChanged"

    const val PREF_KEY_COLOR = "colorTheme"
    const val PREF_KEY_ASSET = "assetTheme"

    enum class ThemeType { COLOR, ASSET }

    // 公共API
    fun getTheme(): Theme {
        return theme.copy()
    }

    fun changeColorScheme(themeName: String) {
        theme.colors = COLOR_SCHEME_MAP[themeName] ?: lightColorScheme
    }

    fun changeAssetScheme(themeName: String) {
        theme.asset = themeName
    }

    fun loadColorFromJson(json: JSONObject) {
        theme.colors = ThemeColors.fromJson(json)
    }

    fun getAssetUri(themeName: String, asset: String): ImageUri {
        return ImageUri.commonAssets("$ASSET_PATH_PREFIX$themeName/$asset")
    }
}

