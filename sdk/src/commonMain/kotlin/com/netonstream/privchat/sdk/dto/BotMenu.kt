package com.netonstream.privchat.sdk.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * Bot menu schema v1, returned as the `data` payload of `bot/menu/get` transfer.
 * See: privchat-docs/spec/07-application/BOT_INTERACTION_SPEC.md §8.
 */
@Serializable
data class BotMenu(
    val version: Int = 1,
    val items: List<BotMenuItem> = emptyList(),
)

@Serializable
data class BotMenuItem(
    val id: String,
    val title: String,
    val action: BotMenuAction,
)

/**
 * Three fixed action kinds. Polymorphism by the `type` discriminator (see
 * BOT_INTERACTION_SPEC §4 / §8.2). Spec forbids inventing new kinds outside
 * `transfer` / `message` / `web` without expanding the spec.
 */
@Serializable
sealed class BotMenuAction {
    /** Inline RPC; client发 wire `TransferRequest`，不入 timeline。 */
    @Serializable
    @SerialName("transfer")
    data class Transfer(
        /** route，必须以 `bot/` 开头（spec §10.1）。 */
        val route: String,
        /** 透传给 server，由 client 序列化为 `TransferRequest.body` JSON 字节。 */
        val body: JsonObject? = null,
    ) : BotMenuAction()

    /** 走 SendMessage + message.dispatch；进 timeline，可重放。 */
    @Serializable
    @SerialName("message")
    data class Message(
        /** client 发出的实际消息文本。 */
        val text: String,
        /** 透传到 `SendMessageRequest.metadata`；推荐含 from_menu / menu_item_id / command。 */
        val metadata: JsonObject? = null,
    ) : BotMenuAction()

    /** 打开 URL / webview / mini-app。 */
    @Serializable
    @SerialName("web")
    data class Web(
        /** v1 强制 HTTPS。 */
        val url: String,
        /** 推荐：`browser` / `in_app_webview` / `mini_app`（spec §8.2）。 */
        @SerialName("open_mode")
        val openMode: String = "in_app_webview",
        /** 非空时 client 先走该 route 拿一次性 signed URL，再打开返回的 URL；典型 `bot/web/open`。 */
        @SerialName("prefetch_signed_url_route")
        val prefetchSignedUrlRoute: String? = null,
    ) : BotMenuAction()
}

private val botMenuJson = Json {
    ignoreUnknownKeys = true
    classDiscriminator = "type"
}

/** 解析 `bot/menu/get` 返回的字节为 [BotMenu]。空字节 / 解析失败返回 null。 */
fun decodeBotMenu(payload: ByteArray?): BotMenu? {
    val raw = payload?.decodeToString()?.takeIf { it.isNotBlank() } ?: return null
    return runCatching { botMenuJson.decodeFromString<BotMenu>(raw) }.getOrNull()
}
