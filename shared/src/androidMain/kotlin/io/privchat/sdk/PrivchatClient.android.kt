package io.privchat.sdk

import android.util.Log
import io.privchat.sdk.dto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.privchat.ffi.PrivchatConfigBuilder
import io.privchat.ffi.PrivchatException
import io.privchat.ffi.PrivchatSdk

private const val TAG_CHANNELS = "PrivchatGetChannels"

private inline fun <T> runCatchingSdk(block: () -> T): Result<T> = runCatching(block).fold(
    onSuccess = { Result.success(it) },
    onFailure = { Result.failure(if (it is PrivchatException) it.toSdkError() else it) }
)

actual fun parseServerUrl(url: String): ServerEndpoint? = runCatching {
    val builder = PrivchatConfigBuilder()
        .dataDir("/tmp")
        .connectionTimeout(30u)
        .heartbeatInterval(30u)
        .debugMode(false)
    val builderWithUrl = builder.serverUrl(url)  // serverUrl 返回新 builder，必须使用返回值
    val config = builderWithUrl.build()
    val e = config.serverConfig.endpoints.firstOrNull() ?: return@runCatching null
    ServerEndpoint(
        protocol = when (e.protocol) {
            io.privchat.ffi.TransportProtocol.QUIC -> TransportProtocol.Quic
            io.privchat.ffi.TransportProtocol.TCP -> TransportProtocol.Tcp
            io.privchat.ffi.TransportProtocol.WEB_SOCKET -> TransportProtocol.WebSocket
        },
        host = e.host,
        port = e.port.toInt(),
        path = e.path,
        useTls = e.useTls,
    )
}.getOrNull()

private fun PrivchatException.toSdkError(): SdkError = when (this) {
    is PrivchatException.Generic -> SdkError.Generic(msg)
    is PrivchatException.Database -> SdkError.Database(msg)
    is PrivchatException.Network -> SdkError.Network(msg, code)
    is PrivchatException.Authentication -> SdkError.Authentication(reason)
    is PrivchatException.InvalidParameter -> SdkError.InvalidParameter(field, msg)
    is PrivchatException.Timeout -> SdkError.Timeout(timeoutSecs)
    is PrivchatException.Disconnected -> SdkError.Disconnected
    is PrivchatException.NotInitialized -> SdkError.NotInitialized
}

private fun SendMessageOptions.toFfi() = io.privchat.ffi.SendMessageOptions(
    inReplyToMessageId = inReplyToMessageId,
    mentions = mentions,
    silent = silent,
    extraJson = extraJson
)

private fun io.privchat.ffi.MessageEntry.toCommon() = io.privchat.sdk.dto.MessageEntry(
    id = this.id,
    serverMessageId = this.serverMessageId,
    channelId = this.channelId,
    channelType = this.channelType,
    fromUid = this.fromUid,
    content = this.content,
    status = when (this.status) {
        io.privchat.ffi.MessageStatus.PENDING -> io.privchat.sdk.dto.MessageStatus.Pending
        io.privchat.ffi.MessageStatus.SENDING -> io.privchat.sdk.dto.MessageStatus.Sending
        io.privchat.ffi.MessageStatus.SENT -> io.privchat.sdk.dto.MessageStatus.Sent
        io.privchat.ffi.MessageStatus.FAILED -> io.privchat.sdk.dto.MessageStatus.Failed
        io.privchat.ffi.MessageStatus.READ -> io.privchat.sdk.dto.MessageStatus.Read
    },
    timestamp = this.timestamp
)

private fun io.privchat.ffi.ChannelListEntry.toCommon() = io.privchat.sdk.dto.ChannelListEntry(
    channelId = this.channelId,
    channelType = this.channelType,
    name = this.name,
    lastTs = this.lastTs,
    notifications = this.notifications,
    messages = this.messages,
    mentions = this.mentions,
    markedUnread = this.markedUnread,
    isFavourite = this.isFavourite,
    isLowPriority = this.isLowPriority,
    avatarUrl = this.avatarUrl,
    isDm = this.isDm,
    isEncrypted = this.isEncrypted,
    memberCount = this.memberCount,
    topic = this.topic,
    latestEvent = this.latestEvent?.let { ev -> io.privchat.sdk.dto.LatestChannelEvent(ev.eventType, ev.content, ev.timestamp) }
)

