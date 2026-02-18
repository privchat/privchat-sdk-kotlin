package om.netonstream.privchat.sdk.dto

data class GroupCreateResult(
    val groupId: ULong,
    val name: String,
    val description: String?,
    val memberCount: UInt,
    val createdAt: String,
    val creatorId: ULong,
)
