package io.privchat.sdk

import io.privchat.sdk.dto.*

actual fun parseServerUrl(url: String): ServerEndpoint? = null // macOS implementation pending

/**
 * macOS actual - Trixnity cinterop 链接 privchat-ffi.a。
 * 与 iosMain 共享实现逻辑，当前为占位。
 */
actual class PrivchatClient actual constructor() {
    actual suspend fun connect(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun disconnect(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual fun isConnected(): Boolean = throw NotImplementedError("macOS implementation pending")
    actual fun connectionState(): ConnectionState = throw NotImplementedError("macOS implementation pending")
    actual fun currentUserId(): ULong? = throw NotImplementedError("macOS implementation pending")
    actual suspend fun shutdown(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun register(username: String, password: String, deviceId: String): Result<AuthResult> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun login(username: String, password: String, deviceId: String): Result<AuthResult> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun authenticate(userId: ULong, token: String, deviceId: String): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun logout(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun sendText(channelId: ULong, channelType: Int, text: String): Result<ULong> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun sendText(channelId: ULong, channelType: Int, text: String, options: SendMessageOptions): Result<ULong> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun sendMedia(channelId: ULong, filePath: String, options: SendMessageOptions?): Result<Pair<ULong, AttachmentInfo>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun retryMessage(messageId: ULong): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun markAsRead(channelId: ULong, messageId: ULong): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun markFullyReadAt(channelId: ULong, messageId: ULong): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun revokeMessage(messageId: ULong): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun editMessage(messageId: ULong, newContent: String): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun addReaction(messageId: ULong, emoji: String): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun removeReaction(messageId: ULong, emoji: String): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun reactions(channelId: ULong, messageId: ULong): Result<List<ReactionChip>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun reactionsBatch(channelId: ULong, messageIds: List<ULong>): Result<Map<ULong, List<ReactionChip>>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun isEventReadBy(channelId: ULong, messageId: ULong, userId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun seenByForEvent(channelId: ULong, messageId: ULong, limit: UInt?): Result<List<SeenByEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun searchMessages(query: String, channelId: String?): Result<List<MessageEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun getMessages(channelId: ULong, limit: UInt, beforeSeq: ULong?): Result<List<MessageEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getMessageById(messageId: ULong): Result<MessageEntry?> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun paginateBack(channelId: ULong, beforeSeq: ULong, limit: UInt): Result<List<MessageEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun paginateForward(channelId: ULong, afterSeq: ULong, limit: UInt): Result<List<MessageEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getChannels(limit: UInt, offset: UInt): Result<List<ChannelListEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getFriends(limit: UInt?, offset: UInt?): Result<List<FriendEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getGroups(limit: UInt?, offset: UInt?): Result<List<GroupEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getGroupMembers(groupId: ULong, limit: UInt?, offset: UInt?): Result<List<GroupMemberEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun runBootstrapSync(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual fun runBootstrapSyncInBackground() {}
    actual suspend fun isBootstrapCompleted(): Result<Boolean> = Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun syncEntities(type: String, scope: String?): Result<UInt> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun syncChannel(channelId: ULong, channelType: UByte): Result<SyncStateEntry> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun syncAllChannels(): Result<List<SyncStateEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getChannelSyncState(channelId: ULong, channelType: UByte): Result<SyncStateEntry> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun needsSync(channelId: ULong, channelType: UByte): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun startSupervisedSync(observer: SyncObserver): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun stopSupervisedSync(): Result<Unit> = Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun markChannelRead(channelId: ULong, channelType: Int): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun pinChannel(channelId: ULong, pin: Boolean): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun hideChannel(channelId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun muteChannel(channelId: ULong, muted: Boolean): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun channelUnreadStats(channelId: ULong): Result<UnreadStats> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun ownLastRead(channelId: ULong): Result<LastReadPosition> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun setChannelNotificationMode(channelId: ULong, mode: NotificationMode): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun getOrCreateDirectChannel(peerUserId: ULong): Result<GetOrCreateDirectChannelResult> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun searchUsers(query: String): Result<List<UserEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun sendFriendRequest(toUserId: ULong, remark: String?, searchSessionId: String?): Result<ULong> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun acceptFriendRequest(fromUserId: ULong): Result<ULong> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun rejectFriendRequest(fromUserId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun deleteFriend(userId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun listFriendPendingRequests(): Result<List<FriendPendingEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun createGroup(name: String, memberIds: List<ULong>): Result<GroupCreateResult> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun inviteToGroup(groupId: ULong, userIds: List<ULong>): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun removeGroupMember(groupId: ULong, userId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun leaveGroup(groupId: ULong): Result<Boolean> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun joinGroupByQrcode(qrcode: String): Result<GroupQrCodeJoinResult> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun subscribePresence(userIds: List<ULong>): Result<List<PresenceEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual fun unsubscribePresence(userIds: List<ULong>) {}
    actual fun getPresence(userId: ULong): PresenceEntry? = null
    actual fun batchGetPresence(userIds: List<ULong>): List<PresenceEntry> = emptyList()
    actual suspend fun fetchPresence(userIds: List<ULong>): Result<List<PresenceEntry>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun sendTyping(channelId: ULong): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun stopTyping(channelId: ULong): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual suspend fun sendAttachmentFromPath(channelId: ULong, path: String, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun sendAttachmentBytes(channelId: ULong, filename: String, mimeType: String, data: ByteArray, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun downloadAttachmentToCache(fileId: String, fileUrl: String, progress: ProgressObserver?): Result<String> =
        Result.failure(NotImplementedError("macOS implementation pending"))
    actual suspend fun downloadAttachmentToPath(fileUrl: String, outputPath: String, progress: ProgressObserver?): Result<Unit> =
        Result.failure(NotImplementedError("macOS implementation pending"))

    actual fun setVideoProcessHook(hook: VideoProcessHook?) {}
    actual fun removeVideoProcessHook() {}

    actual companion object {
        actual fun create(config: PrivchatConfig): Result<PrivchatClient> =
            Result.failure(NotImplementedError("macOS implementation pending"))
    }
}
