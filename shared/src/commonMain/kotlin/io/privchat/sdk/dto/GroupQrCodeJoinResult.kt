package io.privchat.sdk.dto

data class GroupQrCodeJoinResult(
    val status: String,
    val groupId: ULong,
    val requestId: String?,
    val message: String?,
    val expiresAt: String?,
    val userId: ULong?,
    val joinedAt: String?,
)
