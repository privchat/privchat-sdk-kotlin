package io.privchat.sdk.kotlin.sample.util

/**
 * 会话列表时间格式化
 * lastTs: 毫秒时间戳
 */
expect object TimeFormatter {
    fun formatChannelTime(lastTs: Long): String
    fun currentTimeMillis(): Long
}
