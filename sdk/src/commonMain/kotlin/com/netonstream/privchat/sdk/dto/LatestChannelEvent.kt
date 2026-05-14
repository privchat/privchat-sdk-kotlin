package com.netonstream.privchat.sdk.dto

/**
 * 会话列表里"最近一条事件"快照。
 *
 * **content 字段语义重要更新（架构归正后）**：
 * - TEXT 类型：[content] 是纯文本。
 * - 其它类型（IMAGE / VIDEO / VOICE / FILE / SYSTEM 等）：[content] 是**结构化 JSON 原文**，
 *   不再被 SDK 改写成 "[图片]" 等本地化文案。预览渲染交由 UI 层基于 [messageType] +
 *   [isRevoked] + [content] + 客户端 i18n 字典完成（唯一渲染入口）。
 */
data class LatestChannelEvent(
    val eventType: String,
    /** 原始 content（TEXT = 纯文本；其他类型 = 结构化 JSON）。 */
    val content: String,
    val timestamp: ULong,
    /**
     * 最后一条消息的 ContentMessageType 整型值。null 表示 SDK 尚未拿到具体类型
     * （兼容旧数据 / 同步未就绪），UI 层按 UNKNOWN 处理。
     */
    val messageType: Int? = null,
    /** 最后一条消息是否已被撤回。 */
    val isRevoked: Boolean = false,
)
