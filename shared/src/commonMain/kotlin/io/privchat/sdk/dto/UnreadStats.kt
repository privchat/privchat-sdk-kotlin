package io.privchat.sdk.dto

data class UnreadStats(
    val messages: ULong,
    val notifications: ULong,
    val mentions: ULong,
)
