package om.netonstream.privchat.sdk.dto

data class FriendPendingEntry(
    val fromUserId: ULong,
    val message: String?,
    val createdAt: String,
)
