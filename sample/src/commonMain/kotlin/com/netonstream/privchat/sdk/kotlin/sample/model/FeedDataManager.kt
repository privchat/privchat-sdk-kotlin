package om.netonstream.privchat.sdk.kotlin.sample.model

import com.tencent.kuikly.core.manager.PagerManager
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.timer.setTimeout
import om.netonstream.privchat.sdk.kotlin.sample.base.BridgeModule
import om.netonstream.privchat.sdk.kotlin.sample.pages.feed.FeedItem

enum class FeedType(val value: String) {
    FOLLOW("follow"),      // 关注
    RECOMMEND("recommend"), // 推荐
    TRENDING("trending")   // 热门
}

internal object FeedDataManager {
    private var callbackMap: HashMap<Pair<FeedType, Int>, Any> = hashMapOf()

    private fun genKey(type: FeedType, page: Int): Pair<FeedType, Int> {
        return type to page
    }

    /**
     * 请求数据
     * @param type 数据类型
     * @param page 页码（从0开始，0表示刷新）
     * @param callback 回调函数 (数据列表, 错误信息)
     */
    internal fun requestFeeds(
        type: FeedType,
        page: Int,
        callback: (List<FeedItem>, String) -> Unit
    ) {
        val key = genKey(type, page)
        
        // 已经在请求中了
        if (callbackMap.containsKey(key)) {
            callback(listOf(), "in request")
            return
        }
        
        // 模拟最多3页数据
        if (page >= 3) {
            callback(listOf(), "")
            return
        }

        callbackMap[key] = callback
        
        // 使用 BridgeModule 读取 assets 中的 JSON 文件
        // 如果没有 JSON 文件，则使用模拟数据
        val pathName = getFileName(type, page)
        val bridgeModule = PagerManager.getCurrentPager().getModule<BridgeModule>(BridgeModule.MODULE_NAME)

        if (bridgeModule != null) {
            bridgeModule.readAssetFile(pathName) { json ->
                if (json == null || json.optString("error").isNotEmpty()) {
                    // 如果文件不存在，使用模拟数据
                    setTimeout(500) {
                        val mockData = generateMockData(type, page)
                        callback(mockData, "")
                        callbackMap.remove(key)
                    }
                } else {
                    // JSON 格式：{result: "文件内容（JSON字符串）", error: ""}
                    // 需要解析 result 字符串，文件内容可能是数组格式
                    try {
                        val resultStr = json.optString("result")
                        if (resultStr.isNotEmpty()) {
                            // 文件内容直接是数组，需要包装成 {result: [...]}
                            val jsonArray = com.tencent.kuikly.core.nvi.serialization.json.JSONArray(resultStr)
                            val wrapperJson = JSONObject().apply {
                                put("result", jsonArray)
                            }
                            val feeds = parseJson(wrapperJson)
                            callback(feeds, "")
                        } else {
                            // 如果没有 result，使用模拟数据
                            val mockData = generateMockData(type, page)
                            callback(mockData, "")
                        }
                    } catch (e: Exception) {
                        // 如果解析失败，使用模拟数据
                        val mockData = generateMockData(type, page)
                        callback(mockData, "")
                    }
                    callbackMap.remove(key)
                }
            }
        } else {
            // 如果没有 BridgeModule，使用模拟数据（延迟模拟网络请求）
            setTimeout(500) {
                val mockData = generateMockData(type, page)
                callback(mockData, "")
                callbackMap.remove(key)
            }
        }
    }

    /**
     * 从 JSON 解析数据
     */
    private fun parseJson(json: JSONObject): List<FeedItem> {
        val jsonArray = json.optJSONArray("result")
        val feeds = mutableListOf<FeedItem>()
        jsonArray?.let {
            for (i in 0 until it.length()) {
                it.optJSONObject(i)?.let { itemJson ->
                    val feedItem = FeedItem(
                        id = itemJson.optInt("id", i),
                        title = itemJson.optString("title", ""),
                        content = itemJson.optString("content", ""),
                        author = itemJson.optString("author", ""),
                        avatar = itemJson.optString("avatar", ""),
                        imageUrl = itemJson.optString("imageUrl", "")
                    )
                    feeds.add(feedItem)
                }
            }
        }
        return feeds
    }

    /**
     * 获取文件名
     */
    private fun getFileName(type: FeedType, page: Int): String {
        return "HomePage/json/${type.value}_${page}.json"
    }

    /**
     * 生成模拟数据
     */
    private fun generateMockData(type: FeedType, page: Int): List<FeedItem> {
        val feedList = mutableListOf<FeedItem>()
        val startIndex = page * 10
        val typeName = when (type) {
            FeedType.FOLLOW -> "关注"
            FeedType.RECOMMEND -> "推荐"
            FeedType.TRENDING -> "热门"
        }

        for (i in 0 until 10) {
            feedList.add(
                FeedItem(
                    id = startIndex + i,
                    title = "$typeName - 第 ${startIndex + i + 1} 条内容",
                    content = "这是第 ${startIndex + i + 1} 条内容的详细描述，包含了丰富的信息和内容...",
                    author = "作者${i % 5 + 1}",
                    avatar = "",
                    imageUrl = ""
                )
            )
        }
        return feedList
    }

}

