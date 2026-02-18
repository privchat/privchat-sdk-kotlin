package om.netonstream.privchat.sdk.dto

data class UserEntry(
    val userId: ULong,
    val username: String,
    val nickname: String?,
    val avatarUrl: String?,
    val userType: Short,
    val isFriend: Boolean,
    val canSendMessage: Boolean,
    val searchSessionId: ULong?,
    val isOnline: Boolean?,
)
