package om.netonstream.privchat.sdk.dto

data class GroupQrCodeJoinResult(
    val status: String,
    val groupId: ULong,
    val requestId: String?,
    val message: String?,
    val expiresAt: ULong?,
    val userId: ULong?,
    val joinedAt: ULong?,
)
