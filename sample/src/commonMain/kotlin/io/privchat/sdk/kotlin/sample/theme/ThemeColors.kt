package io.privchat.sdk.kotlin.sample.theme

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

data class ThemeColors(
    // 主题色
    val primary: Color,
    val background: Color,
    val backgroundElement: Color,

    // 顶部栏
    val topBarBackground: Color,
    val topBarTextUnfocused: Color,
    val topBarTextFocused: Color,
    val topBarIndicator: Color,

    // 底部栏
    val tabBarBackground: Color,
    val tabBarTextUnfocused: Color,
    val tabBarTextFocused: Color,
    val tabBarIconUnfocused: Color,
    val tabBarIconFocused: Color,

    val feedBackground: Color,

    // 内容文本
    val feedContentText: Color,
    val feedContentDivider: Color,
) {
    companion object {
        private const val KEY_PRIMARY = "primary"
        private const val KEY_BACKGROUND = "background"
        private const val KEY_BACKGROUND_ELEMENT = "backgroundElement"
        private const val KEY_TOP_BAR_BACKGROUND = "topBarBackground"
        private const val KEY_TOP_BAR_TEXT_UNFOCUSED = "topBarTextUnfocused"
        private const val KEY_TOP_BAR_TEXT_FOCUSED = "topBarTextFocused"
        private const val KEY_TOP_BAR_INDICATOR = "topBarIndicator"
        private const val KEY_TAB_BAR_BACKGROUND = "tabBarBackground"
        private const val KEY_TAB_BAR_TEXT_UNFOCUSED = "tabBarTextUnfocused"
        private const val KEY_TAB_BAR_TEXT_FOCUSED = "tabBarTextFocused"
        private const val KEY_TAB_BAR_ICON_UNFOCUSED = "tabBarIconUnfocused"
        private const val KEY_TAB_BAR_ICON_FOCUSED = "tabBarIconFocused"
        private const val KEY_FEED_BACKGROUND = "feedBackground"
        private const val KEY_FEED_CONTENT_TEXT = "feedContentText"
        private const val KEY_FEED_CONTENT_DIVIDER = "feedContentDivider"

        fun fromJson(json: JSONObject): ThemeColors {
            return ThemeColors(
                primary = Color(json.optString(KEY_PRIMARY)),
                background = Color(json.optString(KEY_BACKGROUND)),
                backgroundElement = Color(json.optString(KEY_BACKGROUND_ELEMENT)),
                topBarBackground = Color(json.optString(KEY_TOP_BAR_BACKGROUND)),
                topBarTextUnfocused = Color(json.optString(KEY_TOP_BAR_TEXT_UNFOCUSED)),
                topBarTextFocused = Color(json.optString(KEY_TOP_BAR_TEXT_FOCUSED)),
                topBarIndicator = Color(json.optString(KEY_TOP_BAR_INDICATOR)),
                tabBarBackground = Color(json.optString(KEY_TAB_BAR_BACKGROUND)),
                tabBarTextUnfocused = Color(json.optString(KEY_TAB_BAR_TEXT_UNFOCUSED)),
                tabBarTextFocused = Color(json.optString(KEY_TAB_BAR_TEXT_FOCUSED)),
                tabBarIconUnfocused = Color(json.optString(KEY_TAB_BAR_ICON_UNFOCUSED)),
                tabBarIconFocused = Color(json.optString(KEY_TAB_BAR_ICON_FOCUSED)),
                feedBackground = Color(json.optString(KEY_FEED_BACKGROUND)),
                feedContentText = Color(json.optString(KEY_FEED_CONTENT_TEXT)),
                feedContentDivider = Color(json.optString(KEY_FEED_CONTENT_DIVIDER))
            )
        }
    }
}

val lightColorScheme = ThemeColors(
    primary = Color.WHITE,
    background = Color(0xFFFFFFFF), // iOS 系统白色背景
    backgroundElement = Color(0xFF000000), // 黑色文字
    topBarBackground = Color(0xFFFFFFFF), // iOS 系统顶部栏白色
    topBarTextUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中）
    topBarTextFocused = Color(0xFF007AFF), // iOS 系统蓝色（选中）
    topBarIndicator = Color(0xFF007AFF), // iOS 系统蓝色指示器
    tabBarBackground = Color(0xFFFFFFFF), // iOS 系统底部栏白色
    tabBarTextUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中）
    tabBarTextFocused = Color(0xFF007AFF), // iOS 系统蓝色（选中）
    tabBarIconUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中）
    tabBarIconFocused = Color(0xFF007AFF), // iOS 系统蓝色（选中）
    feedBackground = Color(0xFFFFFFFF), // 白色内容背景
    feedContentText = Color(0xFF000000), // 黑色文字
    feedContentDivider = Color(0xFFC6C6C8) // iOS 标准分割线颜色
)

val darkColorScheme = ThemeColors(
    primary = Color(0xFF000000),
    background = Color(0xFF000000), // iOS 系统暗色背景（纯黑）
    backgroundElement = Color(0xFFFFFFFF), // 白色文字
    topBarBackground = Color(0xFF1C1C1E), // iOS 系统暗色顶部栏
    topBarTextUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中，与亮色模式一致）
    topBarTextFocused = Color(0xFF0A84FF), // iOS 系统蓝色（暗色模式下的蓝色）
    topBarIndicator = Color(0xFF0A84FF), // iOS 系统蓝色指示器
    tabBarBackground = Color(0xFF1C1C1E), // iOS 系统暗色底部栏
    tabBarTextUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中）
    tabBarTextFocused = Color(0xFF0A84FF), // iOS 系统蓝色（选中）
    tabBarIconUnfocused = Color(0xFF8E8E93), // iOS 标准灰色（未选中）
    tabBarIconFocused = Color(0xFF0A84FF), // iOS 系统蓝色（选中）
    feedBackground = Color(0xFF1C1C1E), // 暗色内容背景
    feedContentText = Color(0xFFF2F2F7), // iOS 系统浅色文字
    feedContentDivider = Color(0xFF38383A) // iOS 系统暗色分割线
)