private fun io.privchat.ffi.FriendEntry.toCommon() = io.privchat.sdk.dto.FriendEntry(
    userId = this.userId,
    username = this.username,
    nickname = this.nickname,
    avatarUrl = this.avatarUrl,
    userType = this.userType,
    status = this.status,
    addedAt = this.addedAt,
    remark = this.remark
)

private fun io.privchat.ffi.GroupEntry.toCommon() = io.privchat.sdk.dto.GroupEntry(
    groupId = this.groupId,
    name = this.name,
    avatar = this.avatar,
    ownerId = this.ownerId,
    isDismissed = this.isDismissed,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

private fun io.privchat.ffi.GroupMemberEntry.toCommon() = io.privchat.sdk.dto.GroupMemberEntry(
    userId = this.userId,
    channelId = this.channelId,
    channelType = this.channelType,
    name = this.name,
    remark = this.remark,
    avatar = this.avatar,
    role = this.role,
    status = this.status,
    inviteUserId = this.inviteUserId
)

private fun io.privchat.ffi.UserEntry.toCommon() = io.privchat.sdk.dto.UserEntry(
    userId = this.userId,
    username = this.username,
    nickname = this.nickname,
    avatarUrl = this.avatarUrl,
    userType = this.userType,
    isFriend = this.isFriend,
    canSendMessage = this.canSendMessage,
    searchSessionId = this.searchSessionId,
    isOnline = this.isOnline
)

private fun io.privchat.ffi.FriendPendingEntry.toCommon() = io.privchat.sdk.dto.FriendPendingEntry(
    fromUserId = this.fromUserId,
    message = this.message,
    createdAt = this.createdAt
)

private fun io.privchat.ffi.SyncStateEntry.toCommon() = io.privchat.sdk.dto.SyncStateEntry(
    channelId = this.channelId,
    channelType = this.channelType,
    localPts = this.localPts,
    serverPts = this.serverPts,
    needsSync = this.needsSync,
    lastSyncAt = this.lastSyncAt
)

private fun io.privchat.ffi.UnreadStats.toCommon() = io.privchat.sdk.dto.UnreadStats(
    messages = this.messages,
    notifications = this.notifications,
    mentions = this.mentions
)

private fun io.privchat.ffi.LastReadPosition.toCommon() = io.privchat.sdk.dto.LastReadPosition(
    serverMessageId = this.serverMessageId,
    timestamp = this.timestamp
)

private fun io.privchat.ffi.PresenceEntry.toCommon() = io.privchat.sdk.dto.PresenceEntry(
    userId = this.userId,
    isOnline = this.isOnline,
    lastSeen = this.lastSeen,
    deviceType = this.deviceType
)

private fun io.privchat.ffi.ReactionChip.toCommon() = io.privchat.sdk.dto.ReactionChip(
    emoji = this.emoji,
    userIds = this.userIds,
    count = this.count
)

private fun io.privchat.ffi.SeenByEntry.toCommon() = io.privchat.sdk.dto.SeenByEntry(
    userId = this.userId,
    readAt = this.readAt
)

private fun io.privchat.ffi.AttachmentInfo.toCommon() = io.privchat.sdk.dto.AttachmentInfo(
    url = this.url,
    mimeType = this.mimeType,
    size = this.size,
    thumbnailUrl = this.thumbnailUrl,
    filename = this.filename,
    fileId = this.fileId,
    width = this.width,
    height = this.height,
    duration = this.duration
)

private fun io.privchat.sdk.dto.NotificationMode.toFfiMode() = when (this) {
    io.privchat.sdk.dto.NotificationMode.All -> io.privchat.ffi.NotificationMode.ALL
    io.privchat.sdk.dto.NotificationMode.Mentions -> io.privchat.ffi.NotificationMode.MENTIONS
    io.privchat.sdk.dto.NotificationMode.None -> io.privchat.ffi.NotificationMode.NONE
}

actual class PrivchatClient private actual constructor() {
    private val ffi: io.privchat.ffi.PrivchatSdk

    init {
        ffi = Companion.pendingFfi ?: throw IllegalStateException("Use PrivchatClient.create()")
        Companion.pendingFfi = null
    }
    actual suspend fun connect(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.connect() } }
    actual suspend fun disconnect(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.disconnect() } }
    actual fun isConnected(): Boolean = ffi.isConnected()
    actual fun connectionState(): ConnectionState = ffi.connectionState().toCommon()
    actual fun currentUserId(): ULong? = ffi.currentUserId()
    actual suspend fun shutdown(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.shutdown() } }

    actual suspend fun register(username: String, password: String, deviceId: String): Result<AuthResult> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.register(username, password, deviceId).toCommon() } }
    actual suspend fun login(username: String, password: String, deviceId: String): Result<AuthResult> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.login(username, password, deviceId).toCommon() } }
    actual suspend fun authenticate(userId: ULong, token: String, deviceId: String): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.authenticate(userId, token, deviceId) } }
    actual suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.logout() } }

    actual suspend fun sendText(channelId: ULong, channelType: Int, text: String): Result<ULong> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.sendMessage(text, channelId, channelType) } }
    actual suspend fun sendText(channelId: ULong, channelType: Int, text: String, options: SendMessageOptions): Result<ULong> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.sendMessageWithOptions(text, channelId, options.toFfi()) } }
    actual suspend fun sendMedia(channelId: ULong, filePath: String, options: SendMessageOptions?): Result<Pair<ULong, AttachmentInfo>> =
        withContext(Dispatchers.IO) {
            val opts = options?.toFfi() ?: io.privchat.ffi.SendMessageOptions(null, emptyList(), false, null)
            runCatchingSdk {
                val r = ffi.sendAttachmentFromPath(channelId, filePath, opts, null)
                r.id to r.attachment.toCommon()
            }
        }
    actual suspend fun retryMessage(messageId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.retryMessage(messageId) } }
    actual suspend fun markAsRead(channelId: ULong, messageId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.markAsRead(channelId, messageId) } }
    actual suspend fun markFullyReadAt(channelId: ULong, messageId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.markFullyReadAt(channelId, messageId) } }
    actual suspend fun revokeMessage(messageId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.revokeMessage(messageId) } }
    actual suspend fun editMessage(messageId: ULong, newContent: String): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.editMessage(messageId, newContent) } }
    actual suspend fun addReaction(messageId: ULong, emoji: String): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.addReaction(messageId, emoji) } }
    actual suspend fun removeReaction(messageId: ULong, emoji: String): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.removeReaction(messageId, emoji) } }
    actual suspend fun reactions(channelId: ULong, messageId: ULong): Result<List<ReactionChip>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.reactions(channelId, messageId).map { it.toCommon() } } }
    actual suspend fun reactionsBatch(channelId: ULong, messageIds: List<ULong>): Result<Map<ULong, List<ReactionChip>>> =
        withContext(Dispatchers.IO) {
            runCatchingSdk { ffi.reactionsBatch(channelId, messageIds).mapValues { (_, chips) -> chips.map { it.toCommon() } } }
        }
    actual suspend fun isEventReadBy(channelId: ULong, messageId: ULong, userId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.isEventReadBy(channelId, messageId, userId) } }
    actual suspend fun seenByForEvent(channelId: ULong, messageId: ULong, limit: UInt?): Result<List<SeenByEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.seenByForEvent(channelId, messageId, limit).map { it.toCommon() } } }
    actual suspend fun searchMessages(query: String, channelId: String?): Result<List<MessageEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.searchMessages(query, channelId).map { it.toCommon() } } }

    actual suspend fun getMessages(channelId: ULong, limit: UInt, beforeSeq: ULong?): Result<List<MessageEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getMessageHistory(channelId, limit, beforeSeq).map { it.toCommon() } } }
    actual suspend fun getMessageById(messageId: ULong): Result<MessageEntry?> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getMessageById(messageId)?.toCommon() } }
    actual suspend fun paginateBack(channelId: ULong, beforeSeq: ULong, limit: UInt): Result<List<MessageEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.paginateBack(channelId, beforeSeq, limit).map { it.toCommon() } } }
    actual suspend fun paginateForward(channelId: ULong, afterSeq: ULong, limit: UInt): Result<List<MessageEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.paginateForward(channelId, afterSeq, limit).map { it.toCommon() } } }
    actual suspend fun getChannels(limit: UInt, offset: UInt): Result<List<ChannelListEntry>> =
        withContext(Dispatchers.IO) {
            runCatchingSdk {
                val raw = ffi.getChannels(limit, offset)
                Log.d(TAG_CHANNELS, "[getChannels] SDK 返回会话数: ${raw.size}, limit=$limit, offset=$offset")
                raw.forEachIndexed { i, ffiEntry ->
                    Log.d(TAG_CHANNELS, "[getChannels] [$i] channelId=${ffiEntry.channelId}, channelType=${ffiEntry.channelType}, name=${ffiEntry.name}, messages(unread)=${ffiEntry.messages}")
                }
                raw.map { it.toCommon() }
            }
        }
    actual suspend fun getFriends(limit: UInt?, offset: UInt?): Result<List<FriendEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getFriends(limit, offset).map { it.toCommon() } } }
    actual suspend fun getGroups(limit: UInt?, offset: UInt?): Result<List<GroupEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getGroups(limit, offset).map { it.toCommon() } } }
    actual suspend fun getGroupMembers(groupId: ULong, limit: UInt?, offset: UInt?): Result<List<GroupMemberEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getGroupMembers(groupId, limit, offset).map { it.toCommon() } } }

    actual suspend fun runBootstrapSync(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.runBootstrapSync() } }
    actual fun runBootstrapSyncInBackground() = ffi.runBootstrapSyncInBackground()
    actual suspend fun isBootstrapCompleted(): Result<Boolean> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.isBootstrapCompleted() } }
    actual suspend fun syncEntities(type: String, scope: String?): Result<UInt> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.syncEntities(type, scope) } }
    actual suspend fun syncChannel(channelId: ULong, channelType: UByte): Result<SyncStateEntry> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.syncChannel(channelId, channelType).toCommon() } }
    actual suspend fun syncAllChannels(): Result<List<SyncStateEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.syncAllChannels().map { it.toCommon() } } }
    actual suspend fun getChannelSyncState(channelId: ULong, channelType: UByte): Result<SyncStateEntry> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getChannelSyncState(channelId, channelType).toCommon() } }
    actual suspend fun needsSync(channelId: ULong, channelType: UByte): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.needsSync(channelId, channelType) } }
    actual suspend fun startSupervisedSync(observer: SyncObserver): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatchingSdk {
                val ffiObserver = object : io.privchat.ffi.SyncObserver {
                    override fun onState(status: io.privchat.ffi.SyncStatus) {
                        observer.onState(SyncStatus(
                            phase = when (status.phase) {
                                io.privchat.ffi.SyncPhase.IDLE -> SyncPhase.Idle
                                io.privchat.ffi.SyncPhase.RUNNING -> SyncPhase.Running
                                io.privchat.ffi.SyncPhase.BACKING_OFF -> SyncPhase.BackingOff
                                io.privchat.ffi.SyncPhase.ERROR -> SyncPhase.Error
                            },
                            message = status.message
                        ))
                    }
                }
                ffi.startSupervisedSync(ffiObserver)
            }
        }
    actual suspend fun stopSupervisedSync(): Result<Unit> = withContext(Dispatchers.IO) { runCatchingSdk { ffi.stopSupervisedSync() } }

    actual suspend fun markChannelRead(channelId: ULong, channelType: Int): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.markChannelRead(channelId, channelType) } }
    actual suspend fun pinChannel(channelId: ULong, pin: Boolean): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.pinChannel(channelId, pin) } }
    actual suspend fun hideChannel(channelId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.hideChannel(channelId) } }
    actual suspend fun muteChannel(channelId: ULong, muted: Boolean): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.muteChannel(channelId, muted) } }
    actual suspend fun channelUnreadStats(channelId: ULong): Result<UnreadStats> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.channelUnreadStats(channelId).toCommon() } }
    actual suspend fun ownLastRead(channelId: ULong): Result<LastReadPosition> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.ownLastRead(channelId).toCommon() } }
    actual suspend fun setChannelNotificationMode(channelId: ULong, mode: NotificationMode): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.setChannelNotificationMode(channelId, mode.toFfiMode()) } }
    actual suspend fun getOrCreateDirectChannel(peerUserId: ULong): Result<GetOrCreateDirectChannelResult> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.getOrCreateDirectChannel(peerUserId, null, null).toCommon() } }

    actual suspend fun searchUsers(query: String): Result<List<UserEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.searchUsers(query).map { it.toCommon() } } }
    actual suspend fun sendFriendRequest(toUserId: ULong, remark: String?, searchSessionId: String?): Result<ULong> =
        withContext(Dispatchers.IO) {
            runCatchingSdk {
                val json = ffi.sendFriendRequest(toUserId, remark, searchSessionId)
                org.json.JSONObject(json).optLong("request_id", 0L).toULong()
            }
        }
    actual suspend fun acceptFriendRequest(fromUserId: ULong): Result<ULong> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.acceptFriendRequest(fromUserId) } }
    actual suspend fun rejectFriendRequest(fromUserId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.rejectFriendRequest(fromUserId) } }
    actual suspend fun deleteFriend(userId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.deleteFriend(userId) } }
    actual suspend fun listFriendPendingRequests(): Result<List<FriendPendingEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.listFriendPendingRequests().map { it.toCommon() } } }
    actual suspend fun createGroup(name: String, memberIds: List<ULong>): Result<GroupCreateResult> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.createGroup(name, memberIds).toCommon() } }
    actual suspend fun inviteToGroup(groupId: ULong, userIds: List<ULong>): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.inviteToGroup(groupId, userIds) } }
    actual suspend fun removeGroupMember(groupId: ULong, userId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.removeGroupMember(groupId, userId) } }
    actual suspend fun leaveGroup(groupId: ULong): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.leaveGroup(groupId) } }
    actual suspend fun joinGroupByQrcode(qrcode: String): Result<GroupQrCodeJoinResult> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.joinGroupByQrcode(qrcode, null, null).toCommon() } }

    actual suspend fun subscribePresence(userIds: List<ULong>): Result<List<PresenceEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.subscribePresence(userIds).map { it.toCommon() } } }
    actual fun unsubscribePresence(userIds: List<ULong>) { try { ffi.unsubscribePresence(userIds) } catch (_: Exception) {} }
    actual fun getPresence(userId: ULong): PresenceEntry? = ffi.getPresence(userId)?.toCommon()
    actual fun batchGetPresence(userIds: List<ULong>): List<PresenceEntry> = ffi.batchGetPresence(userIds).map { it.toCommon() }
    actual suspend fun fetchPresence(userIds: List<ULong>): Result<List<PresenceEntry>> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.fetchPresence(userIds).map { it.toCommon() } } }
    actual suspend fun sendTyping(channelId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.sendTyping(channelId) } }
    actual suspend fun stopTyping(channelId: ULong): Result<Unit> =
        withContext(Dispatchers.IO) { runCatchingSdk { ffi.stopTyping(channelId) } }

    actual suspend fun sendAttachmentFromPath(channelId: ULong, path: String, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>> =
        withContext(Dispatchers.IO) {
            val opts = options?.toFfi() ?: io.privchat.ffi.SendMessageOptions(null, emptyList(), false, null)
            val prog = progress?.let { p ->
                object : io.privchat.ffi.ProgressObserver {
                    override fun onProgress(current: ULong, total: ULong?) { p.onProgress(current, total) }
                }
            }
            runCatchingSdk {
                val r = ffi.sendAttachmentFromPath(channelId, path, opts, prog)
                r.id to r.attachment.toCommon()
            }
        }
    actual suspend fun sendAttachmentBytes(channelId: ULong, filename: String, mimeType: String, data: ByteArray, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>> =
        withContext(Dispatchers.IO) {
            val opts = options?.toFfi() ?: io.privchat.ffi.SendMessageOptions(null, emptyList(), false, null)
            val prog = progress?.let { p ->
                object : io.privchat.ffi.ProgressObserver {
                    override fun onProgress(current: ULong, total: ULong?) { p.onProgress(current, total) }
                }
            }
            runCatchingSdk {
                val r = ffi.sendAttachmentBytes(channelId, filename, mimeType, data, opts, prog)
                Pair<ULong, io.privchat.sdk.dto.AttachmentInfo>(r.id, r.attachment.toCommon())
            }
        }
    actual suspend fun downloadAttachmentToCache(fileId: String, fileUrl: String, progress: ProgressObserver?): Result<String> =
        withContext(Dispatchers.IO) {
            val prog = progress?.let { p ->
                object : io.privchat.ffi.ProgressObserver {
                    override fun onProgress(current: ULong, total: ULong?) { p.onProgress(current, total) }
                }
            }
            runCatchingSdk { ffi.downloadAttachmentToCache(fileId, fileUrl, prog) }
        }
    actual suspend fun downloadAttachmentToPath(fileUrl: String, outputPath: String, progress: ProgressObserver?): Result<Unit> =
        withContext(Dispatchers.IO) {
            val prog = progress?.let { p ->
                object : io.privchat.ffi.ProgressObserver {
                    override fun onProgress(current: ULong, total: ULong?) { p.onProgress(current, total) }
                }
            }
            runCatchingSdk { ffi.downloadAttachmentToPath(fileUrl, outputPath, prog) }
        }

    actual fun setVideoProcessHook(hook: io.privchat.sdk.dto.VideoProcessHook?) {
        val ffiHook: io.privchat.ffi.VideoProcessHook? = if (hook != null) {
            val h = hook
            object : io.privchat.ffi.VideoProcessHook {
                override fun process(op: io.privchat.ffi.MediaProcessOp, sourcePath: String, metaPath: String, outputPath: String): Boolean {
                    val opDto = when (op) {
                        io.privchat.ffi.MediaProcessOp.THUMBNAIL -> io.privchat.sdk.dto.MediaProcessOp.Thumbnail
                        io.privchat.ffi.MediaProcessOp.COMPRESS -> io.privchat.sdk.dto.MediaProcessOp.Compress
                    }
                    return h.process(opDto, sourcePath, metaPath, outputPath).getOrElse { e: Throwable ->
                        throw io.privchat.ffi.PrivchatException.Generic(e.toString())
                    }
                }
            }
        } else null
        ffi.setVideoProcessHook(ffiHook)
    }

    actual fun removeVideoProcessHook() = ffi.removeVideoProcessHook()

    actual companion object {
        @Volatile
        internal var pendingFfi: io.privchat.ffi.PrivchatSdk? = null

        actual fun create(config: PrivchatConfig): Result<PrivchatClient> = runCatchingSdk {
            var builder = PrivchatConfigBuilder()
                .dataDir(config.dataDir)
                .connectionTimeout(config.connectionTimeout)
                .heartbeatInterval(config.heartbeatInterval)
                .debugMode(config.debugMode)
            config.assetsDir?.let { builder = builder.assetsDir(it) }
            config.serverEndpoints.forEach { ep ->
                builder = builder.serverEndpoint(
                    io.privchat.ffi.ServerEndpoint(
                        protocol = ep.protocol.toFfi(),
                        host = ep.host,
                        port = ep.port.toUShort(),
                        path = ep.path,
                        useTls = ep.useTls,
                    )
                )
            }
            config.fileApiBaseUrl?.let { builder = builder.fileApiBaseUrl(it) }
            val ffiConfig = builder.build()
            pendingFfi = PrivchatSdk(ffiConfig)
            try {
                PrivchatClient()
            } finally {
                pendingFfi = null
            }
        }
    }
}

private fun TransportProtocol.toFfi() = when (this) {
    TransportProtocol.Quic -> io.privchat.ffi.TransportProtocol.QUIC
    TransportProtocol.Tcp -> io.privchat.ffi.TransportProtocol.TCP
    TransportProtocol.WebSocket -> io.privchat.ffi.TransportProtocol.WEB_SOCKET
}

private fun io.privchat.ffi.ConnectionState.toCommon() = when (this) {
    io.privchat.ffi.ConnectionState.DISCONNECTED -> ConnectionState.Disconnected
    io.privchat.ffi.ConnectionState.CONNECTING -> ConnectionState.Connecting
    io.privchat.ffi.ConnectionState.CONNECTED -> ConnectionState.Connected
    io.privchat.ffi.ConnectionState.RECONNECTING -> ConnectionState.Reconnecting
    io.privchat.ffi.ConnectionState.FAILED -> ConnectionState.Failed
}

private fun io.privchat.ffi.AuthResult.toCommon() = io.privchat.sdk.dto.AuthResult(userId = this.userId, token = this.token)

private fun io.privchat.ffi.GroupCreateResult.toCommon() = io.privchat.sdk.dto.GroupCreateResult(
    groupId = this.groupId,
    name = this.name,
    description = this.description,
    memberCount = this.memberCount,
    createdAt = this.createdAt,
    creatorId = this.creatorId
)

private fun io.privchat.ffi.GroupQrCodeJoinResult.toCommon() = io.privchat.sdk.dto.GroupQrCodeJoinResult(
    status = status,
    groupId = groupId,
    requestId = requestId,
    message = message,
    expiresAt = expiresAt,
    userId = userId,
    joinedAt = joinedAt
)

private fun io.privchat.ffi.GetOrCreateDirectChannelResult.toCommon() = io.privchat.sdk.dto.GetOrCreateDirectChannelResult(
    channelId = channelId,
    created = created
)
