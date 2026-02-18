package om.netonstream.privchat.sdk.dto

data class GroupEntry(
    val groupId: ULong,
    val name: String?,
    val avatar: String,
    val ownerId: ULong?,
    val isDismissed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
