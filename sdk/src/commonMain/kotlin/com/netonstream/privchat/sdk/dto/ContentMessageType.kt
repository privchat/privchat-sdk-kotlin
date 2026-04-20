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
    // 编号按业务优先级（0=文字 最常用 … 10=转发 最少用），与 Rust 协议保持一致。
    TEXT(0),
    VOICE(1),
    IMAGE(2),
    VIDEO(3),
    // FILE 涵盖普通文件，包括音频文件（mp3 / wav 等）。协议层无独立 Audio 类型。
    FILE(4),
    SYSTEM(5),
    STICKER(6),
    CONTACT_CARD(7),
    LOCATION(8),
    // LINK 的 title / description / thumbnail 由 SDK 宿主通过预览回调（类比视频抽帧 hook）填充；
    // 未注册回调时仅带 URL，UI 显示空白缩略图。
    LINK(9),
    FORWARD(10);

    companion object {
        /**
         * 将原始协议 Int 归一化到枚举。未识别值（含负数 / 未来协议新增）返回 `null`，
         * 用于客户端兜底（UI 层以 Unknown 渲染）。
         */
        fun fromValue(value: Int): ContentMessageType? = when (value) {
            0 -> TEXT
            1 -> VOICE
            2 -> IMAGE
            3 -> VIDEO
            4 -> FILE
            5 -> SYSTEM
            6 -> STICKER
            7 -> CONTACT_CARD
            8 -> LOCATION
            9 -> LINK
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
