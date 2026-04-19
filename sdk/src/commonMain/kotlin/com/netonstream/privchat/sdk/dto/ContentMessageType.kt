package om.netonstream.privchat.sdk.dto

/**
 * 协议标准内容消息类型，对齐 Rust 侧 `privchat_protocol::message::ContentMessageType`。
 *
 * 仅映射协议已知的内容类型。撤回（revoked）是状态 overlay，不在此枚举内——
 * 渲染层应组合 `MessageEntry.isRevoked` 与本枚举共同决定显示分派。
 *
 * 值必须与服务端/协议保持一致，禁止客户端自定义新增。
 */
enum class ContentMessageType(val value: Int) {
    TEXT(0),
    IMAGE(1),
    FILE(2),
    VOICE(3),
    VIDEO(4),
    SYSTEM(5),
    AUDIO(6),
    LOCATION(7),
    CONTACT_CARD(8),
    STICKER(9),
    FORWARD(10);

    companion object {
        /**
         * 将原始协议 Int 归一化到枚举。未识别值（含负数 / 未来协议新增）返回 `null`，
         * 用于客户端兜底（UI 层以 Unknown 渲染）。
         */
        fun fromValue(value: Int): ContentMessageType? = when (value) {
            0 -> TEXT
            1 -> IMAGE
            2 -> FILE
            3 -> VOICE
            4 -> VIDEO
            5 -> SYSTEM
            6 -> AUDIO
            7 -> LOCATION
            8 -> CONTACT_CARD
            9 -> STICKER
            10 -> FORWARD
            else -> null
        }
    }
}

/**
 * `MessageEntry` 的内容类型归一化视图。与 Rust 侧 `MessageVm::content_type()` 同构：
 * - 已知协议类型 → `ContentMessageType`
 * - 未识别 / 未来协议新增 → `null`（UI 兜底走 Unknown）
 *
 * 注：此方法不读取 `isRevoked` 状态，仅反映原始 `messageType` Int。
 * 撤回判断属显示层（RenderType），在 Step 2 引入。
 */
fun MessageEntry.contentType(): ContentMessageType? =
    ContentMessageType.fromValue(messageType)
