package com.netonstream.privchat.sdk

import com.netonstream.privchat.sdk.dto.*
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
    fun enterBackground(): Result<Unit>
    fun enterForeground(): Result<Unit>

    // ========== Auth ==========
    suspend fun register(username: String, password: String, deviceId: String): Result<AuthResult>
    suspend fun login(username: String, password: String, deviceId: String): Result<AuthResult>
    suspend fun authenticate(userId: ULong, token: String, deviceId: String): Result<Unit>
    /**
     * 调 privchat-server `account/auth/refresh` RPC 续期 access token。
     * 业务层 catch 10002 错误时调用：拿到新 access token 后再调 [authenticate] 应用。
     * 业务层负责 refresh_token 的存储（从 [AuthResult.refreshToken] 捕获），
     * SDK 不负责持久化 refresh_token——这样 SDK 适配第一方 / 第三方业务两种 login 流程。
     * 详见 TOKEN_REFRESH_SPEC v1.0 §5。
     */
    suspend fun refreshAccessToken(refreshToken: String, deviceId: String): Result<RefreshAccessTokenResult>
    suspend fun updateProfile(displayName: String?, avatarUrl: String? = null, bio: String? = null): Result<Unit>
    suspend fun updateDevicePushState(deviceId: String, apnsArmed: Boolean, pushToken: String?): Result<Unit>
    suspend fun restoreLocalSession(): Result<Boolean>
    suspend fun logout(): Result<Unit>
    /**
     * 查询上一次 ForcedLogout 终局记录。成功 Connect 后会被清空。
     * 用途：冷启动时如果返回非 null，说明旧 token 已被服务端拒掉；UI 应直接跳登录页，
     * 不要继续按旧凭据触发自动重连。
     */
    suspend fun lastTerminalReason(): Result<TerminalReason?>

    // ========== Messaging ==========
    fun generateLocalMessageId(): Result<ULong>
    suspend fun sendText(channelId: ULong, channelType: Int, text: String): Result<ULong>
    suspend fun sendText(channelId: ULong, channelType: Int, text: String, options: SendMessageOptions): Result<ULong>
    suspend fun sendTextWithLocalId(channelId: ULong, channelType: Int, text: String, localMessageId: ULong): Result<ULong>
    suspend fun sendMedia(channelId: ULong, filePath: String, options: SendMessageOptions?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun retryMessage(messageId: ULong): Result<Unit>
    suspend fun markReadToPts(channelId: ULong, readPts: ULong): Result<ULong>
    suspend fun markAsRead(channelId: ULong, messageId: ULong): Result<Unit>
    suspend fun markFullyReadAt(channelId: ULong, messageId: ULong): Result<Unit>
    suspend fun revokeMessage(messageId: ULong): Result<Unit>
    /** 本地删除消息：删 DB + 清附件目录，不触达服务端。返回 true 表示实际删除，false 表示消息不存在。 */
    suspend fun deleteMessageLocal(messageId: ULong): Result<Boolean>
    /**
     * 转发消息到目标频道。SDK 内部克隆源消息字段 + 复制附件文件到新消息目录，
     * 然后走正常出站队列发送。调用方负责过滤不可转发的消息类型（如 VOICE）。
     * 返回新消息的本地 messageId。
     */
    suspend fun forwardMessage(srcMessageId: ULong, targetChannelId: ULong, targetChannelType: Int): Result<ULong>
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
    suspend fun getChannelById(channelId: ULong): Result<ChannelListEntry?>
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
    suspend fun getChannelCurrentPts(channelId: ULong, channelType: UByte): Result<ULong>
    suspend fun needsSync(channelId: ULong, channelType: UByte): Result<Boolean>
    suspend fun startSupervisedSync(observer: SyncObserver): Result<Unit>
    suspend fun stopSupervisedSync(): Result<Unit>

    // ========== Read/Delivered Status ==========
    /** 查询对端已读水位（冷启动用）。返回 null 表示无记录，不透传 0。 */
    suspend fun getPeerReadPts(channelId: ULong, channelType: Int): Result<ULong?>

    // ========== Channels ==========
    suspend fun markChannelRead(channelId: ULong, channelType: Int): Result<Unit>
    suspend fun pinChannel(channelId: ULong, pin: Boolean): Result<Boolean>
    suspend fun hideChannel(channelId: ULong): Result<Boolean>
    /** 本地隐藏 channel（不触达服务端）。收到新消息时会自动取消隐藏。 */
    suspend fun setChannelHiddenLocal(channelId: ULong, hidden: Boolean): Result<Unit>
    /** 本地删除 channel：隐藏 + 清空所有消息与附件。不触达服务端。返回 true 表示实际删除到行。 */
    suspend fun deleteChannelLocal(channelId: ULong): Result<Boolean>
    suspend fun muteChannel(channelId: ULong, muted: Boolean): Result<Boolean>
    suspend fun channelUnreadStats(channelId: ULong): Result<UnreadStats>
    suspend fun ownLastRead(channelId: ULong): Result<LastReadPosition>
    suspend fun setChannelNotificationMode(channelId: ULong, mode: NotificationMode): Result<Unit>
    suspend fun getOrCreateDirectChannel(peerUserId: ULong): Result<GetOrCreateDirectChannelResult>
    suspend fun dmPeerUserId(channelId: ULong): Result<ULong?>

    // ========== Friends & Groups ==========
    suspend fun searchUsers(query: String): Result<List<UserEntry>>
    suspend fun getUserProfileLocalFirst(userId: ULong): Result<SearchedUserDto>
    suspend fun listUsersByIds(userIds: List<ULong>): Result<List<UserEntry>>
    /** 关注 Bot：server 写 follow 表 + 通知 application 写 binding。详见 SERVICE_ACCOUNT_FOLLOW_SPEC §3.1。 */
    suspend fun followBot(botUserId: ULong): Result<BotFollowOutcome>
    /** 取消关注 Bot：server 切 status=0，**不**删 channel / 历史。详见 spec §3.2。 */
    suspend fun unfollowBot(botUserId: ULong): Result<BotUnfollowOutcome>
    /**
     * 发送好友申请。
     *
     * @param source 来源类型字符串：`search` / `group` / `card_share` / `friend` / `conversation` 之一。
     *               null 时服务端只接受 qrcode / phone 等替代路径；通常 UI 必须给出。
     * @param sourceId 与 [source] 对应的 ID 字符串：search→搜索会话 ID，conversation→channel_id，
     *                 group→群 ID，card_share→分享 ID，friend→好友 user_id。
     *
     * 服务端按 (source, sourceId) 做权限校验，详见 spec/05-feature/RELATION_BUILDING_SPEC。
     */
    suspend fun sendFriendRequest(
        toUserId: ULong,
        remark: String?,
        source: String?,
        sourceId: String?,
    ): Result<ULong>
    suspend fun acceptFriendRequest(fromUserId: ULong): Result<ULong>
    suspend fun rejectFriendRequest(fromUserId: ULong): Result<Boolean>
    /**
     * F-sync.2: 撤回我发出的、尚未被处理的好友申请。
     *
     * Server 把对应 friendships row 改成 status=4 (Recalled)，row 保留以参与
     * entity sync 多端分发；本地 friend 表更新走 sync_entities("friend")。
     * 重新申请：再次调 [sendFriendRequest] 即可（server ON CONFLICT 把
     * status 改回 0=pending）。
     */
    suspend fun recallFriendRequest(targetUserId: ULong): Result<Boolean>
    suspend fun deleteFriend(userId: ULong): Result<Boolean>
    suspend fun updateUserAlias(userId: ULong, alias: String?): Result<Unit>
    suspend fun listFriendPendingRequests(): Result<List<FriendPendingEntry>>

    /**
     * F-sync.3: 从本地 friendships 投影读好友申请（非 accepted 行）。
     *
     * 这是 [listFriendPendingRequests] 的本地化版本——不走 RPC，直接读 SDK 通过
     * `entity/sync_entities("friend")` 同步到本地的 friend 表。
     *
     * @param outgoing true=我发出的（Sent tab）；false=我收到的（Received tab）。
     * @param statuses 按 status 过滤；空列表 = 所有非-accepted 态（0/3/4/5）。
     */
    suspend fun listFriendRequests(
        outgoing: Boolean,
        statuses: List<Short> = emptyList(),
        limit: Long = 200,
        offset: Long = 0,
    ): Result<List<FriendRequestEntry>>
    suspend fun createGroup(name: String, memberIds: List<ULong>): Result<GroupCreateResult>
    suspend fun inviteToGroup(groupId: ULong, userIds: List<ULong>): Result<Boolean>
    suspend fun removeGroupMember(groupId: ULong, userId: ULong): Result<Boolean>
    suspend fun leaveGroup(groupId: ULong): Result<Boolean>
    suspend fun joinGroupByQrcode(qrcode: String): Result<GroupQrCodeJoinResult>

    // ========== Presence & Typing ==========
    fun getPresence(userId: ULong): PresenceEntry?
    fun batchGetPresence(userIds: List<ULong>): List<PresenceEntry>
    suspend fun fetchPresence(userIds: List<ULong>): Result<List<PresenceEntry>>
    suspend fun sendTyping(channelId: ULong): Result<Unit>
    suspend fun stopTyping(channelId: ULong): Result<Unit>

    // ========== Channel Event Subscription ==========
    suspend fun subscribeChannel(channelId: ULong, channelType: UByte, token: String? = null): Result<Unit>
    suspend fun unsubscribeChannel(channelId: ULong, channelType: UByte): Result<Unit>

    // ========== Channel Transfer (client→app RPC) ==========
    /**
     * Channel Transfer client→app RPC. Sends a wire `TransferRequest` (biz_type=19)
     * on the given channel and awaits the matching `TransferResponse`. `body` is
     * route-defined opaque bytes; `timeoutMs = 0` falls back to the SDK default.
     * See `02-server/CHANNEL_TRANSFER_SPEC.md` v2.0 and
     * `07-application/BOT_INTERACTION_SPEC.md` (e.g. route `bot/menu/get`).
     */
    suspend fun transfer(
        channelId: ULong,
        route: String,
        body: ByteArray,
        timeoutMs: ULong = 0u,
    ): Result<TransferReply>

    // ========== File ==========
    suspend fun sendAttachmentFromPath(channelId: ULong, path: String, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun sendAttachmentBytes(channelId: ULong, filename: String, mimeType: String, data: ByteArray, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>>

    // ========== Media Store ==========
    fun getAttachmentTargetDir(uid: ULong, messageId: Long, createdAtMs: Long): String
    fun resolveAttachmentPath(uid: ULong, messageId: Long, createdAtMs: Long, filename: String?): String?

    // ========== Voice ==========
    suspend fun sendVoiceFromPath(channelId: ULong, path: String, durationMs: Long, options: SendMessageOptions?, progress: ProgressObserver?): Result<Pair<ULong, AttachmentInfo>>
    suspend fun downloadAttachmentToCache(fileId: String, fileUrl: String, progress: ProgressObserver?): Result<String>
    suspend fun downloadAttachmentToPath(fileUrl: String, outputPath: String, progress: ProgressObserver?): Result<Unit>
    suspend fun updateMediaDownloaded(messageId: ULong, downloaded: Boolean): Result<Unit>

    // ========== Media Download (Telegram-style: stream + Range resume + pause/cancel) ==========
    /** Start (or no-op if already in-flight) a streaming download. Emits MediaDownloadStateChanged events. */
    suspend fun startMessageMediaDownload(messageId: ULong, downloadUrl: String, mime: String, filenameHint: String?, createdAtMs: Long): Result<Unit>
    suspend fun pauseMessageMediaDownload(messageId: ULong): Result<Unit>
    suspend fun resumeMessageMediaDownload(messageId: ULong): Result<Unit>
    /** Abort in-flight download. Keeps the `.part` file so a later start() can resume. */
    suspend fun cancelMessageMediaDownload(messageId: ULong): Result<Unit>
    suspend fun getMediaDownloadState(messageId: ULong): Result<MediaDownloadState>

    fun setVideoProcessHook(hook: VideoProcessHook?)
    fun removeVideoProcessHook()

    /** Plan 2: reply to [SdkEventPayload] of type `media_job_requested`. */
    fun submitMediaJobResult(jobId: String, result: MediaJobResult): Result<Unit>

    companion object {
        fun create(config: PrivchatConfig): Result<PrivchatClient>
    }
}
