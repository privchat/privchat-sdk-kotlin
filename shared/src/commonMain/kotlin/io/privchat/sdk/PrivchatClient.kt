package io.privchat.sdk

import io.privchat.sdk.dto.*
import kotlinx.coroutines.flow.Flow

/**
 * 解析服务器 URL 为 ServerEndpoint（使用 SDK 内置解析逻辑）
 * 支持 quic://, wss://, ws://, tcp://
 */
expect fun parseServerUrl(url: String): ServerEndpoint?

/**
 * Privchat SDK 客户端 - 符合 [SDK_API_CONTRACT](https://github.com/privchat/privchat-sdk/blob/main/docs/SDK_API_CONTRACT.md)
 *
 * expect/actual 由各平台实现，API 与 privchat-sdk-android / privchat-sdk-swift 保持一致。
 */
expect class PrivchatClient private constructor() {
    // ========== Client ==========
    suspend fun connect(): Result<Unit>
    suspend fun disconnect(): Result<Unit>
    suspend fun setNetworkHint(hint: NetworkHint): Result<Unit>
    /** Release the underlying SDK handle. Call when this client is no longer needed (e.g. after disconnect on failure). */
    fun close()
    fun isConnected(): Boolean
    fun connectionState(): ConnectionState
    fun currentUserId(): ULong?
    suspend fun shutdown(): Result<Unit>
    suspend fun nextEvent(timeoutMs: ULong = 1000u): Result<SdkEventEnvelope?>
    suspend fun recentEvents(limit: ULong = 100u): Result<List<SdkEventEnvelope>>
    fun observeEvents(timeoutMs: ULong = 1000u): Flow<SdkEventEnvelope>

    // ========== Auth ==========
    suspend fun register(username: String, password: String, deviceId: String): Result<AuthResult>
    suspend fun login(username: String, password: String, deviceId: String): Result<AuthResult>
    suspend fun authenticate(userId: ULong, token: String, deviceId: String): Result<Unit>
    suspend fun restoreLocalSession(): Result<Boolean>
    suspend fun logout(): Result<Unit>

    // ========== Messaging ==========
    fun generateLocalMessageId(): Result<ULong>
    suspend fun sendText(channelId: ULong, channelType: Int, text: String): Result<ULong>
    suspend fun sendText(channelId: ULong, channelType: Int, text: String, options: SendMessageOptions): Result<ULong>
    suspend fun sendTextWithLocalId(channelId: ULong, channelType: Int, text: String, localMessageId: ULong): Result<ULong>
    suspend fun sendMedia(channelId: ULong, filePath: String, options: SendMessageOptions?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun retryMessage(messageId: ULong): Result<Unit>
    suspend fun markAsRead(channelId: ULong, messageId: ULong): Result<Unit>
    suspend fun markFullyReadAt(channelId: ULong, messageId: ULong): Result<Unit>
    suspend fun revokeMessage(messageId: ULong): Result<Unit>
    suspend fun editMessage(messageId: ULong, newContent: String): Result<Unit>
    suspend fun addReaction(messageId: ULong, emoji: String): Result<Unit>
    suspend fun removeReaction(messageId: ULong, emoji: String): Result<Unit>
    suspend fun reactions(channelId: ULong, messageId: ULong): Result<List<ReactionChip>>
    suspend fun reactionsBatch(channelId: ULong, messageIds: List<ULong>): Result<Map<ULong, List<ReactionChip>>>
    suspend fun isEventReadBy(channelId: ULong, messageId: ULong, userId: ULong): Result<Boolean>
    suspend fun seenByForEvent(channelId: ULong, messageId: ULong, limit: UInt?): Result<List<SeenByEntry>>
    suspend fun searchMessages(query: String, channelId: String?): Result<List<MessageEntry>>

    // ========== Storage ==========
    suspend fun getMessages(channelId: ULong, limit: UInt, beforeSeq: ULong?): Result<List<MessageEntry>>
    suspend fun getMessagesByType(channelId: ULong, channelType: Int, limit: UInt, beforeSeq: ULong?): Result<List<MessageEntry>>
    suspend fun getMessageById(messageId: ULong): Result<MessageEntry?>
    suspend fun paginateBack(channelId: ULong, beforeSeq: ULong, limit: UInt): Result<List<MessageEntry>>
    suspend fun paginateForward(channelId: ULong, afterSeq: ULong, limit: UInt): Result<List<MessageEntry>>
    suspend fun listLocalAccounts(): Result<List<LocalAccountInfo>>
    suspend fun setCurrentUid(uid: String): Result<Unit>
    suspend fun getChannels(limit: UInt, offset: UInt): Result<List<ChannelListEntry>>
    suspend fun getFriends(limit: UInt?, offset: UInt?): Result<List<FriendEntry>>
    suspend fun getGroups(limit: UInt?, offset: UInt?): Result<List<GroupEntry>>
    suspend fun getGroupMembers(groupId: ULong, limit: UInt?, offset: UInt?): Result<List<GroupMemberEntry>>

    // ========== Sync ==========
    suspend fun runBootstrapSync(): Result<Unit>
    fun runBootstrapSyncInBackground()
    suspend fun isBootstrapCompleted(): Result<Boolean>
    suspend fun syncEntities(type: String, scope: String?): Result<UInt>
    suspend fun syncChannel(channelId: ULong, channelType: UByte): Result<SyncStateEntry>
    suspend fun syncAllChannels(): Result<List<SyncStateEntry>>
    suspend fun getChannelSyncState(channelId: ULong, channelType: UByte): Result<SyncStateEntry>
    suspend fun needsSync(channelId: ULong, channelType: UByte): Result<Boolean>
    suspend fun startSupervisedSync(observer: SyncObserver): Result<Unit>
    suspend fun stopSupervisedSync(): Result<Unit>

    // ========== Channels ==========
    suspend fun markChannelRead(channelId: ULong, channelType: Int): Result<Unit>
    suspend fun pinChannel(channelId: ULong, pin: Boolean): Result<Boolean>
    suspend fun hideChannel(channelId: ULong): Result<Boolean>
    suspend fun muteChannel(channelId: ULong, muted: Boolean): Result<Boolean>
    suspend fun channelUnreadStats(channelId: ULong): Result<UnreadStats>
    suspend fun ownLastRead(channelId: ULong): Result<LastReadPosition>
    suspend fun setChannelNotificationMode(channelId: ULong, mode: NotificationMode): Result<Unit>
    suspend fun getOrCreateDirectChannel(peerUserId: ULong): Result<GetOrCreateDirectChannelResult>

    // ========== Friends & Groups ==========
    suspend fun searchUsers(query: String): Result<List<UserEntry>>
    suspend fun listUsersByIds(userIds: List<ULong>): Result<List<UserEntry>>
    suspend fun sendFriendRequest(toUserId: ULong, remark: String?, searchSessionId: String?): Result<ULong>
    suspend fun acceptFriendRequest(fromUserId: ULong): Result<ULong>
    suspend fun rejectFriendRequest(fromUserId: ULong): Result<Boolean>
    suspend fun deleteFriend(userId: ULong): Result<Boolean>
    suspend fun listFriendPendingRequests(): Result<List<FriendPendingEntry>>
    suspend fun createGroup(name: String, memberIds: List<ULong>): Result<GroupCreateResult>
    suspend fun inviteToGroup(groupId: ULong, userIds: List<ULong>): Result<Boolean>
    suspend fun removeGroupMember(groupId: ULong, userId: ULong): Result<Boolean>
    suspend fun leaveGroup(groupId: ULong): Result<Boolean>
    suspend fun joinGroupByQrcode(qrcode: String): Result<GroupQrCodeJoinResult>

    // ========== Presence & Typing ==========
    suspend fun subscribePresence(userIds: List<ULong>): Result<List<PresenceEntry>>
    fun unsubscribePresence(userIds: List<ULong>)
    fun getPresence(userId: ULong): PresenceEntry?
    fun batchGetPresence(userIds: List<ULong>): List<PresenceEntry>
    suspend fun fetchPresence(userIds: List<ULong>): Result<List<PresenceEntry>>
    suspend fun sendTyping(channelId: ULong): Result<Unit>
    suspend fun stopTyping(channelId: ULong): Result<Unit>

    // ========== File ==========
    suspend fun sendAttachmentFromPath(channelId: ULong, path: String, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun sendAttachmentBytes(channelId: ULong, filename: String, mimeType: String, data: ByteArray, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun downloadAttachmentToCache(fileId: String, fileUrl: String, progress: ProgressObserver?): Result<String>
    suspend fun downloadAttachmentToPath(fileUrl: String, outputPath: String, progress: ProgressObserver?): Result<Unit>

    fun setVideoProcessHook(hook: VideoProcessHook?)
    fun removeVideoProcessHook()

    companion object {
        fun create(config: PrivchatConfig): Result<PrivchatClient>
    }
}
