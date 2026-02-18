package om.netonstream.privchat.sdk.kotlin.sample.util

import platform.Foundation.*

actual object TimeFormatter {
    actual fun formatChannelTime(lastTs: Long): String {
        if (lastTs <= 0) return ""
        val date = NSDate.dateWithTimeIntervalSince1970(lastTs / 1000.0)
        val now = NSDate().timeIntervalSince1970 * 1000
        val diff = (now - lastTs).toLong()
        val formatter = NSDateFormatter()
        return when {
            diff < 60_000 -> "刚刚"
            diff < 24 * 60 * 60 * 1000 -> {
                formatter.dateFormat = "HH:mm"
                formatter.stringFromDate(date) ?: ""
            }
            diff < 2 * 24 * 60 * 60 * 1000 -> {
                formatter.dateFormat = "HH:mm"
                "昨天 ${formatter.stringFromDate(date) ?: ""}"
            }
            diff < 7 * 24 * 60 * 60 * 1000 -> {
                formatter.dateFormat = "M/d HH:mm"
                formatter.stringFromDate(date) ?: ""
            }
            else -> {
                formatter.dateFormat = "M/d"
                formatter.stringFromDate(date) ?: ""
            }
        }
    }

    actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
}
