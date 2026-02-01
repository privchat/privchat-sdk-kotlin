package io.privchat.sdk.kotlin.sample.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual object TimeFormatter {
    private val todayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val yesterdayFormat = SimpleDateFormat("昨天 HH:mm", Locale.getDefault())
    private val weekFormat = SimpleDateFormat("M/d HH:mm", Locale.getDefault())
    private val yearFormat = SimpleDateFormat("M/d", Locale.getDefault())

    actual fun formatChannelTime(lastTs: Long): String {
        if (lastTs <= 0) return ""
        val date = Date(lastTs)
        val now = System.currentTimeMillis()
        val diff = now - lastTs
        return when {
            diff < 60_000 -> "刚刚"
            diff < 24 * 60 * 60 * 1000 -> todayFormat.format(date)
            diff < 2 * 24 * 60 * 60 * 1000 -> yesterdayFormat.format(date)
            diff < 7 * 24 * 60 * 60 * 1000 -> weekFormat.format(date)
            else -> yearFormat.format(date)
        }
    }

    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}
