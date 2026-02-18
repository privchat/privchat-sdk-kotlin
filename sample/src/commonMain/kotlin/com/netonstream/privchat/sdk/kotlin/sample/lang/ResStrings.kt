package om.netonstream.privchat.sdk.kotlin.sample.lang

import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

data class ResStrings(
    val language: String,

    // 底部导航栏
    val btmBarLive: String,
    val btmBarRanking: String,
    val btmBarEntertainment: String,
    val btmBarMe: String,

    // 顶部标签
    val topBarFollow: String,
    val topBarRecommend: String,
    val topBarTrending: String,

    // 加载状态
    val loading: String,
    val refreshing: String,
    val refreshDone: String,
    val pullToRefresh: String,
    val releaseToRefresh: String,
    val loadMore: String,
    val noMoreData: String,
    val tapToRetry: String,
    val loadingFailed: String,

    // 设置页面
    val setting: String,
    val themeHint: String,
    val languageHint: String,
) {
    companion object {
        private const val KEY_LANGUAGE = "language"
        private const val KEY_BTM_BAR_LIVE = "btmBarLive"
        private const val KEY_BTM_BAR_RANKING = "btmBarRanking"
        private const val KEY_BTM_BAR_ENTERTAINMENT = "btmBarEntertainment"
        private const val KEY_BTM_BAR_ME = "btmBarMe"
        private const val KEY_TOP_BAR_FOLLOW = "topBarFollow"
        private const val KEY_TOP_BAR_RECOMMEND = "topBarRecommend"
        private const val KEY_TOP_BAR_TRENDING = "topBarTrending"
        private const val KEY_LOADING = "loading"
        private const val KEY_REFRESHING = "refreshing"
        private const val KEY_REFRESH_DONE = "refreshDone"
        private const val KEY_PULL_TO_REFRESH = "pullToRefresh"
        private const val KEY_RELEASE_TO_REFRESH = "releaseToRefresh"
        private const val KEY_LOAD_MORE = "loadMore"
        private const val KEY_NO_MORE_DATA = "noMoreData"
        private const val KEY_TAP_TO_RETRY = "tapToRetry"
        private const val KEY_LOADING_FAILED = "loadingFailed"
        private const val KEY_SETTING = "setting"
        private const val KEY_THEME_HINT = "themeHint"
        private const val KEY_LANGUAGE_HINT = "languageHint"

        fun fromJson(json: JSONObject): ResStrings {
            return ResStrings(
                language = json.optString(KEY_LANGUAGE),
                btmBarLive = json.optString(KEY_BTM_BAR_LIVE),
                btmBarRanking = json.optString(KEY_BTM_BAR_RANKING),
                btmBarEntertainment = json.optString(KEY_BTM_BAR_ENTERTAINMENT),
                btmBarMe = json.optString(KEY_BTM_BAR_ME),
                topBarFollow = json.optString(KEY_TOP_BAR_FOLLOW),
                topBarRecommend = json.optString(KEY_TOP_BAR_RECOMMEND),
                topBarTrending = json.optString(KEY_TOP_BAR_TRENDING),
                loading = json.optString(KEY_LOADING),
                refreshing = json.optString(KEY_REFRESHING),
                refreshDone = json.optString(KEY_REFRESH_DONE),
                pullToRefresh = json.optString(KEY_PULL_TO_REFRESH),
                releaseToRefresh = json.optString(KEY_RELEASE_TO_REFRESH),
                loadMore = json.optString(KEY_LOAD_MORE),
                noMoreData = json.optString(KEY_NO_MORE_DATA),
                tapToRetry = json.optString(KEY_TAP_TO_RETRY),
                loadingFailed = json.optString(KEY_LOADING_FAILED),
                setting = json.optString(KEY_SETTING),
                themeHint = json.optString(KEY_THEME_HINT),
                languageHint = json.optString(KEY_LANGUAGE_HINT)
            )
        }
    }
}

val SimplifiedChinese = ResStrings(
    language = "zh-Hans",
    btmBarLive = "直播",
    btmBarRanking = "排行",
    btmBarEntertainment = "娱乐",
    btmBarMe = "我的",
    topBarFollow = "关注",
    topBarRecommend = "推荐",
    topBarTrending = "热门",
    loading = "加载中...",
    refreshing = "正在刷新",
    refreshDone = "刷新成功",
    pullToRefresh = "下拉刷新",
    releaseToRefresh = "松手即可刷新",
    loadMore = "加载更多",
    noMoreData = "无更多数据",
    tapToRetry = "点击重试",
    loadingFailed = "加载失败",
    setting = "设置",
    themeHint = "选择主题",
    languageHint = "请选择语言"
)

val TraditionalChinese = ResStrings(
    language = "zh-Hant",
    btmBarLive = "直播",
    btmBarRanking = "排行",
    btmBarEntertainment = "娛樂",
    btmBarMe = "我的",
    topBarFollow = "關注",
    topBarRecommend = "推薦",
    topBarTrending = "熱門",
    loading = "加載中...",
    refreshing = "正在刷新",
    refreshDone = "刷新成功",
    pullToRefresh = "下拉刷新",
    releaseToRefresh = "松手即可刷新",
    loadMore = "加載更多",
    noMoreData = "無更多數據",
    tapToRetry = "點擊重試",
    loadingFailed = "加載失敗",
    setting = "設置",
    themeHint = "選擇主題",
    languageHint = "請選擇語言"
)

val enJsonString = """
{
  "language": "en",
  "btmBarLive": "Live",
  "btmBarRanking": "Ranking",
  "btmBarEntertainment": "Entertainment",
  "btmBarMe": "Me",
  "topBarFollow": "Following",
  "topBarRecommend": "Recommend",
  "topBarTrending": "Trending",
  "loading": "Loading...",
  "refreshing": "Refreshing",
  "refreshDone": "Refresh successful",
  "pullToRefresh": "Pull to refresh",
  "releaseToRefresh": "Release to refresh",
  "loadMore": "Load more",
  "noMoreData": "No more data",
  "tapToRetry": "Tap to retry",
  "loadingFailed": "Loading failed",
  "setting": "Settings",
  "themeHint": "Select theme",
  "languageHint": "Please select language"
}
""".trimIndent()

