package com.netonstream.privchat.sdk.dto

/**
 * 账号级推送偏好。
 *
 * 跨设备一致，存在服务端而不是设备本地：iOS 的远程通知由系统直接展示，
 * "不显示消息预览"这件事只能在推送发出**之前**由服务端做掉——内容一旦随
 * APNs 送到设备，客户端就没有隐藏它的机会了。
 *
 * @property showPreview 通知里是否显示消息内容。false = 只显示"你收到一条新消息"。
 * @property globalMute  全局免打扰：所有会话都不推送。
 */
data class PushPreference(
    val showPreview: Boolean,
    val globalMute: Boolean,
)
