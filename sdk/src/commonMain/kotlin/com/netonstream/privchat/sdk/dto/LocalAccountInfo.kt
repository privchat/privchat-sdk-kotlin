package om.netonstream.privchat.sdk.dto

data class LocalAccountInfo(
    val uid: String,
    val createdAt: Long,
    val lastLoginAt: Long,
    val isActive: Boolean,
)
