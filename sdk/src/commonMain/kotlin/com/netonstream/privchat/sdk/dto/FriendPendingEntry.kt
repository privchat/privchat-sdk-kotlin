package om.netonstream.privchat.sdk.dto

data class FriendPendingEntry(
    val fromUserId: ULong,
    val user: SearchedUserDto,
    val message: String?,
    val createdAt: ULong,
)

data class SearchedUserDto(
    val userId: ULong,
    val username: String,
    val nickname: String,
    val avatarUrl: String?,
    val userType: Short,
    val searchSessionId: ULong,
    val isFriend: Boolean,
    val canSendMessage: Boolean,
)
