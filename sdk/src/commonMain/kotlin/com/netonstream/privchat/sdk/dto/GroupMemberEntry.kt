package om.netonstream.privchat.sdk.dto

data class GroupMemberEntry(
    val userId: ULong,
    val channelId: ULong,
    val channelType: Int,
    val name: String,
    val remark: String,
    val avatar: String,
    val role: Int,
    val status: Int,
    val inviteUserId: ULong,
)
