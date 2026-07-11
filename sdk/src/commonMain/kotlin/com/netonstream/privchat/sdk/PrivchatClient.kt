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
    /**
     * AVATAR_CACHE_SPEC §8: 头像上传前客户端预处理（Rust image 管道，与消息缩略图同库）。
     * decode（白名单 jpeg/png/webp，gif/损坏格式直接失败，不消耗上传流量）→
     * 中心裁剪正方形 → 边长 >480 缩放到 480x480（≤480 不放大）→ 编码 PNG 写临时文件。
     * 返回处理后文件路径；App 选图后先过它再走上传管道。
     */
    suspend fun prepareAvatarImage(path: String): Result<String>
    /**
     * 显式头像 re-cache（CLIENT_GLOBAL_STATE §4.3 P2）：把当前登录用户新头像从 [avatarUrl]
     * 下载到本地并强制落库（avatar_local_path 是展示主字段，avatarUrl 只是下载源）。
     * 用于自己上传头像成功后立即刷新本地缓存。下载失败返回 failure，不污染旧缓存。
     */
    suspend fun recacheSelfAvatar(avatarUrl: String): Result<com.netonstream.privchat.sdk.dto.AvatarCacheResult>
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
    suspend fun sendLink(channelId: ULong, channelType: Int, payload: LinkMessagePayload, options: SendMessageOptions = SendMessageOptions()): Result<ULong>
    suspend fun sendLocation(channelId: ULong, channelType: Int, payload: LocationMessagePayload, options: SendMessageOptions = SendMessageOptions()): Result<ULong>
    suspend fun sendContactCard(channelId: ULong, channelType: Int, payload: ContactCardMessagePayload, options: SendMessageOptions = SendMessageOptions()): Result<ULong>
    /**
     * 发红包消息（PrivChat 标准 IM 能力，RP-ferry）。content 只带引用+展示快照；
     * 资金动作须先走 platform `/app/red-packet/send`（money-first），SDK 不碰资金。
     */
    suspend fun sendRedPacket(channelId: ULong, channelType: Int, content: RedPacketContent, options: SendMessageOptions = SendMessageOptions()): Result<ULong>
    /** 发转账消息（标准 IM 能力）。资金动作须先走 platform `/app/money-transfer/send`。 */
    suspend fun sendMoneyTransfer(channelId: ULong, channelType: Int, content: MoneyTransferContent, options: SendMessageOptions = SendMessageOptions()): Result<ULong>
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

    /**
     * 云端历史搜索（服务端 message/history/search，MESSAGE_HISTORY spec §4）。
     * channelId 非空 = 只搜该会话；空 = 全局（我参与的全部会话）。
     * 命中为 snippet 投影，不落本地库；点击命中走 [getMessagesAround]。
     * 服务端限频 300ms/user——调用方必须 debounce 300–500ms 并忽略过期结果。
     */
    suspend fun searchMessageHistory(
        query: String,
        channelId: ULong? = null,
        cursor: String? = null,
        limit: UInt? = null,
    ): Result<SearchHistoryPage>

    /**
     * jump-to-message 上下文（服务端 message/history/around，spec §5）。
     * SDK 内部已把 before/anchor/after 完整消息回填本地库；UI 应从本地库
     * 渲染并定位/高亮 anchor。anchor 不可见（不存在/撤回/删除/无权限）时
     * 返回 not_found 错误。
     */
    suspend fun getMessagesAround(
        channelId: ULong,
        channelType: Int,
        messageId: ULong,
        beforeLimit: UInt? = null,
        afterLimit: UInt? = null,
    ): Result<MessagesAroundPage>

    /**
     * 以 anchor 为轴按显示排序读**本地**上下文窗口（spec §5 渲染原语）。
     * 典型链路：[getMessagesAround]（服务端回填）→ 本方法（本地重查）→
     * UI 渲染 + 定位/高亮 anchor。anchor 本地不存在返回空列表。
     */
    suspend fun getLocalMessagesAround(
        channelId: ULong,
        channelType: Int,
        anchorMessageId: ULong,
        beforeLimit: UInt = 25u,
        afterLimit: UInt = 25u,
    ): Result<List<MessageEntry>>

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

    /**
     * 主动从 server 增量同步群成员到本地（group/member/list RPC → upsert 本地
     * group_member 表，含 server 返回的 nickname）。
     *
     * 用途：[getGroupMembers] 只读本地，新群 / 久未刷新的群本地缺成员 nickname
     * 时会 fallback 成 userId。进群成员页 / 群设置页时先调本方法刷一次，再
     * [getGroupMembers] 读本地即拿到正确昵称。按需触发，不在启动时全量刷。
     */
    suspend fun syncGroupMembers(groupId: ULong): Result<Unit>

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
    /** 拉黑：加入黑名单。服务端据此停止下发对方消息。 */
    suspend fun addToBlacklist(userId: ULong): Result<Boolean>
    /** 解除拉黑：移出黑名单。 */
    suspend fun removeFromBlacklist(userId: ULong): Result<Boolean>
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

    // ========== Group settings / mute / pin (P0-4 / P0-5 / P1) ==========
    //
    // 群设置（allow_search / join_policy / member_can_invite / allow_member_add_friend /
    // all_muted 等）以服务端 DB 为真源；置顶/禁言权限由服务端校验（群主/管理员）。

    /** 读群设置（任意群成员可调，服务端鉴权）。 */
    suspend fun groupGetSettings(groupId: ULong): Result<GroupSettingsView>

    /** 更新群设置（仅群主，服务端鉴权）。仅设置了的可选字段会被更新。 */
    suspend fun groupUpdateSettings(input: GroupSettingsUpdateInput): Result<Boolean>

    /** 单人禁言（群主/管理员）。durationSeconds=null/0 表示永久，>0 表示秒数。返回禁言截止毫秒。 */
    suspend fun groupMuteMember(
        groupId: ULong,
        userId: ULong,
        durationSeconds: ULong?,
    ): Result<ULong>

    /** 解除单人禁言（群主/管理员）。 */
    suspend fun groupUnmuteMember(groupId: ULong, userId: ULong): Result<Boolean>

    /** 全员禁言开关（群主/管理员）。 */
    suspend fun groupMuteAll(groupId: ULong, enabled: Boolean): Result<GroupMuteAllView>

    /** 群消息置顶 / 取消置顶（群主/管理员；pinned=false 取消）。channelId 为消息所在频道。 */
    suspend fun groupPinMessage(
        groupId: ULong,
        channelId: ULong,
        messageId: ULong,
        pinned: Boolean,
    ): Result<GroupPinMessageView>

    /** 群置顶消息列表（群成员可读，按置顶时间倒序）。 */
    suspend fun groupPinnedMessages(groupId: ULong): Result<List<GroupPinnedMessageView>>

    // ========== QR Code v1.4 (QR_CODE_SPEC v1.4) ==========
    //
    // 个人名片 / 群二维码：qr_key 永久挂在主表上，UI 通过 get 读、refresh 旋转、
    // resolve 把对方 qr_key 翻成最小用户卡片，join 用群 qr_key 申请加群。
    // URL 形态 `https://<host>/privchat:protocol/<entity>/<action>/<qr_key>`
    // 由 server 一次拼好返回；SDK 不在 client 侧拼 URL。

    /** 读自己的永久 qr_key + 已拼好的 scan URL。 */
    suspend fun userQrcodeGet(): Result<UserQrCodeGetView>

    /** 旋转自己的 qr_key（应对骚扰）。旧 qr_key 立即作废。 */
    suspend fun userQrcodeRefresh(): Result<UserQrCodeRefreshView>

    /** 用对端的 qr_key 拉最小用户卡片（不返回 qr_key 本身）。 */
    suspend fun userQrcodeResolve(qrKey: String): Result<UserQrCodeResolveView>

    /** 读群当前二维码 + URL。任意 group member 可调（server 鉴权）。 */
    suspend fun groupQrcodeGet(groupId: ULong): Result<GroupQrCodeGetView>

    /** 旋转群二维码。Owner/Admin only（server 鉴权）。 */
    suspend fun groupQrcodeRefresh(groupId: ULong): Result<GroupQrCodeRefreshView>

    /** 用群 qr_key 申请加群。response.status = "joined" / "pending"
     *  （按群 `join_need_approval` 决定）。`message` 可附申请理由。 */
    suspend fun groupQrcodeJoin(qrKey: String, message: String? = null): Result<GroupQrCodeJoinResult>

    /**
     * Local QR matrix encoder. Pure CPU; **does not touch the network or
     * the SDK client state**, so it works offline (e.g. rendering "我的二维码"
     * the user已经持有的 URL时 — server already returned the URL on the last
     * online call, we just need to draw it).
     *
     * Returns a `size×size` cell matrix (`0=light, 1=dark`) — quiet zone
     * NOT included; UI layer should add 4-module padding around it.
     *
     * Error-correction level fixed at M (~15%) — sensible default for
     * permanent name-card / group URLs that get printed / re-shared.
     */
    suspend fun qrEncodeMatrix(text: String): Result<QrMatrixView>

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
    /**
     * Start a streaming download for an attachment-encrypted (v1) message by `fileId`.
     * The core SDK resolves the signed URL + cek via `file/get_url` and decrypts the blob
     * on completion. Prefer this for any message carrying a `file_id`; the URL form above
     * is retained only for legacy plaintext attachments.
     */
    suspend fun startMessageMediaDownloadByFileId(messageId: ULong, fileId: ULong, mime: String, filenameHint: String?, createdAtMs: Long): Result<Unit>
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
