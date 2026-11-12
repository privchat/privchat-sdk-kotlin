package com.netonstream.privchat.sdk.dto

/**
 * UTC 毫秒在指定 IANA 时区下的本地日期时间分量（TIMEZONE_SPEC §4 原子能力）。
 *
 * 供上层本地化格式化自行组装 i18n 文案；SDK 不在此层做任何语言相关拼接。
 * [dayOfWeek] 采用 ISO 约定：周一=1 … 周日=7。所有字段为本地日历值，时间戳本身
 * 在数据层始终保持 UTC 毫秒，不因时区配置而偏移回写。
 */
data class LocalDateTimeParts(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val dayOfWeek: Int,
)