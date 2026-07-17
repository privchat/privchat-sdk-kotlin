

@file:Suppress(
    "NAME_SHADOWING",
    "INCOMPATIBLE_MATCHING",
    "RemoveRedundantBackticks",
    "KotlinRedundantDiagnosticSuppress",
    "UnusedImport",
    "unused",
    "RemoveRedundantQualifierName",
    "UnnecessaryOptInAnnotation"
)
@file:OptIn(ExperimentalStdlibApi::class, kotlin.time.ExperimentalTime::class)

package uniffi.privchat_sdk_ffi

// Common helper code.
//
// Ideally this would live in a separate .kt file where it can be unittested etc
// in isolation, and perhaps even published as a re-useable package.
//
// However, it's important that the details of how this helper code works (e.g. the
// way that different builtin types are passed across the FFI) exactly match what's
// expected by the Rust code on the other side of the interface. In practice right
// now that means coming from the exact some version of `uniffi` that was used to
// compile the Rust component. The easiest way to ensure this is to bundle the Kotlin
// helpers directly inline like we're doing here.

import uniffi.runtime.*

import kotlin.jvm.JvmField


typealias InternalException = uniffi.runtime.InternalException



// Public interface members begin here.


// Interface implemented by anything that can contain an object reference.
//
// Such types expose a `destroy()` method that must be called to cleanly
// dispose of the contained objects. Failure to call this method may result
// in memory leaks.
//
// The easiest way to ensure this method is called is to use the `.use`
// helper method to execute a block and destroy the object at the end.
interface Disposable : AutoCloseable {
    fun destroy()
    override fun close() = destroy()
    companion object {
        fun destroy(vararg args: Any?) {
            args.filterIsInstance<Disposable>()
                .forEach(Disposable::destroy)
        }
    }
}

inline fun <T : Disposable?, R> T.use(block: (T) -> R) =
    try {
        block(this)
    } finally {
        try {
            // N.B. our implementation is on the nullable type `Disposable?`.
            this?.destroy()
        } catch (e: Throwable) {
            // swallow
        }
    }

/** Used to instantiate an interface without an actual pointer, for fakes in tests, mostly. */
object NoPointer

























interface PrivchatClientInterface {
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `acceptFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `accountUserDetailRemote`(`userId`: kotlin.ULong): AccountUserDetailView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `accountUserShareCardRemote`(`userId`: kotlin.ULong): AccountUserShareCardView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `accountUserUpdateRemote`(`payload`: AccountUserUpdateInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ackOutboundFiles`(`queueIndex`: kotlin.ULong, `messageIds`: List<kotlin.ULong>): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ackOutboundMessages`(`messageIds`: List<kotlin.ULong>): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `addChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUids`: List<kotlin.ULong>)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `addReaction`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `addReactionBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class)fun `addServer`(`endpoint`: ServerEndpoint)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `addToBlacklist`(`blockedUserId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `assetsDir`(): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `authLogoutRemote`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `authenticate`(`userId`: kotlin.ULong, `token`: kotlin.String, `deviceId`: kotlin.String)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `batchGetPresence`(`userIds`: List<kotlin.ULong>): List<PresenceStatus>
    fun `build`(): kotlin.String
    fun `builder`(): kotlin.String
    suspend fun `cancelMessageMediaDownload`(`messageId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelBroadcastCreateRemote`(`payload`: ChannelBroadcastCreateInput): ChannelBroadcastCreateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelBroadcastListRemote`(`payload`: ChannelBroadcastListInput): ChannelBroadcastListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelBroadcastSubscribeRemote`(`payload`: ChannelBroadcastSubscribeInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelContentListRemote`(`payload`: ChannelContentListInput): ChannelContentListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelContentPublishRemote`(`payload`: ChannelContentPublishInput): ChannelContentPublishView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelSendQueueSetEnabled`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelTags`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): List<kotlin.String>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `channelUnreadStats`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `checkBlacklist`(`targetUserId`: kotlin.ULong): BlacklistCheckResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `checkFriend`(`friendId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `clearLocalState`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `clearPresenceCache`()
    fun `config`(): PrivchatConfig
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `connect`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `connectBlocking`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `connectionState`(): ConnectionState
    fun `connectionTimeout`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createGroup`(`name`: kotlin.String, `description`: kotlin.String?, `memberIds`: List<kotlin.ULong>?): GroupCreateResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createLocalAttachmentPlaceholder`(`input`: NewMessage, `localMessageId`: kotlin.ULong?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createLocalAttachmentPlaceholderTyped`(`input`: NewMessage, `localMessageId`: kotlin.ULong, `metadata`: LocalAttachmentMetadataInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createLocalMessage`(`input`: NewMessage): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createLocalMessageWithId`(`input`: NewMessage, `localMessageId`: kotlin.ULong?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `dataDir`(): kotlin.String
    fun `debugMode`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteBlacklistEntry`(`blockedUserId`: kotlin.ULong)
    
    /**
     * 本地删除 channel：标记隐藏 + 清空所有关联消息与附件文件。不触达服务端。
     * 返回 true 表示 channel 原本存在；false 表示 channel 不存在或没有消息（幂等）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteChannelLocal`(`channelId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteFriend`(`friendId`: kotlin.ULong): kotlin.Boolean
    
    /**
     * 本地删除消息：删 DB 行 + 清附件目录。不触达服务端。
     * 返回 true 表示确实删到了行；false 表示消息不存在（幂等）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteMessageLocal`(`messageId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `disconnect`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `downloadAttachmentToCache`(`sourcePath`: kotlin.String, `fileName`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `downloadAttachmentToPath`(`sourcePath`: kotlin.String, `targetPath`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `editMessage`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `editMessageBlocking`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueLocalMessage`(`input`: NewMessage): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueOutboundFile`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueOutboundMessage`(`messageId`: kotlin.ULong, `payload`: kotlin.ByteArray): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueText`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueTextWithLocalId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String, `localMessageId`: kotlin.ULong?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ensureSynced`()
    fun `enterBackground`()
    fun `enterForeground`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `entitySyncRemote`(`payload`: SyncEntitiesInput): SyncEntitiesView
    fun `eventConfig`(): EventConfigView
    fun `eventStreamCursor`(): kotlin.ULong
    fun `eventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fetchGroupMembersRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?): GroupMemberRemoteList
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fileRequestUploadTokenRemote`(`payload`: FileRequestUploadTokenInput): FileRequestUploadTokenView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fileUploadCallbackRemote`(`payload`: FileUploadCallbackInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `finalizeLocalAttachment`(`messageId`: kotlin.ULong, `content`: kotlin.String, `thumbStatus`: kotlin.Int)
    
    /**
     * 关注一个 Bot（user_type=2）；server 写 `privchat_bot_follow` + 通知 application
     * 写 `privchat_business_channel` binding。返回 channel_id 后即可 Subscribe + Transfer。
     *
     * Spec: `02-server/SERVICE_ACCOUNT_FOLLOW_SPEC` §3.1。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `followBot`(`botUserId`: kotlin.ULong): BotFollowResult
    
    /**
     * 把指定本地消息转发到目标频道。
     *
     * 内部做两件事：
     * 1. 克隆源消息的 `content / message_type / mime_type / extra`，用当前登录用户作为 `from_uid`，
     * 通过 `enqueue_local_message` 创建新本地行并加入出站队列（走正常发送链路）。
     * 2. 若源消息带附件（`mime_type` 非空），则把源消息目录下的所有文件整体复制到新消息目录，
     * 并把 `media_downloaded` 置为 true，让 UI 立即看到本地缩略图 / 文件。
     *
     * 调用方负责限制不可转发的类型（比如 VOICE / 撤回消息）——SDK 会拒绝撤回消息但不做类型过滤。
     * 可选的 note 文本由调用方自行追加 `send_message` 调用，本接口不负责。
     *
     * 返回新消息的 `message_id`（本地 rowid）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `forwardMessage`(`srcMessageId`: kotlin.ULong, `targetChannelId`: kotlin.ULong, `targetChannelType`: kotlin.Int): kotlin.ULong
    
        @Throws(PrivchatFfiException::class)fun `generateLocalMessageId`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getAllUnreadMentionCounts`(`userId`: kotlin.ULong): List<UnreadMentionCount>
    
    /**
     * 获取附件下载目标目录 (Canonical 路径)
     * 参数必须传入 message 表的主键和创建时间，禁止使用业务脏字段
     */
        @Throws(PrivchatFfiException::class)fun `getAttachmentTargetDir`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getBlacklist`(): List<StoredBlacklistEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelById`(`channelId`: kotlin.ULong): StoredChannel?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelExtra`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): StoredChannelExtra?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelListEntries`(`page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelSyncState`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): ChannelSyncState
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelUnreadCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getConnectionState`(): ConnectionState
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getConnectionSummary`(): ConnectionSummary
    
    /**
     * 读取当前会话的 access token（只读拉取模式）。
     * SDK 权威地管理 token；app 层通常无需直接使用，仅在需要透传给外部服务时调用。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getCurrentAccessToken`(): kotlin.String?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getDevicePushStatus`(`deviceId`: kotlin.String?): DevicePushStatusView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getEarliestId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.ULong?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getFriendPendingRequests`(): List<FriendPendingEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredFriend>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupById`(`groupId`: kotlin.ULong): StoredGroup?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupInfo`(`groupId`: kotlin.ULong): GroupInfoView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroupMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroup>
    suspend fun `getMediaDownloadState`(`messageId`: kotlin.ULong): MediaDownloadState
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessageById`(`messageId`: kotlin.ULong): StoredMessage?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessageExtra`(`messageId`: kotlin.ULong): StoredMessageExtra?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredMessage>
    
    /**
     * jump-to-message 上下文（spec §5）：before/anchor/after 已回填本地库，
     * UI 从本地重查渲染并定位/高亮 anchor。anchor 不可见（不存在/撤回/删除/无权限）
     * 时服务端统一 not_found。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessagesAround`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `messageId`: kotlin.ULong, `beforeLimit`: kotlin.UInt?, `afterLimit`: kotlin.UInt?): MessagesAroundView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessagesRemote`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `beforeServerMessageId`: kotlin.ULong?, `limit`: kotlin.UInt?): MessageHistoryView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getOrCreateDirectChannel`(`peerUserId`: kotlin.ULong): DirectChannelResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPeerReadPts`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.ULong?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPresence`(`userId`: kotlin.ULong): PresenceStatus?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPresenceStats`(): PresenceStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPrivacySettings`(): PrivacySettingsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getProfile`(): ProfileView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getTotalUnreadCount`(`excludeMuted`: kotlin.Boolean): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getTypingStats`(): TypingStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getUnreadMentionCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getUserById`(`userId`: kotlin.ULong): StoredUser?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupAddMembersRemote`(`groupId`: kotlin.ULong, `userIds`: List<kotlin.ULong>): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupApprovalHandleRemote`(`requestId`: kotlin.String, `approved`: kotlin.Boolean, `reason`: kotlin.String?): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupApprovalListRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?): GroupApprovalListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupGetSettingsRemote`(`groupId`: kotlin.ULong): GroupSettingsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupLeaveRemote`(`groupId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupMuteAllRemote`(`groupId`: kotlin.ULong, `enabled`: kotlin.Boolean): GroupMuteAllView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupMuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `durationSeconds`: kotlin.ULong?): kotlin.ULong
    
    /**
     * 群消息置顶 / 取消置顶（仅群主/管理员；`pinned=false` 为取消置顶）。
     * `channel_id` 为消息所在通信频道（群聊场景等于 group_id），服务端会三方校验一致性。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupPinMessageRemote`(`groupId`: kotlin.ULong, `channelId`: kotlin.ULong, `messageId`: kotlin.ULong, `pinned`: kotlin.Boolean): GroupPinMessageView
    
    /**
     * 获取群置顶消息列表（群成员可读，按置顶时间倒序）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupPinnedMessagesRemote`(`groupId`: kotlin.ULong): List<GroupPinnedMessageView>
    
    /**
     * QR_CODE_SPEC v1.3 — `group/qrcode/get`：读群当前永久二维码。
     * Member 及以上可见（server 鉴权）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupQrcodeGetRemote`(`groupId`: kotlin.ULong): GroupQrCodeGetView
    
    /**
     * QR_CODE_SPEC v1.3 — `group/join/qrcode`：扫码加群。
     * Server 用 `qr_key` 反查 `group_id` 后走与邀请相同的 join_need_approval 流程。
     * v1.3 删除了 token 参数（UNIQUE qr_key 本身就是不可枚举凭证）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupQrcodeJoinRemote`(`qrKey`: kotlin.String, `message`: kotlin.String?): GroupQrCodeJoinResult
    
    /**
     * QR_CODE_SPEC v1.3 — `group/qrcode/refresh`：旋转群二维码。
     * Owner/Admin only（server 鉴权）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupQrcodeRefreshRemote`(`groupId`: kotlin.ULong): GroupQrCodeRefreshView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupRemoveMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupSetRoleRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `role`: kotlin.String): GroupRoleSetView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupTransferOwnerRemote`(`groupId`: kotlin.ULong, `targetUserId`: kotlin.ULong): GroupTransferOwnerView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupUnmuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupUpdateSettingsRemote`(`payload`: GroupSettingsUpdateInput): kotlin.Boolean
    fun `heartbeatInterval`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `hideChannel`(`channelId`: kotlin.ULong): kotlin.Boolean
    fun `httpClientConfig`(): HttpClientConfigView
    fun `imageSendMaxEdge`(): kotlin.UInt
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `inviteToGroup`(`groupId`: kotlin.ULong, `memberIds`: List<kotlin.ULong>): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `isBootstrapCompleted`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `isConnected`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `isEventReadBy`(`serverMessageId`: kotlin.ULong, `userId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `isInitialized`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `isShuttingDown`(): kotlin.Boolean
    fun `isSupervisedSyncRunning`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `joinGroupByQrcode`(`qrKey`: kotlin.String): GroupQrCodeJoinResult
    
    /**
     * 读取最近一次 Terminal 认证错误快照。
     * `None` 表示当前没有未清的 ForcedLogout 记录（例如已成功 Connect 一次）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `lastTerminalReason`(): TerminalReason?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `leaveChannel`(`channelId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `leaveGroup`(`groupId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listBlacklistEntries`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredBlacklistEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannelMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannel>
    
    /**
     * F-sync.2: 列出好友申请（非 accepted 行）。
     *
     * - `outgoing=true`：我发出的（is_outgoing=true）；`outgoing=false`：我收到的。
     * - `statuses` 留空 = 全要 pending/rejected/recalled/expired；具体传如
     * [0] 只看 pending、[0,3] pending+rejected 等。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listFriendRequests`(`outgoing`: kotlin.Boolean, `statuses`: List<kotlin.Short>, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredFriend>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredFriend>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroupMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroup>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listLocalAccounts`(): List<LocalAccountSummary>
    
    /**
     * 以 anchor 为轴的本地上下文窗口（显示排序；spec §5 跳转渲染原语）。
     * 通常先调 get_messages_around 完成服务端回填，再用本方法从本地读窗口渲染。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listLocalMessagesAround`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `anchorServerMessageId`: kotlin.ULong, `beforeLimit`: kotlin.ULong, `afterLimit`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listMessageReactions`(`messageId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredMessageReaction>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listMyDevices`(): List<DeviceInfoView>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listPendingReminders`(`uid`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredReminder>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listReactions`(`serverMessageId`: kotlin.ULong): MessageReactionListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listUnreadMentionMessageIds`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong, `limit`: kotlin.ULong): List<kotlin.ULong>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listUsersByIds`(`userIds`: List<kotlin.ULong>): List<StoredUser>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `logConnectionState`(): ConnectionSummary
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `login`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String): LoginResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `logout`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markAllMentionsRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markFullyReadAt`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markMentionRead`(`messageId`: kotlin.ULong, `userId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markMessageSent`(`messageId`: kotlin.ULong, `serverMessageId`: kotlin.ULong, `messageSeq`: kotlin.UInt)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markReadToPts`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markReadToPtsBlocking`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markReminderDone`(`reminderId`: kotlin.ULong, `done`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageReadList`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): MessageReadListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageReadStats`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): MessageReadStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageUnreadCountRemote`(`channelId`: kotlin.ULong): MessageUnreadCountView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `muteChannel`(`channelId`: kotlin.ULong, `muted`: kotlin.Boolean): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `needsSync`(): kotlin.Boolean
    fun `networkEventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextEvent`(`timeoutMs`: kotlin.ULong): SdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextEventEnvelope`(`timeoutMs`: kotlin.ULong): SequencedSdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextNetworkEvent`(`timeoutMs`: kotlin.ULong): SdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextNetworkEventEnvelope`(`timeoutMs`: kotlin.ULong): SequencedSdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextTimelineEvent`(`timeoutMs`: kotlin.ULong): SdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextTimelineEventEnvelope`(`timeoutMs`: kotlin.ULong): SequencedSdkEvent?
    fun `onAppBackground`()
    fun `onAppForeground`()
    fun `onConnectionStateChanged`()
    fun `onMessageReceived`()
    fun `onReactionChanged`()
    fun `onTypingIndicator`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ownLastRead`(`channelId`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `paginateBack`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `paginateForward`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredMessage>
    suspend fun `pauseMessageMediaDownload`(`messageId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `peekOutboundFiles`(`queueIndex`: kotlin.ULong, `limit`: kotlin.ULong): List<QueueMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `peekOutboundMessages`(`limit`: kotlin.ULong): List<QueueMessage>
    
    /**
     * 拉一次 `account/user/detail` 并把对端用户写入本地 users 表。
     * 用于 follow 后让会话头显示昵称/头像，spec BOT_INTERACTION_SPEC §3.0。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `persistUserProfileLocal`(`targetUserId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `pinChannel`(`channelId`: kotlin.ULong, `pinned`: kotlin.Boolean): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ping`()
    
    /**
     * AVATAR_CACHE_SPEC §8: 头像上传前客户端预处理。
     *
     * decode（白名单 jpeg/png/webp，gif/损坏格式直接 Err，不消耗上传流量）→
     * 中心裁剪正方形 → 边长 >480 缩放到 480x480（≤480 不放大）→ 编码 PNG
     * 写临时文件。返回处理后文件路径，App 选图后先过它再走上传管道。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `prepareAvatarImage`(`srcPath`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeGenerate`(`qrType`: kotlin.String, `payload`: kotlin.String, `expireSeconds`: kotlin.ULong?): QrCodeGenerateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeList`(`includeRevoked`: kotlin.Boolean?): QrCodeListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeRefresh`(`qrType`: kotlin.String, `targetId`: kotlin.String): QrCodeRefreshView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeResolve`(`qrKey`: kotlin.String, `token`: kotlin.String?): QrCodeResolveView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeRevoke`(`qrKey`: kotlin.String): QrCodeRevokeView
    fun `queueConfig`(): QueueConfigView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactionStats`(`serverMessageId`: kotlin.ULong): MessageReactionStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactions`(`serverMessageId`: kotlin.ULong): MessageReactionListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactionsBatch`(`serverMessageIds`: List<kotlin.ULong>): ReactionsBatchView
    
    /**
     * [`Self::recache_user_avatar`] 的便捷封装 = recache 当前登录用户头像。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recacheSelfAvatar`(`avatarUrl`: kotlin.String): AvatarCacheResult
    
    /**
     * 底层唯一头像 re-cache 能力（CLIENT_GLOBAL_STATE §4 全局统一）：把 `user_id` 的头像从
     * `avatar_url` 下载到本地并强制落库（avatar / avatar_local_path / avatar_cached_url 三者对齐）。
     * **任意头像来源**（当前用户 / 好友 / 群成员 / 会话 peer / 资料页刷新）都走这一个入口——
     * `avatar_local_path` 是展示主字段，`avatar_url` 只是下载源。下载失败返回 Err，不污染旧缓存。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recacheUserAvatar`(`userId`: kotlin.ULong, `avatarUrl`: kotlin.String): AvatarCacheResult
    
    /**
     * F-sync.2: 撤回自己发出的、尚未处理的好友申请。
     *
     * server 把 friendships.(user_id=me, friend_id=target, status=0) 改成
     * Recalled(4)，并通过 push + entity sync 广播给双方所有设备。本地状态由
     * entity sync 拉到 friend 表（status=4），UI Sent tab 据此显示"已撤回"。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recallFriendRequest`(`targetUserId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recallMessage`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recallMessageBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): kotlin.Boolean
    fun `recentEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    fun `recentNetworkEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    fun `recentNetworkPlainEvents`(`limit`: kotlin.ULong): List<SdkEvent>
    fun `recentTimelineEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    fun `recentTimelinePlainEvents`(`limit`: kotlin.ULong): List<SdkEvent>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recordMention`(`input`: MentionInput): kotlin.ULong
    
    /**
     * Refresh access token via privchat-server `account/auth/refresh` RPC.
     *
     * Pure RPC wrapper. **MUST NOT** read/write SDK store, modify state, or
     * auto-call authenticate. Caller must:
     * 1) provide `refresh_token` (read from caller's own secure storage);
     * 2) handle errors (10009/10010 → user re-login; transport → retry);
     * 3) call `authenticate(uid, result.access_token, device_id)` to apply.
     *
     * 详见 TOKEN_REFRESH_SPEC v1.0 §5。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `refreshAccessToken`(`input`: RefreshAccessTokenInput): RefreshAccessTokenResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `register`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String): LoginResult
    fun `registerLifecycleHook`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `rejectFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeFromBlacklist`(`blockedUserId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeGroupMember`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeReaction`(`serverMessageId`: kotlin.ULong, `emoji`: kotlin.String): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeVideoProcessHook`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `requireCurrentUserId`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `resolveAttachmentBytes`(`sourcePath`: kotlin.String): kotlin.ByteArray
    
    /**
     * 解析本地已存在的附件路径 (含 Legacy 兼容)
     */fun `resolveAttachmentPath`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long, `filename`: kotlin.String?): kotlin.String?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `resolveChannelIdByServerMessageId`(`serverMessageId`: kotlin.ULong): kotlin.ULong
    suspend fun `resolveChannelType`(`channelId`: kotlin.ULong): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `resolveLocalMessageIdByServerMessageId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `serverMessageId`: kotlin.ULong): kotlin.ULong?
    
    /**
     * 查找本地缩略图路径：{user_root}/files/{yyyymm}/{message_id}/thumb.webp
     */fun `resolveThumbnailPath`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long): kotlin.String?
    suspend fun `resumeMessageMediaDownload`(`messageId`: kotlin.ULong)
    fun `retryConfig`(): RetryConfigView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `retryMessage`(`messageId`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `rpcCall`(`route`: kotlin.String, `bodyJson`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `runBootstrapSync`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchChannel`(`keyword`: kotlin.String): List<StoredChannel>
    
    /**
     * 云端历史搜索（spec §4）。channel_id: Some=CHANNEL scope / None=GLOBAL。
     * 命中是 snippet 投影不落本地库；服务端限频 300ms/user——UI 必须 debounce
     * 300–500ms、忽略过期 in-flight 结果、query<2 字符不发起远程。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchMessageHistory`(`query`: kotlin.String, `channelId`: kotlin.ULong?, `cursor`: kotlin.String?, `limit`: kotlin.UInt?): SearchHistoryView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `keyword`: kotlin.String): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchUserByQrcode`(`qrKey`: kotlin.String, `token`: kotlin.String?): AccountSearchResultView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchUsers`(`query`: kotlin.String): List<SearchUserEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `seenByForEvent`(`serverMessageId`: kotlin.ULong): List<SeenByEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendAttachmentBytes`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendAttachmentFromPath`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `path`: kotlin.String): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendContactCardMessage`(`input`: ContactCardMessageInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendFriendRequest`(`targetUserId`: kotlin.ULong, `message`: kotlin.String?, `source`: kotlin.String?, `sourceId`: kotlin.String?): FriendRequestResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendLinkMessage`(`input`: LinkMessageInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendLocalMessageNow`(`input`: NewMessage): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendLocationMessage`(`input`: LocationMessageInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendMessage`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendMessageBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendMessageWithInput`(`input`: NewMessage): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendMessageWithOptions`(`input`: NewMessage, `options`: SendMessageOptionsInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendQueueSetEnabled`(`enabled`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isTyping`: kotlin.Boolean, `actionType`: TypingActionType)
    fun `serverConfig`(): PrivchatConfig
    fun `servers`(): List<ServerEndpoint>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sessionSnapshot`(): SessionSnapshot?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelFavourite`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)
    
    /**
     * 设置本地 channel 隐藏标记（不触达服务端）。
     * 隐藏后会话列表不再显示该 channel，收到新消息时自动取消隐藏。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelHiddenLocal`(`channelId`: kotlin.ULong, `hidden`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelLowPriority`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `mode`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setCurrentUid`(`uid`: kotlin.String)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setMessagePinned`(`messageId`: kotlin.ULong, `isPinned`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setMessageRevoke`(`messageId`: kotlin.ULong, `revoked`: kotlin.Boolean, `revoker`: kotlin.ULong?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setNetworkHint`(`hint`: NetworkHint)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setVideoProcessHook`(`hook`: VideoProcessHook?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `shutdown`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `shutdownBlocking`()
    
    /**
     * Start a streaming Telegram-style download for a message's primary attachment.
     * Delegates to [`PrivchatSdk::start_message_media_download`] — the core SDK owns
     * the state machine, so the Rust iced UI and the FFI Kotlin/iOS layer share it.
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startMessageMediaDownload`(`messageId`: kotlin.ULong, `downloadUrl`: kotlin.String, `mime`: kotlin.String, `filenameHint`: kotlin.String?, `createdAtMs`: kotlin.Long)
    
    /**
     * Start a streaming download for an attachment-encrypted (v1) message by
     * `file_id`. The SDK resolves the signed URL + cek via `file/get_url` and
     * decrypts the blob on completion. Prefer this over the URL-driven entry for
     * any message that carries a `file_id`; the legacy URL form is retained only
     * for plaintext (pre-encryption) attachments.
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startMessageMediaDownloadByFileId`(`messageId`: kotlin.ULong, `fileId`: kotlin.ULong, `mime`: kotlin.String, `filenameHint`: kotlin.String?, `createdAtMs`: kotlin.Long)
    
        @Throws(PrivchatFfiException::class)fun `startSupervisedSync`(`intervalSecs`: kotlin.ULong)
    fun `startTransportDisconnectListener`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startTypingBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stickerPackageDetailRemote`(`payload`: StickerPackageDetailInput): StickerPackageDetailView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stickerPackageListRemote`(`payload`: StickerPackageListInput): StickerPackageListView
    fun `stopSupervisedSync`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stopTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `storage`(): UserStoragePaths
    
    /**
     * Plan 2：宿主处理完 `SdkEvent::MediaJobRequested` 后回传结果。
     */
        @Throws(PrivchatFfiException::class)fun `submitMediaJobResult`(`jobId`: kotlin.String, `result`: MediaJobResult)
    
    /**
     * 订阅频道事件（进入聊天页面时调用，接收 typing / presence 等状态事件）
     * channel_type: 0=Private, 1=Group, 2=Room
     * token: 可选，Room 类型订阅时传入业务 API 签发的 ticket（JWT）
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `subscribeChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.UByte, `token`: kotlin.String?)
    fun `subscribeEvents`(): kotlin.Boolean
    fun `subscribeNetworkStatus`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncAllChannels`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncBatchGetChannelPtsRemote`(`payload`: BatchGetChannelPtsInput): BatchGetChannelPtsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncEntities`(`entityType`: kotlin.String, `scope`: kotlin.String?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncEntitiesInBackground`(`entityType`: kotlin.String, `scope`: kotlin.String?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncGetChannelPtsRemote`(`payload`: GetChannelPtsInput): ChannelPtsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncGetDifferenceRemote`(`payload`: GetDifferenceInput): GetDifferenceView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncMessages`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncMessagesInBackground`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncState`(): SyncStateSnapshot
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncSubmitRemote`(`payload`: SyncSubmitInput): SyncSubmitView
    fun `timelineEventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    fun `timezoneHours`(): kotlin.Int
    fun `timezoneLocal`(): kotlin.String
    fun `timezoneMinutes`(): kotlin.Int
    fun `timezoneSeconds`(): kotlin.Int
    fun `toClientEndpoint`(): kotlin.String?
    
    /**
     * Channel Transfer client→app RPC. Sends a wire `TransferRequest`
     * (biz_type=19) and awaits the matching `TransferResponse` (biz_type=20).
     * `timeout_ms = 0` falls back to the SDK default (5000 ms).
     * See `02-server/CHANNEL_TRANSFER_SPEC.md` v2.0 and
     * `07-application/BOT_INTERACTION_SPEC.md` for typical routes.
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `transfer`(`channelId`: kotlin.ULong, `route`: kotlin.String, `body`: kotlin.ByteArray, `timeoutMs`: kotlin.ULong): TransferReplyView
    
    /**
     * 取消关注 Bot；server 切 status=0 但**不**删 channel / 历史 / application 业务行。
     *
     * Spec: `02-server/SERVICE_ACCOUNT_FOLLOW_SPEC` §3.2。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `unfollowBot`(`botUserId`: kotlin.ULong): BotUnfollowResult
    
    /**
     * 取消订阅频道事件（离开聊天页面时调用）
     * channel_type: 0=Private, 1=Group, 2=Room
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `unsubscribeChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.UByte)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateDevicePushState`(`deviceId`: kotlin.String, `apnsArmed`: kotlin.Boolean, `pushToken`: kotlin.String?): DevicePushUpdateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateMediaDownloaded`(`messageId`: kotlin.ULong, `downloaded`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateMessageStatus`(`messageId`: kotlin.ULong, `status`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updatePrivacySettings`(`payload`: AccountPrivacyUpdateInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateProfile`(`payload`: ProfileUpdateInput): ProfileView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateThumbStatus`(`messageId`: kotlin.ULong, `thumbStatus`: kotlin.Int)
    
    /**
     * 设置用户备注（本地）
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateUserAlias`(`userId`: kotlin.ULong, `alias`: kotlin.String?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertBlacklistEntry`(`input`: UpsertBlacklistInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertChannel`(`input`: UpsertChannelInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertChannelExtra`(`input`: UpsertChannelExtraInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertChannelMember`(`input`: UpsertChannelMemberInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertFriend`(`input`: UpsertFriendInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertGroup`(`input`: UpsertGroupInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertGroupMember`(`input`: UpsertGroupMemberInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertMessageReaction`(`input`: UpsertMessageReactionInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertReminder`(`input`: UpsertReminderInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `upsertUser`(`input`: UpsertUserInput)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userId`(): kotlin.ULong?
    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/get`：读自己的永久 qr_key + URL。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeGet`(): UserQrCodeGetView
    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/refresh`：旋转自己的 qr_key。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeRefresh`(): UserQrCodeRefreshView
    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/resolve`：把对端 qrkey 翻译成最小用户卡片。
     * 响应**不含** qr_key（避免二次扩散）。
     */
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeResolve`(`qrKey`: kotlin.String): UserQrCodeResolveView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userStoragePaths`(): UserStoragePaths
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `wipeCurrentUserFull`()
    
    companion object
}

expect open class PrivchatClient: Disposable, PrivchatClientInterface {
    constructor(pointer: Pointer)

    /**
     * This constructor can be used to instantiate a fake object. Only used for tests. Any
     * attempt to actually use an object constructed this way will fail as there is no
     * connected Rust object.
     */
    constructor(noPointer: NoPointer)
    constructor(`config`: PrivchatConfig)

    override fun destroy()
    override fun close()

    internal inline fun <R> callWithPointer(block: (ptr: Pointer) -> R): R
    fun uniffiClonePointer(): Pointer

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `acceptFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `accountUserDetailRemote`(`userId`: kotlin.ULong) : AccountUserDetailView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `accountUserShareCardRemote`(`userId`: kotlin.ULong) : AccountUserShareCardView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `accountUserUpdateRemote`(`payload`: AccountUserUpdateInput) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ackOutboundFiles`(`queueIndex`: kotlin.ULong, `messageIds`: List<kotlin.ULong>) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ackOutboundMessages`(`messageIds`: List<kotlin.ULong>) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `addChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUids`: List<kotlin.ULong>)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `addReaction`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `addReactionBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class)override fun `addServer`(`endpoint`: ServerEndpoint)
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `addToBlacklist`(`blockedUserId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `assetsDir`() : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `authLogoutRemote`() : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `authenticate`(`userId`: kotlin.ULong, `token`: kotlin.String, `deviceId`: kotlin.String)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `batchGetPresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus>

    override fun `build`(): kotlin.String
    

    override fun `builder`(): kotlin.String
    

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `cancelMessageMediaDownload`(`messageId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelBroadcastCreateRemote`(`payload`: ChannelBroadcastCreateInput) : ChannelBroadcastCreateView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelBroadcastListRemote`(`payload`: ChannelBroadcastListInput) : ChannelBroadcastListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelBroadcastSubscribeRemote`(`payload`: ChannelBroadcastSubscribeInput) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelContentListRemote`(`payload`: ChannelContentListInput) : ChannelContentListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelContentPublishRemote`(`payload`: ChannelContentPublishInput) : ChannelContentPublishView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelSendQueueSetEnabled`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelTags`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : List<kotlin.String>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `channelUnreadStats`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `checkBlacklist`(`targetUserId`: kotlin.ULong) : BlacklistCheckResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `checkFriend`(`friendId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `clearLocalState`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `clearPresenceCache`()

    override fun `config`(): PrivchatConfig
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `connect`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `connectBlocking`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `connectionState`() : ConnectionState

    override fun `connectionTimeout`(): kotlin.ULong
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `createGroup`(`name`: kotlin.String, `description`: kotlin.String?, `memberIds`: List<kotlin.ULong>?) : GroupCreateResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `createLocalAttachmentPlaceholder`(`input`: NewMessage, `localMessageId`: kotlin.ULong?) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `createLocalAttachmentPlaceholderTyped`(`input`: NewMessage, `localMessageId`: kotlin.ULong, `metadata`: LocalAttachmentMetadataInput) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `createLocalMessage`(`input`: NewMessage) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `createLocalMessageWithId`(`input`: NewMessage, `localMessageId`: kotlin.ULong?) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `dataDir`() : kotlin.String

    override fun `debugMode`(): kotlin.Boolean
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteBlacklistEntry`(`blockedUserId`: kotlin.ULong)

    
    /**
     * 本地删除 channel：标记隐藏 + 清空所有关联消息与附件文件。不触达服务端。
     * 返回 true 表示 channel 原本存在；false 表示 channel 不存在或没有消息（幂等）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteChannelLocal`(`channelId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteFriend`(`friendId`: kotlin.ULong) : kotlin.Boolean

    
    /**
     * 本地删除消息：删 DB 行 + 清附件目录。不触达服务端。
     * 返回 true 表示确实删到了行；false 表示消息不存在（幂等）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteMessageLocal`(`messageId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `disconnect`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `downloadAttachmentToCache`(`sourcePath`: kotlin.String, `fileName`: kotlin.String) : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `downloadAttachmentToPath`(`sourcePath`: kotlin.String, `targetPath`: kotlin.String) : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `editMessage`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `editMessageBlocking`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueLocalMessage`(`input`: NewMessage) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueOutboundFile`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray) : FileQueueRef

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueOutboundMessage`(`messageId`: kotlin.ULong, `payload`: kotlin.ByteArray) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueText`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueTextWithLocalId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String, `localMessageId`: kotlin.ULong?) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ensureSynced`()

    override fun `enterBackground`()
    

    override fun `enterForeground`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `entitySyncRemote`(`payload`: SyncEntitiesInput) : SyncEntitiesView

    override fun `eventConfig`(): EventConfigView
    

    override fun `eventStreamCursor`(): kotlin.ULong
    

    override fun `eventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `fetchGroupMembersRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?) : GroupMemberRemoteList

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `fileRequestUploadTokenRemote`(`payload`: FileRequestUploadTokenInput) : FileRequestUploadTokenView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `fileUploadCallbackRemote`(`payload`: FileUploadCallbackInput) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `finalizeLocalAttachment`(`messageId`: kotlin.ULong, `content`: kotlin.String, `thumbStatus`: kotlin.Int)

    
    /**
     * 关注一个 Bot（user_type=2）；server 写 `privchat_bot_follow` + 通知 application
     * 写 `privchat_business_channel` binding。返回 channel_id 后即可 Subscribe + Transfer。
     *
     * Spec: `02-server/SERVICE_ACCOUNT_FOLLOW_SPEC` §3.1。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `followBot`(`botUserId`: kotlin.ULong) : BotFollowResult

    
    /**
     * 把指定本地消息转发到目标频道。
     *
     * 内部做两件事：
     * 1. 克隆源消息的 `content / message_type / mime_type / extra`，用当前登录用户作为 `from_uid`，
     * 通过 `enqueue_local_message` 创建新本地行并加入出站队列（走正常发送链路）。
     * 2. 若源消息带附件（`mime_type` 非空），则把源消息目录下的所有文件整体复制到新消息目录，
     * 并把 `media_downloaded` 置为 true，让 UI 立即看到本地缩略图 / 文件。
     *
     * 调用方负责限制不可转发的类型（比如 VOICE / 撤回消息）——SDK 会拒绝撤回消息但不做类型过滤。
     * 可选的 note 文本由调用方自行追加 `send_message` 调用，本接口不负责。
     *
     * 返回新消息的 `message_id`（本地 rowid）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `forwardMessage`(`srcMessageId`: kotlin.ULong, `targetChannelId`: kotlin.ULong, `targetChannelType`: kotlin.Int) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class)override fun `generateLocalMessageId`(): kotlin.ULong
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getAllUnreadMentionCounts`(`userId`: kotlin.ULong) : List<UnreadMentionCount>

    
    /**
     * 获取附件下载目标目录 (Canonical 路径)
     * 参数必须传入 message 表的主键和创建时间，禁止使用业务脏字段
     */
    @Throws(PrivchatFfiException::class)override fun `getAttachmentTargetDir`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long): kotlin.String
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getBlacklist`() : List<StoredBlacklistEntry>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannelById`(`channelId`: kotlin.ULong) : StoredChannel?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannelExtra`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : StoredChannelExtra?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannelListEntries`(`page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredChannel>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannelSyncState`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : ChannelSyncState

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannelUnreadCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannel>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getConnectionState`() : ConnectionState

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getConnectionSummary`() : ConnectionSummary

    
    /**
     * 读取当前会话的 access token（只读拉取模式）。
     * SDK 权威地管理 token；app 层通常无需直接使用，仅在需要透传给外部服务时调用。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getCurrentAccessToken`() : kotlin.String?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getDevicePushStatus`(`deviceId`: kotlin.String?) : DevicePushStatusView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getEarliestId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.ULong?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getFriendPendingRequests`() : List<FriendPendingEntry>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredFriend>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getGroupById`(`groupId`: kotlin.ULong) : StoredGroup?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getGroupInfo`(`groupId`: kotlin.ULong) : GroupInfoView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroupMember>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroup>

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMediaDownloadState`(`messageId`: kotlin.ULong) : MediaDownloadState

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessageById`(`messageId`: kotlin.ULong) : StoredMessage?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessageExtra`(`messageId`: kotlin.ULong) : StoredMessageExtra?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessage>

    
    /**
     * jump-to-message 上下文（spec §5）：before/anchor/after 已回填本地库，
     * UI 从本地重查渲染并定位/高亮 anchor。anchor 不可见（不存在/撤回/删除/无权限）
     * 时服务端统一 not_found。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessagesAround`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `messageId`: kotlin.ULong, `beforeLimit`: kotlin.UInt?, `afterLimit`: kotlin.UInt?) : MessagesAroundView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessagesRemote`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `beforeServerMessageId`: kotlin.ULong?, `limit`: kotlin.UInt?) : MessageHistoryView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getOrCreateDirectChannel`(`peerUserId`: kotlin.ULong) : DirectChannelResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getPeerReadPts`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.ULong?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getPresence`(`userId`: kotlin.ULong) : PresenceStatus?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getPresenceStats`() : PresenceStatsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getPrivacySettings`() : PrivacySettingsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getProfile`() : ProfileView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getTotalUnreadCount`(`excludeMuted`: kotlin.Boolean) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getTypingStats`() : TypingStatsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getUnreadMentionCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getUserById`(`userId`: kotlin.ULong) : StoredUser?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupAddMembersRemote`(`groupId`: kotlin.ULong, `userIds`: List<kotlin.ULong>) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupApprovalHandleRemote`(`requestId`: kotlin.String, `approved`: kotlin.Boolean, `reason`: kotlin.String?) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupApprovalListRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?) : GroupApprovalListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupGetSettingsRemote`(`groupId`: kotlin.ULong) : GroupSettingsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupLeaveRemote`(`groupId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupMuteAllRemote`(`groupId`: kotlin.ULong, `enabled`: kotlin.Boolean) : GroupMuteAllView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupMuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `durationSeconds`: kotlin.ULong?) : kotlin.ULong

    
    /**
     * 群消息置顶 / 取消置顶（仅群主/管理员；`pinned=false` 为取消置顶）。
     * `channel_id` 为消息所在通信频道（群聊场景等于 group_id），服务端会三方校验一致性。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupPinMessageRemote`(`groupId`: kotlin.ULong, `channelId`: kotlin.ULong, `messageId`: kotlin.ULong, `pinned`: kotlin.Boolean) : GroupPinMessageView

    
    /**
     * 获取群置顶消息列表（群成员可读，按置顶时间倒序）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupPinnedMessagesRemote`(`groupId`: kotlin.ULong) : List<GroupPinnedMessageView>

    
    /**
     * QR_CODE_SPEC v1.3 — `group/qrcode/get`：读群当前永久二维码。
     * Member 及以上可见（server 鉴权）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupQrcodeGetRemote`(`groupId`: kotlin.ULong) : GroupQrCodeGetView

    
    /**
     * QR_CODE_SPEC v1.3 — `group/join/qrcode`：扫码加群。
     * Server 用 `qr_key` 反查 `group_id` 后走与邀请相同的 join_need_approval 流程。
     * v1.3 删除了 token 参数（UNIQUE qr_key 本身就是不可枚举凭证）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupQrcodeJoinRemote`(`qrKey`: kotlin.String, `message`: kotlin.String?) : GroupQrCodeJoinResult

    
    /**
     * QR_CODE_SPEC v1.3 — `group/qrcode/refresh`：旋转群二维码。
     * Owner/Admin only（server 鉴权）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupQrcodeRefreshRemote`(`groupId`: kotlin.ULong) : GroupQrCodeRefreshView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupRemoveMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupSetRoleRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `role`: kotlin.String) : GroupRoleSetView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupTransferOwnerRemote`(`groupId`: kotlin.ULong, `targetUserId`: kotlin.ULong) : GroupTransferOwnerView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupUnmuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupUpdateSettingsRemote`(`payload`: GroupSettingsUpdateInput) : kotlin.Boolean

    override fun `heartbeatInterval`(): kotlin.ULong
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `hideChannel`(`channelId`: kotlin.ULong) : kotlin.Boolean

    override fun `httpClientConfig`(): HttpClientConfigView
    

    override fun `imageSendMaxEdge`(): kotlin.UInt
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `inviteToGroup`(`groupId`: kotlin.ULong, `memberIds`: List<kotlin.ULong>) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `isBootstrapCompleted`() : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `isConnected`() : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `isEventReadBy`(`serverMessageId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `isInitialized`() : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `isShuttingDown`() : kotlin.Boolean

    override fun `isSupervisedSyncRunning`(): kotlin.Boolean
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `joinGroupByQrcode`(`qrKey`: kotlin.String) : GroupQrCodeJoinResult

    
    /**
     * 读取最近一次 Terminal 认证错误快照。
     * `None` 表示当前没有未清的 ForcedLogout 记录（例如已成功 Connect 一次）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `lastTerminalReason`() : TerminalReason?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `leaveChannel`(`channelId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `leaveGroup`(`groupId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listBlacklistEntries`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredBlacklistEntry>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannelMember>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannel>

    
    /**
     * F-sync.2: 列出好友申请（非 accepted 行）。
     *
     * - `outgoing=true`：我发出的（is_outgoing=true）；`outgoing=false`：我收到的。
     * - `statuses` 留空 = 全要 pending/rejected/recalled/expired；具体传如
     * [0] 只看 pending、[0,3] pending+rejected 等。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listFriendRequests`(`outgoing`: kotlin.Boolean, `statuses`: List<kotlin.Short>, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredFriend>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredFriend>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroupMember>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroup>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listLocalAccounts`() : List<LocalAccountSummary>

    
    /**
     * 以 anchor 为轴的本地上下文窗口（显示排序；spec §5 跳转渲染原语）。
     * 通常先调 get_messages_around 完成服务端回填，再用本方法从本地读窗口渲染。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listLocalMessagesAround`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `anchorServerMessageId`: kotlin.ULong, `beforeLimit`: kotlin.ULong, `afterLimit`: kotlin.ULong) : List<StoredMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listMessageReactions`(`messageId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessageReaction>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listMyDevices`() : List<DeviceInfoView>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listPendingReminders`(`uid`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredReminder>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listReactions`(`serverMessageId`: kotlin.ULong) : MessageReactionListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listUnreadMentionMessageIds`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong, `limit`: kotlin.ULong) : List<kotlin.ULong>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `listUsersByIds`(`userIds`: List<kotlin.ULong>) : List<StoredUser>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `logConnectionState`() : ConnectionSummary

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `login`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String) : LoginResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `logout`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markAllMentionsRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markFullyReadAt`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markMentionRead`(`messageId`: kotlin.ULong, `userId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markMessageSent`(`messageId`: kotlin.ULong, `serverMessageId`: kotlin.ULong, `messageSeq`: kotlin.UInt)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markReadToPts`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markReadToPtsBlocking`(`channelId`: kotlin.ULong, `readPts`: kotlin.ULong) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markReminderDone`(`reminderId`: kotlin.ULong, `done`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `messageReadList`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : MessageReadListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `messageReadStats`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : MessageReadStatsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `messageUnreadCountRemote`(`channelId`: kotlin.ULong) : MessageUnreadCountView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `muteChannel`(`channelId`: kotlin.ULong, `muted`: kotlin.Boolean) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `needsSync`() : kotlin.Boolean

    override fun `networkEventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextEvent`(`timeoutMs`: kotlin.ULong) : SdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextEventEnvelope`(`timeoutMs`: kotlin.ULong) : SequencedSdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextNetworkEvent`(`timeoutMs`: kotlin.ULong) : SdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextNetworkEventEnvelope`(`timeoutMs`: kotlin.ULong) : SequencedSdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextTimelineEvent`(`timeoutMs`: kotlin.ULong) : SdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextTimelineEventEnvelope`(`timeoutMs`: kotlin.ULong) : SequencedSdkEvent?

    override fun `onAppBackground`()
    

    override fun `onAppForeground`()
    

    override fun `onConnectionStateChanged`()
    

    override fun `onMessageReceived`()
    

    override fun `onReactionChanged`()
    

    override fun `onTypingIndicator`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ownLastRead`(`channelId`: kotlin.ULong) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `paginateBack`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `paginateForward`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredMessage>

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `pauseMessageMediaDownload`(`messageId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `peekOutboundFiles`(`queueIndex`: kotlin.ULong, `limit`: kotlin.ULong) : List<QueueMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `peekOutboundMessages`(`limit`: kotlin.ULong) : List<QueueMessage>

    
    /**
     * 拉一次 `account/user/detail` 并把对端用户写入本地 users 表。
     * 用于 follow 后让会话头显示昵称/头像，spec BOT_INTERACTION_SPEC §3.0。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `persistUserProfileLocal`(`targetUserId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `pinChannel`(`channelId`: kotlin.ULong, `pinned`: kotlin.Boolean) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ping`()

    
    /**
     * AVATAR_CACHE_SPEC §8: 头像上传前客户端预处理。
     *
     * decode（白名单 jpeg/png/webp，gif/损坏格式直接 Err，不消耗上传流量）→
     * 中心裁剪正方形 → 边长 >480 缩放到 480x480（≤480 不放大）→ 编码 PNG
     * 写临时文件。返回处理后文件路径，App 选图后先过它再走上传管道。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `prepareAvatarImage`(`srcPath`: kotlin.String) : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeGenerate`(`qrType`: kotlin.String, `payload`: kotlin.String, `expireSeconds`: kotlin.ULong?) : QrCodeGenerateView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeList`(`includeRevoked`: kotlin.Boolean?) : QrCodeListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeRefresh`(`qrType`: kotlin.String, `targetId`: kotlin.String) : QrCodeRefreshView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeResolve`(`qrKey`: kotlin.String, `token`: kotlin.String?) : QrCodeResolveView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeRevoke`(`qrKey`: kotlin.String) : QrCodeRevokeView

    override fun `queueConfig`(): QueueConfigView
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `reactionStats`(`serverMessageId`: kotlin.ULong) : MessageReactionStatsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `reactions`(`serverMessageId`: kotlin.ULong) : MessageReactionListView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `reactionsBatch`(`serverMessageIds`: List<kotlin.ULong>) : ReactionsBatchView

    
    /**
     * [`Self::recache_user_avatar`] 的便捷封装 = recache 当前登录用户头像。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recacheSelfAvatar`(`avatarUrl`: kotlin.String) : AvatarCacheResult

    
    /**
     * 底层唯一头像 re-cache 能力（CLIENT_GLOBAL_STATE §4 全局统一）：把 `user_id` 的头像从
     * `avatar_url` 下载到本地并强制落库（avatar / avatar_local_path / avatar_cached_url 三者对齐）。
     * **任意头像来源**（当前用户 / 好友 / 群成员 / 会话 peer / 资料页刷新）都走这一个入口——
     * `avatar_local_path` 是展示主字段，`avatar_url` 只是下载源。下载失败返回 Err，不污染旧缓存。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recacheUserAvatar`(`userId`: kotlin.ULong, `avatarUrl`: kotlin.String) : AvatarCacheResult

    
    /**
     * F-sync.2: 撤回自己发出的、尚未处理的好友申请。
     *
     * server 把 friendships.(user_id=me, friend_id=target, status=0) 改成
     * Recalled(4)，并通过 push + entity sync 广播给双方所有设备。本地状态由
     * entity sync 拉到 friend 表（status=4），UI Sent tab 据此显示"已撤回"。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recallFriendRequest`(`targetUserId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recallMessage`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recallMessageBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean

    override fun `recentEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    override fun `recentNetworkEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    override fun `recentNetworkPlainEvents`(`limit`: kotlin.ULong): List<SdkEvent>
    

    override fun `recentTimelineEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    override fun `recentTimelinePlainEvents`(`limit`: kotlin.ULong): List<SdkEvent>
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recordMention`(`input`: MentionInput) : kotlin.ULong

    
    /**
     * Refresh access token via privchat-server `account/auth/refresh` RPC.
     *
     * Pure RPC wrapper. **MUST NOT** read/write SDK store, modify state, or
     * auto-call authenticate. Caller must:
     * 1) provide `refresh_token` (read from caller's own secure storage);
     * 2) handle errors (10009/10010 → user re-login; transport → retry);
     * 3) call `authenticate(uid, result.access_token, device_id)` to apply.
     *
     * 详见 TOKEN_REFRESH_SPEC v1.0 §5。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `refreshAccessToken`(`input`: RefreshAccessTokenInput) : RefreshAccessTokenResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `register`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String) : LoginResult

    override fun `registerLifecycleHook`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `rejectFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `removeChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `removeFromBlacklist`(`blockedUserId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `removeGroupMember`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `removeReaction`(`serverMessageId`: kotlin.ULong, `emoji`: kotlin.String) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `removeVideoProcessHook`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `requireCurrentUserId`() : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveAttachmentBytes`(`sourcePath`: kotlin.String) : kotlin.ByteArray

    
    /**
     * 解析本地已存在的附件路径 (含 Legacy 兼容)
     */override fun `resolveAttachmentPath`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long, `filename`: kotlin.String?): kotlin.String?
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveChannelIdByServerMessageId`(`serverMessageId`: kotlin.ULong) : kotlin.ULong

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveChannelType`(`channelId`: kotlin.ULong) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveLocalMessageIdByServerMessageId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `serverMessageId`: kotlin.ULong) : kotlin.ULong?

    
    /**
     * 查找本地缩略图路径：{user_root}/files/{yyyymm}/{message_id}/thumb.webp
     */override fun `resolveThumbnailPath`(`uid`: kotlin.ULong, `messageId`: kotlin.Long, `createdAtMs`: kotlin.Long): kotlin.String?
    

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resumeMessageMediaDownload`(`messageId`: kotlin.ULong)

    override fun `retryConfig`(): RetryConfigView
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `retryMessage`(`messageId`: kotlin.ULong) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `rpcCall`(`route`: kotlin.String, `bodyJson`: kotlin.String) : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `runBootstrapSync`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `searchChannel`(`keyword`: kotlin.String) : List<StoredChannel>

    
    /**
     * 云端历史搜索（spec §4）。channel_id: Some=CHANNEL scope / None=GLOBAL。
     * 命中是 snippet 投影不落本地库；服务端限频 300ms/user——UI 必须 debounce
     * 300–500ms、忽略过期 in-flight 结果、query<2 字符不发起远程。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `searchMessageHistory`(`query`: kotlin.String, `channelId`: kotlin.ULong?, `cursor`: kotlin.String?, `limit`: kotlin.UInt?) : SearchHistoryView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `searchMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `keyword`: kotlin.String) : List<StoredMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `searchUserByQrcode`(`qrKey`: kotlin.String, `token`: kotlin.String?) : AccountSearchResultView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `searchUsers`(`query`: kotlin.String) : List<SearchUserEntry>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `seenByForEvent`(`serverMessageId`: kotlin.ULong) : List<SeenByEntry>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendAttachmentBytes`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray) : FileQueueRef

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendAttachmentFromPath`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `path`: kotlin.String) : FileQueueRef

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendContactCardMessage`(`input`: ContactCardMessageInput) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendFriendRequest`(`targetUserId`: kotlin.ULong, `message`: kotlin.String?, `source`: kotlin.String?, `sourceId`: kotlin.String?) : FriendRequestResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendLinkMessage`(`input`: LinkMessageInput) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendLocalMessageNow`(`input`: NewMessage) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendLocationMessage`(`input`: LocationMessageInput) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendMessage`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendMessageBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendMessageWithInput`(`input`: NewMessage) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendMessageWithOptions`(`input`: NewMessage, `options`: SendMessageOptionsInput) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendQueueSetEnabled`(`enabled`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sendTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isTyping`: kotlin.Boolean, `actionType`: TypingActionType)

    override fun `serverConfig`(): PrivchatConfig
    

    override fun `servers`(): List<ServerEndpoint>
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `sessionSnapshot`() : SessionSnapshot?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelFavourite`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)

    
    /**
     * 设置本地 channel 隐藏标记（不触达服务端）。
     * 隐藏后会话列表不再显示该 channel，收到新消息时自动取消隐藏。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelHiddenLocal`(`channelId`: kotlin.ULong, `hidden`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelLowPriority`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `mode`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setCurrentUid`(`uid`: kotlin.String)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setMessagePinned`(`messageId`: kotlin.ULong, `isPinned`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setMessageRevoke`(`messageId`: kotlin.ULong, `revoked`: kotlin.Boolean, `revoker`: kotlin.ULong?)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setNetworkHint`(`hint`: NetworkHint)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setVideoProcessHook`(`hook`: VideoProcessHook?)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `shutdown`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `shutdownBlocking`()

    
    /**
     * Start a streaming Telegram-style download for a message's primary attachment.
     * Delegates to [`PrivchatSdk::start_message_media_download`] — the core SDK owns
     * the state machine, so the Rust iced UI and the FFI Kotlin/iOS layer share it.
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `startMessageMediaDownload`(`messageId`: kotlin.ULong, `downloadUrl`: kotlin.String, `mime`: kotlin.String, `filenameHint`: kotlin.String?, `createdAtMs`: kotlin.Long)

    
    /**
     * Start a streaming download for an attachment-encrypted (v1) message by
     * `file_id`. The SDK resolves the signed URL + cek via `file/get_url` and
     * decrypts the blob on completion. Prefer this over the URL-driven entry for
     * any message that carries a `file_id`; the legacy URL form is retained only
     * for plaintext (pre-encryption) attachments.
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `startMessageMediaDownloadByFileId`(`messageId`: kotlin.ULong, `fileId`: kotlin.ULong, `mime`: kotlin.String, `filenameHint`: kotlin.String?, `createdAtMs`: kotlin.Long)

    
    @Throws(PrivchatFfiException::class)override fun `startSupervisedSync`(`intervalSecs`: kotlin.ULong)
    

    override fun `startTransportDisconnectListener`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `startTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `startTypingBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `stickerPackageDetailRemote`(`payload`: StickerPackageDetailInput) : StickerPackageDetailView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `stickerPackageListRemote`(`payload`: StickerPackageListInput) : StickerPackageListView

    override fun `stopSupervisedSync`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `stopTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `storage`() : UserStoragePaths

    
    /**
     * Plan 2：宿主处理完 `SdkEvent::MediaJobRequested` 后回传结果。
     */
    @Throws(PrivchatFfiException::class)override fun `submitMediaJobResult`(`jobId`: kotlin.String, `result`: MediaJobResult)
    

    
    /**
     * 订阅频道事件（进入聊天页面时调用，接收 typing / presence 等状态事件）
     * channel_type: 0=Private, 1=Group, 2=Room
     * token: 可选，Room 类型订阅时传入业务 API 签发的 ticket（JWT）
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `subscribeChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.UByte, `token`: kotlin.String?)

    override fun `subscribeEvents`(): kotlin.Boolean
    

    override fun `subscribeNetworkStatus`(): kotlin.Boolean
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncAllChannels`() : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncBatchGetChannelPtsRemote`(`payload`: BatchGetChannelPtsInput) : BatchGetChannelPtsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncEntities`(`entityType`: kotlin.String, `scope`: kotlin.String?) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncEntitiesInBackground`(`entityType`: kotlin.String, `scope`: kotlin.String?)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncGetChannelPtsRemote`(`payload`: GetChannelPtsInput) : ChannelPtsView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncGetDifferenceRemote`(`payload`: GetDifferenceInput) : GetDifferenceView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncMessages`() : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncMessagesInBackground`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncState`() : SyncStateSnapshot

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `syncSubmitRemote`(`payload`: SyncSubmitInput) : SyncSubmitView

    override fun `timelineEventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    override fun `timezoneHours`(): kotlin.Int
    

    override fun `timezoneLocal`(): kotlin.String
    

    override fun `timezoneMinutes`(): kotlin.Int
    

    override fun `timezoneSeconds`(): kotlin.Int
    

    override fun `toClientEndpoint`(): kotlin.String?
    

    
    /**
     * Channel Transfer client→app RPC. Sends a wire `TransferRequest`
     * (biz_type=19) and awaits the matching `TransferResponse` (biz_type=20).
     * `timeout_ms = 0` falls back to the SDK default (5000 ms).
     * See `02-server/CHANNEL_TRANSFER_SPEC.md` v2.0 and
     * `07-application/BOT_INTERACTION_SPEC.md` for typical routes.
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `transfer`(`channelId`: kotlin.ULong, `route`: kotlin.String, `body`: kotlin.ByteArray, `timeoutMs`: kotlin.ULong) : TransferReplyView

    
    /**
     * 取消关注 Bot；server 切 status=0 但**不**删 channel / 历史 / application 业务行。
     *
     * Spec: `02-server/SERVICE_ACCOUNT_FOLLOW_SPEC` §3.2。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `unfollowBot`(`botUserId`: kotlin.ULong) : BotUnfollowResult

    
    /**
     * 取消订阅频道事件（离开聊天页面时调用）
     * channel_type: 0=Private, 1=Group, 2=Room
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `unsubscribeChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.UByte)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateDevicePushState`(`deviceId`: kotlin.String, `apnsArmed`: kotlin.Boolean, `pushToken`: kotlin.String?) : DevicePushUpdateView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateMediaDownloaded`(`messageId`: kotlin.ULong, `downloaded`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateMessageStatus`(`messageId`: kotlin.ULong, `status`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updatePrivacySettings`(`payload`: AccountPrivacyUpdateInput) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateProfile`(`payload`: ProfileUpdateInput) : ProfileView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateThumbStatus`(`messageId`: kotlin.ULong, `thumbStatus`: kotlin.Int)

    
    /**
     * 设置用户备注（本地）
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateUserAlias`(`userId`: kotlin.ULong, `alias`: kotlin.String?)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertBlacklistEntry`(`input`: UpsertBlacklistInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertChannel`(`input`: UpsertChannelInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertChannelExtra`(`input`: UpsertChannelExtraInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertChannelMember`(`input`: UpsertChannelMemberInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertFriend`(`input`: UpsertFriendInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertGroup`(`input`: UpsertGroupInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertGroupMember`(`input`: UpsertGroupMemberInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertMessageReaction`(`input`: UpsertMessageReactionInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertReminder`(`input`: UpsertReminderInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `upsertUser`(`input`: UpsertUserInput)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userId`() : kotlin.ULong?

    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/get`：读自己的永久 qr_key + URL。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeGet`() : UserQrCodeGetView

    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/refresh`：旋转自己的 qr_key。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeRefresh`() : UserQrCodeRefreshView

    
    /**
     * QR_CODE_SPEC v1.3 — `user/qrcode/resolve`：把对端 qrkey 翻译成最小用户卡片。
     * 响应**不含** qr_key（避免二次扩散）。
     */
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeResolve`(`qrKey`: kotlin.String) : UserQrCodeResolveView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userStoragePaths`() : UserStoragePaths

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `wipeCurrentUserFull`()

    

    
    
    companion object
    
}



data class AccountPrivacyUpdateInput (
    var `allowAddByGroup`: kotlin.Boolean?
         = null , 
    var `allowSearchByPhone`: kotlin.Boolean?
         = null , 
    var `allowSearchByUsername`: kotlin.Boolean?
         = null , 
    var `allowSearchByEmail`: kotlin.Boolean?
         = null , 
    var `allowSearchByQrcode`: kotlin.Boolean?
         = null , 
    var `allowViewByNonFriend`: kotlin.Boolean?
         = null , 
    var `allowReceiveMessageFromNonFriend`: kotlin.Boolean?
         = null 
) {
    
    companion object
}



data class AccountSearchResultView (
    var `users`: List<SearchUserEntry>
        , 
    var `total`: kotlin.ULong
        , 
    var `query`: kotlin.String
        
) {
    
    companion object
}



data class AccountUserDetailView (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String
        , 
    var `nickname`: kotlin.String
        , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `phone`: kotlin.String?
         = null , 
    var `email`: kotlin.String?
         = null , 
    var `userType`: kotlin.Short
        , 
    var `isFriend`: kotlin.Boolean
        , 
    var `canSendMessage`: kotlin.Boolean
        , 
    var `sourceType`: kotlin.String
        , 
    var `sourceId`: kotlin.String
        , 
    /**
     * 是否已关注（仅 user_type=2 Bot 有意义；非 bot 永远 false）
     */
    var `isFollow`: kotlin.Boolean
        
) {
    
    companion object
}



data class AccountUserShareCardView (
    var `shareKey`: kotlin.String
        , 
    var `shareUrl`: kotlin.String
        , 
    var `expireAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class AccountUserUpdateInput (
    var `displayName`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `bio`: kotlin.String?
         = null 
) {
    
    companion object
}



/**
 * 显式头像 re-cache 结果（CLIENT_GLOBAL_STATE §4.3 P2）。
 * `avatar_local_path` 是本地展示主字段；`avatar_cached_url` = 本地文件对应的远程版本（freshness 判据）。
 */
data class AvatarCacheResult (
    var `userId`: kotlin.ULong
        , 
    var `avatarLocalPath`: kotlin.String
        , 
    var `avatarCachedUrl`: kotlin.String
        
) {
    
    companion object
}



data class BatchGetChannelPtsInput (
    var `channels`: List<GetChannelPtsInput>
        
) {
    
    companion object
}



data class BatchGetChannelPtsView (
    var `channels`: List<ChannelPtsView>
        
) {
    
    companion object
}



data class BlacklistCheckResult (
    var `isBlocked`: kotlin.Boolean
        
) {
    
    companion object
}



/**
 * Bot follow 结果（spec SERVICE_ACCOUNT_FOLLOW_SPEC §2.2）。
 */
data class BotFollowResult (
    var `botUserId`: kotlin.ULong
        , 
    /**
     * 与该 bot 之间的 direct channel id；后续 Subscribe / Transfer / SendMessage 都用它。
     */
    var `channelId`: kotlin.ULong
        , 
    /**
     * v1.0 固定 2 (Bot)；保留以兼容未来扩展。
     */
    var `accountUserType`: kotlin.Int
        , 
    var `followed`: kotlin.Boolean
        , 
    /**
     * `true` = 新建关系或从 unfollowed 复活；`false` = 已 followed 幂等复用。
     */
    var `created`: kotlin.Boolean
        
) {
    
    companion object
}



/**
 * Bot unfollow 结果。
 */
data class BotUnfollowResult (
    var `botUserId`: kotlin.ULong
        , 
    /**
     * 已存在的 direct channel id（保留，**不**删除）；`0` = 原本就没关注过。
     */
    var `channelId`: kotlin.ULong
        , 
    /**
     * `true` = 已取消关注；`false` = 原本就没关注，no-op。
     */
    var `unfollowed`: kotlin.Boolean
        
) {
    
    companion object
}



data class ChannelBroadcastCreateInput (
    var `name`: kotlin.String
        , 
    var `description`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null 
) {
    
    companion object
}



data class ChannelBroadcastCreateView (
    var `status`: kotlin.String
        , 
    var `action`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelBroadcastListInput (
    var `page`: kotlin.UInt?
         = null , 
    var `pageSize`: kotlin.UInt?
         = null 
) {
    
    companion object
}



data class ChannelBroadcastListView (
    var `status`: kotlin.String
        , 
    var `action`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelBroadcastSubscribeInput (
    var `channelId`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelContentListInput (
    var `channelId`: kotlin.ULong
        , 
    var `page`: kotlin.UInt?
         = null , 
    var `pageSize`: kotlin.UInt?
         = null 
) {
    
    companion object
}



data class ChannelContentListView (
    var `status`: kotlin.String
        , 
    var `action`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelContentPublishInput (
    var `channelId`: kotlin.ULong
        , 
    var `content`: kotlin.String
        , 
    var `title`: kotlin.String?
         = null , 
    var `contentType`: kotlin.String?
         = null 
) {
    
    companion object
}



data class ChannelContentPublishView (
    var `status`: kotlin.String
        , 
    var `action`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelPtsView (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.UByte
        , 
    var `currentPts`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelSyncState (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `unread`: kotlin.Int
        
) {
    
    companion object
}



data class ConnectionSummary (
    var `state`: kotlin.String
        , 
    var `userId`: kotlin.ULong
        , 
    var `bootstrapCompleted`: kotlin.Boolean
        , 
    var `appInBackground`: kotlin.Boolean
        , 
    var `supervisedSyncRunning`: kotlin.Boolean
        , 
    var `sendQueueEnabled`: kotlin.Boolean
        , 
    var `eventPollCount`: kotlin.ULong
        , 
    var `lifecycleHookRegistered`: kotlin.Boolean
        , 
    var `transportDisconnectListenerStarted`: kotlin.Boolean
        , 
    var `onConnectionStateChangedRegistered`: kotlin.Boolean
        , 
    var `onMessageReceivedRegistered`: kotlin.Boolean
        , 
    var `onReactionChangedRegistered`: kotlin.Boolean
        , 
    var `onTypingIndicatorRegistered`: kotlin.Boolean
        , 
    var `videoProcessHookRegistered`: kotlin.Boolean
        
) {
    
    companion object
}



data class ContactCardMessageInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `fromUid`: kotlin.ULong
        , 
    var `userId`: kotlin.ULong
        , 
    var `options`: StructuredSendOptionsInput?
         = null 
) {
    
    companion object
}



data class DeviceInfoView (
    var `deviceId`: kotlin.String
        , 
    var `deviceName`: kotlin.String
        , 
    var `isCurrent`: kotlin.Boolean
        , 
    var `appInBackground`: kotlin.Boolean
        
) {
    
    companion object
}



data class DevicePushInfoView (
    var `deviceId`: kotlin.String
        , 
    var `apnsArmed`: kotlin.Boolean
        , 
    var `connected`: kotlin.Boolean
        , 
    var `platform`: kotlin.String
        , 
    var `vendor`: kotlin.String
        
) {
    
    companion object
}



data class DevicePushStatusView (
    var `devices`: List<DevicePushInfoView>
        , 
    var `userPushEnabled`: kotlin.Boolean
        
) {
    
    companion object
}



data class DevicePushUpdateView (
    var `deviceId`: kotlin.String
        , 
    var `apnsArmed`: kotlin.Boolean
        , 
    var `userPushEnabled`: kotlin.Boolean
        
) {
    
    companion object
}



data class DirectChannelResult (
    var `channelId`: kotlin.ULong
        , 
    var `created`: kotlin.Boolean
        
) {
    
    companion object
}



data class EventConfigView (
    var `broadcastCapacity`: kotlin.UInt
        , 
    var `pollingApi`: kotlin.String
        , 
    var `pollingEnvelopeApi`: kotlin.String
        , 
    var `eventPollCount`: kotlin.ULong
        , 
    var `sequenceCursor`: kotlin.ULong
        , 
    var `replayApi`: kotlin.String
        , 
    var `historyLimit`: kotlin.ULong
        
) {
    
    companion object
}



data class FileQueueRef (
    var `queueIndex`: kotlin.ULong
        , 
    var `messageId`: kotlin.ULong
        
) {
    
    companion object
}



data class FileRequestUploadTokenInput (
    var `filename`: kotlin.String?
         = null , 
    var `fileSize`: kotlin.Long
        , 
    var `mimeType`: kotlin.String
        , 
    var `fileType`: kotlin.String
        , 
    var `businessType`: kotlin.String
        
) {
    
    companion object
}



data class FileRequestUploadTokenView (
    var `token`: kotlin.String
        , 
    var `uploadUrl`: kotlin.String
        , 
    var `fileId`: kotlin.String
        
) {
    
    companion object
}



data class FileUploadCallbackInput (
    var `fileId`: kotlin.String
        , 
    var `status`: kotlin.String
        
) {
    
    companion object
}



data class FriendPendingEntry (
    var `fromUserId`: kotlin.ULong
        , 
    var `user`: SearchUserEntry
        , 
    var `message`: kotlin.String?
         = null , 
    var `createdAt`: kotlin.ULong
        
) {
    
    companion object
}



data class FriendRequestResult (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String
        , 
    var `status`: kotlin.String
        , 
    var `addedAt`: kotlin.ULong
        , 
    var `message`: kotlin.String?
         = null 
) {
    
    companion object
}



data class GetChannelPtsInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.UByte
        
) {
    
    companion object
}



data class GetDifferenceCommitView (
    var `pts`: kotlin.ULong
        , 
    var `serverMsgId`: kotlin.ULong
        , 
    var `localMessageId`: kotlin.ULong?
         = null , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.UByte
        , 
    var `messageType`: kotlin.String
        , 
    var `contentJson`: kotlin.String
        , 
    var `serverTimestamp`: kotlin.Long
        , 
    var `senderId`: kotlin.ULong
        , 
    var `senderInfoJson`: kotlin.String?
         = null 
) {
    
    companion object
}



data class GetDifferenceInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.UByte
        , 
    var `lastPts`: kotlin.ULong
        , 
    var `limit`: kotlin.UInt?
         = null 
) {
    
    companion object
}



data class GetDifferenceView (
    var `commits`: List<GetDifferenceCommitView>
        , 
    var `currentPts`: kotlin.ULong
        , 
    var `hasMore`: kotlin.Boolean
        
) {
    
    companion object
}



data class GroupApprovalItemView (
    var `requestId`: kotlin.String
        , 
    var `userId`: kotlin.ULong
        , 
    var `inviterId`: kotlin.String?
         = null , 
    var `qrCodeId`: kotlin.String?
         = null , 
    var `message`: kotlin.String?
         = null , 
    var `createdAt`: kotlin.ULong
        , 
    var `expiresAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class GroupApprovalListView (
    var `groupId`: kotlin.String
        , 
    var `approvals`: List<GroupApprovalItemView>
        , 
    var `total`: kotlin.ULong
        
) {
    
    companion object
}



data class GroupCreateResult (
    var `groupId`: kotlin.ULong
        , 
    var `name`: kotlin.String
        , 
    var `description`: kotlin.String?
         = null , 
    var `memberCount`: kotlin.UInt
        , 
    var `createdAt`: kotlin.ULong
        , 
    var `creatorId`: kotlin.ULong
        
) {
    
    companion object
}



data class GroupInfoView (
    var `groupId`: kotlin.ULong
        , 
    var `name`: kotlin.String
        , 
    var `description`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `ownerId`: kotlin.ULong
        , 
    var `createdAt`: kotlin.ULong
        , 
    var `updatedAt`: kotlin.ULong
        , 
    var `memberCount`: kotlin.UInt
        , 
    var `messageCount`: kotlin.UInt?
         = null , 
    var `isArchived`: kotlin.Boolean?
         = null , 
    var `tagsJson`: kotlin.String?
         = null , 
    var `customFieldsJson`: kotlin.String?
         = null 
) {
    
    companion object
}



data class GroupMemberRemoteEntry (
    var `userId`: kotlin.ULong
        , 
    var `role`: kotlin.Int
        , 
    var `status`: kotlin.Int
        , 
    var `alias`: kotlin.String?
         = null , 
    var `isMuted`: kotlin.Boolean
        , 
    var `joinedAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    var `rawJson`: kotlin.String
        
) {
    
    companion object
}



data class GroupMemberRemoteList (
    var `members`: List<GroupMemberRemoteEntry>
        , 
    var `total`: kotlin.ULong
        
) {
    
    companion object
}



data class GroupMuteAllView (
    var `success`: kotlin.Boolean
        , 
    var `groupId`: kotlin.String
        , 
    var `allMuted`: kotlin.Boolean
        , 
    var `message`: kotlin.String
        , 
    var `operatorId`: kotlin.String
        , 
    var `updatedAt`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * 群消息置顶/取消置顶结果
 */
data class GroupPinMessageView (
    var `success`: kotlin.Boolean
        , 
    var `groupId`: kotlin.ULong
        , 
    var `messageId`: kotlin.ULong
        , 
    var `pinned`: kotlin.Boolean
        , 
    var `pinnedAt`: kotlin.ULong?
         = null , 
    var `pinnedBy`: kotlin.ULong?
         = null 
) {
    
    companion object
}



/**
 * 单条群置顶消息
 */
data class GroupPinnedMessageView (
    var `messageId`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `pinnedBy`: kotlin.ULong
        , 
    var `pinnedAt`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `group/qrcode/get` 响应。
 */
data class GroupQrCodeGetView (
    var `qrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `groupId`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `group/join/qrcode` 响应。
 * 注意：v1.3 删除了 `expires_at` 字段（永久二维码无过期概念）。
 */
data class GroupQrCodeJoinResult (
    var `status`: kotlin.String
        , 
    var `groupId`: kotlin.ULong
        , 
    var `requestId`: kotlin.String?
         = null , 
    var `message`: kotlin.String?
         = null , 
    var `userId`: kotlin.ULong?
         = null , 
    var `joinedAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `group/qrcode/refresh` 响应。
 */
data class GroupQrCodeRefreshView (
    var `oldQrKey`: kotlin.String
        , 
    var `newQrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `groupId`: kotlin.ULong
        
) {
    
    companion object
}



data class GroupRoleSetView (
    var `groupId`: kotlin.ULong
        , 
    var `userId`: kotlin.ULong
        , 
    var `role`: kotlin.String
        , 
    var `updatedAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class GroupSettingsUpdateInput (
    var `groupId`: kotlin.ULong
        , 
    var `name`: kotlin.String?
         = null , 
    var `description`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    /**
     * 加群是否需审批（可选）
     */
    var `joinNeedApproval`: kotlin.Boolean?
         = null , 
    /**
     * 成员是否可邀请他人入群（可选）
     */
    var `memberCanInvite`: kotlin.Boolean?
         = null , 
    /**
     * 全员禁言（可选）
     */
    var `allMuted`: kotlin.Boolean?
         = null , 
    /**
     * 群成员之间是否允许私自加好友（可选，P0-5）
     */
    var `allowMemberAddFriend`: kotlin.Boolean?
         = null , 
    /**
     * 群是否允许被搜索发现（可选，P0-4）
     */
    var `allowSearch`: kotlin.Boolean?
         = null , 
    /**
     * 加入策略（可选）：0=不允许申请 1=允许申请需审核 2=允许直接加入
     */
    var `joinPolicy`: kotlin.UByte?
         = null , 
    /**
     * 成员上限（可选）
     */
    var `maxMembers`: kotlin.UInt?
         = null , 
    /**
     * 群公告（可选）
     */
    var `announcement`: kotlin.String?
         = null 
) {
    
    companion object
}



data class GroupSettingsView (
    var `groupId`: kotlin.ULong
        , 
    var `joinNeedApproval`: kotlin.Boolean
        , 
    var `memberCanInvite`: kotlin.Boolean
        , 
    /**
     * 群成员之间是否允许私自加好友（P0-5）
     */
    var `allowMemberAddFriend`: kotlin.Boolean
        , 
    /**
     * 群是否允许被搜索发现（P0-4）
     */
    var `allowSearch`: kotlin.Boolean
        , 
    /**
     * 加入策略：0=不允许申请 1=允许申请需审核 2=允许直接加入（P0-4）
     */
    var `joinPolicy`: kotlin.UByte
        , 
    var `allMuted`: kotlin.Boolean
        , 
    var `maxMembers`: kotlin.ULong
        , 
    var `announcement`: kotlin.String?
         = null , 
    var `description`: kotlin.String?
         = null , 
    var `createdAt`: kotlin.ULong
        , 
    var `updatedAt`: kotlin.ULong
        
) {
    
    companion object
}



data class GroupTransferOwnerView (
    var `groupId`: kotlin.ULong
        , 
    var `newOwnerId`: kotlin.ULong
        , 
    var `transferredAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class HttpClientConfigView (
    var `connectionTimeoutSecs`: kotlin.ULong
        , 
    var `tls`: kotlin.Boolean
        , 
    var `scheme`: kotlin.String
        
) {
    
    companion object
}



data class KeyValueEntry (
    var `key`: kotlin.String
        , 
    var `value`: kotlin.ByteArray
        
) {
    
    companion object
}



data class LinkMessageInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `fromUid`: kotlin.ULong
        , 
    var `url`: kotlin.String
        , 
    var `title`: kotlin.String?
         = null , 
    var `description`: kotlin.String?
         = null , 
    var `thumbnailFileId`: kotlin.ULong?
         = null , 
    var `options`: StructuredSendOptionsInput?
         = null 
) {
    
    companion object
}



data class LocalAccountSummary (
    var `uid`: kotlin.String
        , 
    var `createdAt`: kotlin.Long
        , 
    var `lastLoginAt`: kotlin.Long
        , 
    var `isActive`: kotlin.Boolean
        
) {
    
    companion object
}



data class LocalAttachmentMetadataInput (
    var `fileName`: kotlin.String
        , 
    var `mimeType`: kotlin.String
        , 
    var `duration`: kotlin.UInt?
         = null , 
    var `width`: kotlin.UInt?
         = null , 
    var `height`: kotlin.UInt?
         = null , 
    var `thumbnailWidth`: kotlin.UInt?
         = null , 
    var `thumbnailHeight`: kotlin.UInt?
         = null , 
    var `extensionJson`: kotlin.String?
         = null 
) {
    
    companion object
}



data class LocationMessageInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `fromUid`: kotlin.ULong
        , 
    var `latitude`: kotlin.Double
        , 
    var `longitude`: kotlin.Double
        , 
    var `coordinateSystem`: kotlin.String?
         = null , 
    var `name`: kotlin.String?
         = null , 
    var `address`: kotlin.String?
         = null , 
    var `poiId`: kotlin.String?
         = null , 
    var `poiSource`: kotlin.String?
         = null , 
    var `thumbnailFileId`: kotlin.ULong?
         = null , 
    var `options`: StructuredSendOptionsInput?
         = null 
) {
    
    companion object
}



data class LoginResult (
    var `userId`: kotlin.ULong
        , 
    var `token`: kotlin.String
        , 
    var `deviceId`: kotlin.String
        , 
    var `refreshToken`: kotlin.String?
         = null , 
    var `expiresAt`: kotlin.ULong
        
) {
    
    companion object
}



data class MediaJobResult (
    var `ok`: kotlin.Boolean
        , 
    var `outputPath`: kotlin.String?
         = null , 
    var `error`: kotlin.String?
         = null 
) {
    
    companion object
}



data class MentionInput (
    var `messageId`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `mentionedUserId`: kotlin.ULong
        , 
    var `senderId`: kotlin.ULong
        , 
    var `isMentionAll`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        
) {
    
    companion object
}



data class MessageContentBody (
    var `kind`: kotlin.String
        , 
    var `text`: kotlin.String
        , 
    var `entities`: List<MessageTextEntity>
        , 
    var `replyToMessageId`: kotlin.String?
         = null , 
    var `mentionedUserIds`: List<kotlin.ULong>
        , 
    var `attachmentUrl`: kotlin.String?
         = null , 
    var `attachmentFileId`: kotlin.ULong?
         = null , 
    var `thumbnailUrl`: kotlin.String?
         = null , 
    var `thumbnailFileId`: kotlin.ULong?
         = null , 
    var `fileName`: kotlin.String?
         = null , 
    var `fileSize`: kotlin.Long?
         = null , 
    var `duration`: kotlin.Int?
         = null , 
    var `width`: kotlin.Int?
         = null , 
    var `height`: kotlin.Int?
         = null , 
    var `latitude`: kotlin.Double?
         = null , 
    var `longitude`: kotlin.Double?
         = null , 
    var `coordinateSystem`: kotlin.String?
         = null , 
    var `locationName`: kotlin.String?
         = null , 
    var `address`: kotlin.String?
         = null , 
    var `poiId`: kotlin.String?
         = null , 
    var `poiSource`: kotlin.String?
         = null , 
    var `linkUrl`: kotlin.String?
         = null , 
    var `linkTitle`: kotlin.String?
         = null , 
    var `linkDescription`: kotlin.String?
         = null , 
    var `contactUserId`: kotlin.ULong?
         = null , 
    var `contactName`: kotlin.String?
         = null , 
    var `contactAvatarUrl`: kotlin.String?
         = null , 
    var `systemTemplate`: kotlin.String?
         = null , 
    var `systemRefs`: List<MessageContentRef>
        , 
    var `moneyRefId`: kotlin.String?
         = null , 
    var `moneyTitle`: kotlin.String?
         = null , 
    var `moneySummary`: kotlin.String?
         = null , 
    var `moneyStatus`: kotlin.String?
         = null , 
    var `moneyAmountText`: kotlin.String?
         = null , 
    var `moneyScene`: kotlin.String?
         = null , 
    var `moneyType`: kotlin.Int?
         = null 
) {
    
    companion object
}



data class MessageContentRef (
    var `kind`: kotlin.String
        , 
    var `targetId`: kotlin.String?
         = null , 
    var `text`: kotlin.String?
         = null 
) {
    
    companion object
}



data class MessageHistoryItemView (
    var `messageId`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `senderId`: kotlin.ULong
        , 
    var `content`: kotlin.String
        , 
    var `messageType`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        , 
    /**
     * per-channel pts（server message_seq）；本地排序权威 = (pts, server_message_id)
     */
    var `messageSeq`: kotlin.Long?
         = null , 
    var `replyToMessageId`: kotlin.ULong?
         = null , 
    var `metadataJson`: kotlin.String?
         = null , 
    var `revoked`: kotlin.Boolean
        , 
    var `revokedAt`: kotlin.Long?
         = null , 
    var `revokedBy`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class MessageHistoryView (
    var `messages`: List<MessageHistoryItemView>
        , 
    var `hasMore`: kotlin.Boolean
        
) {
    
    companion object
}



data class MessageReactionEmojiUsersView (
    var `emoji`: kotlin.String
        , 
    var `userIds`: List<kotlin.ULong>
        
) {
    
    companion object
}



data class MessageReactionListView (
    var `success`: kotlin.Boolean
        , 
    var `totalCount`: kotlin.ULong
        , 
    var `reactions`: List<MessageReactionEmojiUsersView>
        
) {
    
    companion object
}



data class MessageReactionStatsView (
    var `success`: kotlin.Boolean
        , 
    var `totalCount`: kotlin.ULong
        , 
    var `reactions`: List<MessageReactionEmojiUsersView>
        
) {
    
    companion object
}



data class MessageReadListView (
    var `readers`: List<MessageReadUserView>
        , 
    var `total`: kotlin.ULong
        
) {
    
    companion object
}



data class MessageReadStatsView (
    var `readCount`: kotlin.UInt
        , 
    var `totalCount`: kotlin.UInt
        
) {
    
    companion object
}



data class MessageReadUserView (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String?
         = null , 
    var `nickname`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `readAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class MessageTextEntity (
    var `kind`: kotlin.String
        , 
    var `start`: kotlin.UInt
        , 
    var `end`: kotlin.UInt
        , 
    var `text`: kotlin.String
        , 
    var `value`: kotlin.String
        , 
    var `userId`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class MessageUnreadCountView (
    var `unreadCount`: kotlin.Int
        , 
    var `channelId`: kotlin.String?
         = null 
) {
    
    companion object
}



/**
 * jump-to-message 上下文（完整消息，SDK 已回填本地库；UI 应从本地重查渲染并定位 anchor）
 */
data class MessagesAroundView (
    var `beforeMessages`: List<MessageHistoryItemView>
        , 
    var `anchorMessage`: MessageHistoryItemView
        , 
    var `afterMessages`: List<MessageHistoryItemView>
        , 
    var `hasMoreBefore`: kotlin.Boolean
        , 
    var `hasMoreAfter`: kotlin.Boolean
        
) {
    
    companion object
}



data class NewMessage (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `fromUid`: kotlin.ULong
        , 
    var `messageType`: kotlin.Int
        , 
    var `content`: kotlin.String
        , 
    var `searchableWord`: kotlin.String
        , 
    var `setting`: kotlin.Int
        , 
    var `extra`: kotlin.String
        , 
    var `mimeType`: kotlin.String?
         = null , 
    var `mediaDownloaded`: kotlin.Boolean
        , 
    var `thumbStatus`: kotlin.Int
        
) {
    
    companion object
}



data class PresenceStatsView (
    var `online`: kotlin.ULong
        , 
    var `offline`: kotlin.ULong
        , 
    var `total`: kotlin.ULong
        
) {
    
    companion object
}



data class PresenceStatus (
    var `userId`: kotlin.ULong
        , 
    var `isOnline`: kotlin.Boolean
        , 
    var `lastSeenAt`: kotlin.Long
        , 
    var `deviceCount`: kotlin.UInt
        , 
    var `version`: kotlin.ULong
        
) {
    
    companion object
}



data class PrivacySettingsView (
    var `userId`: kotlin.ULong
        , 
    var `allowAddByGroup`: kotlin.Boolean
        , 
    var `allowSearchByPhone`: kotlin.Boolean
        , 
    var `allowSearchByUsername`: kotlin.Boolean
        , 
    var `allowSearchByEmail`: kotlin.Boolean
        , 
    var `allowSearchByQrcode`: kotlin.Boolean
        , 
    var `allowViewByNonFriend`: kotlin.Boolean
        , 
    var `allowReceiveMessageFromNonFriend`: kotlin.Boolean
        , 
    var `updatedAt`: kotlin.ULong
        
) {
    
    companion object
}



data class PrivchatConfig (
    var `endpoints`: List<ServerEndpoint>
        , 
    var `connectionTimeoutSecs`: kotlin.ULong
        , 
    var `dataDir`: kotlin.String
        
) {
    
    companion object
}



data class ProfileUpdateInput (
    var `displayName`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `bio`: kotlin.String?
         = null 
) {
    
    companion object
}



data class ProfileView (
    var `status`: kotlin.String
        , 
    var `action`: kotlin.String
        , 
    var `timestamp`: kotlin.ULong
        
) {
    
    companion object
}



data class QrCodeEntryView (
    var `qrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `qrType`: kotlin.String
        , 
    var `targetId`: kotlin.String
        , 
    var `createdAt`: kotlin.ULong
        , 
    var `expireAt`: kotlin.ULong?
         = null , 
    var `usedCount`: kotlin.UInt
        , 
    var `maxUsage`: kotlin.UInt?
         = null , 
    var `revoked`: kotlin.Boolean
        
) {
    
    companion object
}



data class QrCodeGenerateView (
    var `qrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `qrType`: kotlin.String
        , 
    var `targetId`: kotlin.ULong
        , 
    var `createdAt`: kotlin.ULong
        , 
    var `expireAt`: kotlin.ULong?
         = null , 
    var `maxUsage`: kotlin.UInt?
         = null , 
    var `usedCount`: kotlin.UInt
        
) {
    
    companion object
}



data class QrCodeListView (
    var `qrKeys`: List<QrCodeEntryView>
        , 
    var `total`: kotlin.ULong
        
) {
    
    companion object
}



data class QrCodeRefreshView (
    var `oldQrKey`: kotlin.String
        , 
    var `newQrKey`: kotlin.String
        , 
    var `newQrCode`: kotlin.String
        , 
    var `revokedAt`: kotlin.ULong
        
) {
    
    companion object
}



data class QrCodeResolveView (
    var `qrType`: kotlin.String
        , 
    var `targetId`: kotlin.ULong
        , 
    var `action`: kotlin.String
        , 
    var `dataJson`: kotlin.String?
         = null , 
    var `usedCount`: kotlin.UInt
        , 
    var `maxUsage`: kotlin.UInt?
         = null , 
    var `expireAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class QrCodeRevokeView (
    var `success`: kotlin.Boolean
        , 
    var `qrKey`: kotlin.String
        , 
    var `revokedAt`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * One encoded QR matrix delivered to Kotlin/Swift.
 *
 * Wire shape: `size` modules per side, `cells` is row-major with
 * length `size * size`. Values are `0` (light) or `1` (dark).
 * Quiet zone is NOT included — UI adds the standard 4-module margin
 * as container padding.
 */
data class QrMatrixView (
    var `size`: kotlin.UInt
        , 
    var `cells`: kotlin.ByteArray
        
) {
    
    companion object
}



data class QueueConfigView (
    var `normalQueue`: kotlin.String
        , 
    var `fileQueue`: kotlin.String
        
) {
    
    companion object
}



data class QueueMessage (
    var `messageId`: kotlin.ULong
        , 
    var `payload`: kotlin.ByteArray
        
) {
    
    companion object
}



data class ReactionsBatchItemView (
    var `serverMessageId`: kotlin.ULong
        , 
    var `success`: kotlin.Boolean
        , 
    var `totalCount`: kotlin.ULong
        , 
    var `reactions`: List<MessageReactionEmojiUsersView>
        
) {
    
    companion object
}



data class ReactionsBatchView (
    var `items`: List<ReactionsBatchItemView>
        
) {
    
    companion object
}



/**
 * Input for `refresh_access_token` FFI (TOKEN_REFRESH_SPEC v1.0 §5).
 *
 * Both fields are required: caller already knows the device_id (passed at login),
 * and refresh_token is owned by the caller (SDK doesn't store it).
 */
data class RefreshAccessTokenInput (
    var `refreshToken`: kotlin.String
        , 
    var `deviceId`: kotlin.String
        
) {
    
    companion object
}



/**
 * Result of `refresh_access_token` FFI.
 *
 * `refresh_token` is `None` when the server doesn't rotate (B1 default).
 * `refresh_expires_at` is reserved for B2 rotation; B1 may not return it.
 */
data class RefreshAccessTokenResult (
    var `accessToken`: kotlin.String
        , 
    var `refreshToken`: kotlin.String?
         = null , 
    var `expiresAt`: kotlin.ULong
        , 
    var `refreshExpiresAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class RetryConfigView (
    var `messageRetry`: kotlin.Boolean
        , 
    var `strategy`: kotlin.String
        
) {
    
    companion object
}



/**
 * 搜索命中高亮区间（相对 snippet 的字符偏移 [start, end)）
 */
data class SearchHighlightRangeView (
    var `start`: kotlin.UInt
        , 
    var `end`: kotlin.UInt
        
) {
    
    companion object
}



/**
 * 云端历史搜索命中（snippet 投影——UI 展示用，**不落本地 message 表**；
 * 点击后调 get_messages_around 拿完整上下文，spec §4/§5/§6 边界）
 */
data class SearchHistoryHitView (
    var `channelId`: kotlin.ULong
        , 
    var `messageId`: kotlin.ULong
        , 
    var `senderUserId`: kotlin.ULong
        , 
    /**
     * 毫秒时间戳
     */
    var `createdAt`: kotlin.Long
        , 
    var `messageType`: kotlin.String
        , 
    var `snippet`: kotlin.String
        , 
    var `highlightRanges`: List<SearchHighlightRangeView>
        
) {
    
    companion object
}



data class SearchHistoryView (
    var `hits`: List<SearchHistoryHitView>
        , 
    /**
     * keyset 游标；None = 到底。原样回传给下一页请求
     */
    var `nextCursor`: kotlin.String?
         = null 
) {
    
    companion object
}



data class SearchUserEntry (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String
        , 
    var `nickname`: kotlin.String
        , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `userType`: kotlin.Int
        , 
    var `searchSessionId`: kotlin.ULong
        , 
    var `isFriend`: kotlin.Boolean
        , 
    var `canSendMessage`: kotlin.Boolean
        , 
    /**
     * 是否已关注（仅 user_type=2 Bot 有意义；非 bot 永远 false）
     */
    var `isFollow`: kotlin.Boolean
        
) {
    
    companion object
}



data class SeenByEntry (
    var `userId`: kotlin.ULong
        , 
    var `readAt`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class SendMessageOptionsInput (
    var `inReplyToMessageId`: kotlin.ULong?
         = null , 
    var `mentionedUserIds`: List<kotlin.ULong>
        , 
    var `silent`: kotlin.Boolean
        , 
    var `extraJson`: kotlin.String?
         = null 
) {
    
    companion object
}



data class SequencedSdkEvent (
    var `sequenceId`: kotlin.ULong
        , 
    var `timestampMs`: kotlin.Long
        , 
    var `event`: SdkEvent
        
) {
    
    companion object
}



data class ServerEndpoint (
    var `protocol`: TransportProtocol
        , 
    var `host`: kotlin.String
        , 
    var `port`: kotlin.UShort
        , 
    var `path`: kotlin.String?
         = null , 
    var `useTls`: kotlin.Boolean
        
) {
    
    companion object
}



data class SessionSnapshot (
    var `userId`: kotlin.ULong
        , 
    var `token`: kotlin.String
        , 
    var `deviceId`: kotlin.String
        , 
    var `bootstrapCompleted`: kotlin.Boolean
        
) {
    
    companion object
}



data class StickerInfoView (
    var `stickerId`: kotlin.String
        , 
    var `packageId`: kotlin.String
        , 
    var `imageUrl`: kotlin.String
        , 
    var `altText`: kotlin.String
        , 
    var `emoji`: kotlin.String?
         = null , 
    var `width`: kotlin.UInt
        , 
    var `height`: kotlin.UInt
        , 
    var `mimeType`: kotlin.String
        
) {
    
    companion object
}



data class StickerPackageDetailInput (
    var `packageId`: kotlin.String
        
) {
    
    companion object
}



data class StickerPackageDetailView (
    var `package`: StickerPackageInfoView
        
) {
    
    companion object
}



data class StickerPackageInfoView (
    var `packageId`: kotlin.String
        , 
    var `name`: kotlin.String
        , 
    var `thumbnailUrl`: kotlin.String
        , 
    var `author`: kotlin.String
        , 
    var `description`: kotlin.String
        , 
    var `stickerCount`: kotlin.ULong
        , 
    var `stickers`: List<StickerInfoView>
        
) {
    
    companion object
}



class StickerPackageListInput {
    override fun equals(other: Any?): Boolean {
        return other is StickerPackageListInput
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object
}



data class StickerPackageListView (
    var `packages`: List<StickerPackageInfoView>
        
) {
    
    companion object
}



data class StoredBlacklistEntry (
    var `blockedUserId`: kotlin.ULong
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class StoredChannel (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `channelName`: kotlin.String
        , 
    var `channelRemark`: kotlin.String
        , 
    var `avatar`: kotlin.String
        , 
    var `unreadCount`: kotlin.Int
        , 
    var `top`: kotlin.Int
        , 
    var `mute`: kotlin.Int
        , 
    var `lastMsgTimestamp`: kotlin.Long
        , 
    var `lastLocalMessageId`: kotlin.ULong
        , 
    /**
     * 最后一条消息的原始 content（TEXT = 纯文本，其他类型 = 结构化 JSON）。
     * UI 层基于 `last_message_type` + content + i18n 自行渲染预览，**SDK 不做改写**。
     */
    var `lastMsgContent`: kotlin.String
        , 
    var `lastMessageBody`: MessageContentBody?
         = null , 
    var `updatedAt`: kotlin.Long
        , 
    var `peerUserId`: kotlin.ULong?
         = null , 
    /**
     * 群成员数（群会话有意义，来自 group 实体缓存；DM/未知为 0）。供群标题「(N)」。
     */
    var `memberCount`: kotlin.UInt
        , 
    /**
     * 最后一条消息的协议 message_type（ContentMessageType 整型值）。
     */
    var `lastMessageType`: kotlin.Int?
         = null , 
    /**
     * 最后一条消息是否已撤回。
     */
    var `lastMessageIsRevoked`: kotlin.Boolean
        
) {
    
    companion object
}



data class StoredChannelExtra (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `browseTo`: kotlin.ULong
        , 
    var `keepPts`: kotlin.ULong
        , 
    var `keepOffsetY`: kotlin.Int
        , 
    var `draft`: kotlin.String
        , 
    var `draftUpdatedAt`: kotlin.ULong
        , 
    var `version`: kotlin.Long
        
) {
    
    companion object
}



data class StoredChannelMember (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `memberUid`: kotlin.ULong
        , 
    var `memberName`: kotlin.String
        , 
    var `memberRemark`: kotlin.String
        , 
    var `memberAvatar`: kotlin.String
        , 
    var `memberInviteUid`: kotlin.ULong
        , 
    var `role`: kotlin.Int
        , 
    var `status`: kotlin.Int
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `robot`: kotlin.Int
        , 
    var `version`: kotlin.Long
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    var `extra`: kotlin.String
        , 
    var `forbiddenExpirationTime`: kotlin.Long
        , 
    var `memberAvatarCacheKey`: kotlin.String
        
) {
    
    companion object
}



data class StoredFriend (
    var `userId`: kotlin.ULong
        , 
    /**
     * 用户名（来自 LEFT JOIN user，可能为空——例如 user profile 还没同步）。
     */
    var `username`: kotlin.String?
         = null , 
    /**
     * 昵称（同上）。
     */
    var `nickname`: kotlin.String?
         = null , 
    /**
     * 备注名（仅 accepted 行有意义；request 态 server 不填）。
     */
    var `alias`: kotlin.String?
         = null , 
    /**
     * 头像 URL。空串表示无头像。
     */
    var `avatar`: kotlin.String
        , 
    /**
     * AVATAR_CACHE_SPEC P1: 头像本地缓存文件绝对路径（已验证文件存在）；
     * 空串 = 未缓存，回落 avatar（网络 URL）。
     */
    var `avatarLocalPath`: kotlin.String
        , 
    var `tags`: kotlin.String?
         = null , 
    var `isPinned`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    /**
     * F-sync.2: 0=pending / 1=accepted / 2=blocked / 3=rejected / 4=recalled / 5=expired.
     * `list_friends` 只返 status=1；其它态从 `list_friend_requests` 拿。
     */
    var `status`: kotlin.Short
        , 
    /**
     * 申请态下 viewer 是不是 requester：true=我发出的，false=我收到的；accepted=null。
     */
    var `isOutgoing`: kotlin.Boolean?
         = null , 
    var `requestMessage`: kotlin.String?
         = null , 
    var `requestSource`: kotlin.String?
         = null , 
    var `requestSourceId`: kotlin.String?
         = null 
) {
    
    companion object
}



data class StoredGroup (
    var `groupId`: kotlin.ULong
        , 
    var `name`: kotlin.String?
         = null , 
    var `avatar`: kotlin.String
        , 
    var `ownerId`: kotlin.ULong?
         = null , 
    var `isDismissed`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class StoredGroupMember (
    var `groupId`: kotlin.ULong
        , 
    var `userId`: kotlin.ULong
        , 
    var `role`: kotlin.Int
        , 
    var `status`: kotlin.Int
        , 
    var `alias`: kotlin.String?
         = null , 
    var `isMuted`: kotlin.Boolean
        , 
    var `joinedAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class StoredMessage (
    var `messageId`: kotlin.ULong
        , 
    var `serverMessageId`: kotlin.ULong?
         = null , 
    var `localMessageId`: kotlin.ULong?
         = null , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `fromUid`: kotlin.ULong
        , 
    var `messageType`: kotlin.Int
        , 
    var `content`: kotlin.String
        , 
    /**
     * SDK-owned typed projection. Application/UI code consumes this field.
     */
    var `body`: MessageContentBody
        , 
    var `status`: kotlin.Int
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    var `extra`: kotlin.String
        , 
    var `revoked`: kotlin.Boolean
        , 
    var `revokedBy`: kotlin.ULong?
         = null , 
    var `mimeType`: kotlin.String?
         = null , 
    var `mediaDownloaded`: kotlin.Boolean
        , 
    var `thumbStatus`: kotlin.Int
        , 
    var `delivered`: kotlin.Boolean
        , 
    var `pts`: kotlin.ULong?
         = null , 
    /**
     * 引用消息的 server_message_id（envelope.reply_to_message_id）
     */
    var `replyToMessageId`: kotlin.String?
         = null , 
    /**
     * @ 提及的用户 ID 列表（envelope.mentioned_user_ids）
     */
    var `mentionedUserIds`: List<kotlin.ULong>
        
) {
    
    companion object
}



data class StoredMessageExtra (
    var `messageId`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `readed`: kotlin.Int
        , 
    var `readedCount`: kotlin.Int
        , 
    var `unreadCount`: kotlin.Int
        , 
    var `revoke`: kotlin.Boolean
        , 
    var `revoker`: kotlin.ULong?
         = null , 
    var `extraVersion`: kotlin.Long
        , 
    var `isMutualDeleted`: kotlin.Boolean
        , 
    var `contentEdit`: kotlin.String?
         = null , 
    var `editedAt`: kotlin.Int
        , 
    var `needUpload`: kotlin.Boolean
        , 
    var `isPinned`: kotlin.Boolean
        , 
    var `delivered`: kotlin.Boolean
        , 
    var `deliveredAt`: kotlin.ULong
        
) {
    
    companion object
}



data class StoredMessageReaction (
    var `id`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `uid`: kotlin.ULong
        , 
    var `name`: kotlin.String
        , 
    var `emoji`: kotlin.String
        , 
    var `messageId`: kotlin.ULong
        , 
    var `seq`: kotlin.Long
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        
) {
    
    companion object
}



data class StoredReminder (
    var `id`: kotlin.ULong
        , 
    var `reminderId`: kotlin.ULong
        , 
    var `messageId`: kotlin.ULong
        , 
    var `pts`: kotlin.Long
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `uid`: kotlin.ULong
        , 
    var `reminderType`: kotlin.Int
        , 
    var `text`: kotlin.String
        , 
    var `data`: kotlin.String
        , 
    var `isLocate`: kotlin.Boolean
        , 
    var `version`: kotlin.Long
        , 
    var `done`: kotlin.Boolean
        , 
    var `needUpload`: kotlin.Boolean
        , 
    var `publisher`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class StoredUser (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String?
         = null , 
    var `nickname`: kotlin.String?
         = null , 
    var `alias`: kotlin.String?
         = null , 
    var `avatar`: kotlin.String
        , 
    /**
     * AVATAR_CACHE_SPEC P1: 头像本地缓存文件绝对路径（已验证文件存在）；
     * 空串 = 未缓存，回落 avatar（网络 URL）。
     */
    var `avatarLocalPath`: kotlin.String
        , 
    var `userType`: kotlin.Int
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `channelId`: kotlin.String
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class StructuredSendOptionsInput (
    var `inReplyToMessageId`: kotlin.ULong?
         = null , 
    var `mentionedUserIds`: List<kotlin.ULong>
        
) {
    
    companion object
}



data class SyncEntitiesInput (
    var `entityType`: kotlin.String
        , 
    var `sinceVersion`: kotlin.ULong?
         = null , 
    var `scope`: kotlin.String?
         = null , 
    var `limit`: kotlin.UInt?
         = null 
) {
    
    companion object
}



data class SyncEntitiesView (
    var `items`: List<SyncEntityItemView>
        , 
    var `nextVersion`: kotlin.ULong
        , 
    var `hasMore`: kotlin.Boolean
        , 
    var `minVersion`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class SyncEntityItemView (
    var `entityId`: kotlin.String
        , 
    var `version`: kotlin.ULong
        , 
    var `deleted`: kotlin.Boolean
        , 
    var `payloadJson`: kotlin.String?
         = null 
) {
    
    companion object
}



data class SyncPayloadEntry (
    var `key`: kotlin.String
        , 
    var `value`: kotlin.String
        
) {
    
    companion object
}



data class SyncStateSnapshot (
    var `phase`: SyncPhase
        , 
    var `runKind`: SyncRunKind?
         = null , 
    var `attempt`: kotlin.UInt
        , 
    var `errorCode`: kotlin.UInt?
         = null , 
    var `message`: kotlin.String?
         = null , 
    var `updatedAtMs`: kotlin.Long
        
) {
    
    companion object
}



data class SyncSubmitInput (
    var `localMessageId`: kotlin.ULong
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.UByte
        , 
    var `lastPts`: kotlin.ULong
        , 
    var `commandType`: kotlin.String
        , 
    var `payloadEntries`: List<SyncPayloadEntry>
        , 
    var `clientTimestamp`: kotlin.Long
        , 
    var `deviceId`: kotlin.String?
         = null 
) {
    
    companion object
}



data class SyncSubmitView (
    var `decision`: kotlin.String
        , 
    var `decisionReason`: kotlin.String?
         = null , 
    var `pts`: kotlin.ULong?
         = null , 
    var `serverMsgId`: kotlin.ULong?
         = null , 
    var `serverTimestamp`: kotlin.Long
        , 
    var `localMessageId`: kotlin.ULong
        , 
    var `hasGap`: kotlin.Boolean
        , 
    var `currentPts`: kotlin.ULong
        
) {
    
    companion object
}



data class TerminalReason (
    /**
     * `privchat_protocol::ErrorCode` 对应的 u32 码；未携带时为 0。
     */
    var `code`: kotlin.UInt
        , 
    var `message`: kotlin.String
        , 
    var `source`: ForcedLogoutSource
        , 
    var `atMs`: kotlin.Long
        
) {
    
    companion object
}



/**
 * Channel Transfer client→app reply (decoded from wire `TransferResponse`).
 * See `02-server/CHANNEL_TRANSFER_SPEC.md` v2.0.
 */
data class TransferReplyView (
    var `requestId`: kotlin.String
        , 
    var `channelId`: kotlin.ULong
        , 
    var `code`: kotlin.Int
        , 
    var `message`: kotlin.String
        , 
    var `data`: kotlin.ByteArray
        
) {
    
    companion object
}



data class TypingChannelView (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        
) {
    
    companion object
}



data class TypingStatsView (
    var `typing`: kotlin.ULong
        , 
    var `activeChannels`: List<TypingChannelView>
        , 
    var `startedCount`: kotlin.ULong
        , 
    var `stoppedCount`: kotlin.ULong
        
) {
    
    companion object
}



data class UnreadMentionCount (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `unreadCount`: kotlin.Int
        
) {
    
    companion object
}



data class UpsertBlacklistInput (
    var `blockedUserId`: kotlin.ULong
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class UpsertChannelExtraInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `browseTo`: kotlin.ULong
        , 
    var `keepPts`: kotlin.ULong
        , 
    var `keepOffsetY`: kotlin.Int
        , 
    var `draft`: kotlin.String
        , 
    var `draftUpdatedAt`: kotlin.ULong
        
) {
    
    companion object
}



data class UpsertChannelInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `channelName`: kotlin.String
        , 
    var `channelRemark`: kotlin.String
        , 
    var `avatar`: kotlin.String
        , 
    var `unreadCount`: kotlin.Int
        , 
    var `top`: kotlin.Int
        , 
    var `mute`: kotlin.Int
        , 
    var `lastMsgTimestamp`: kotlin.Long
        , 
    var `lastLocalMessageId`: kotlin.ULong
        , 
    var `lastMsgContent`: kotlin.String
        
) {
    
    companion object
}



data class UpsertChannelMemberInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `memberUid`: kotlin.ULong
        , 
    var `memberName`: kotlin.String
        , 
    var `memberRemark`: kotlin.String
        , 
    var `memberAvatar`: kotlin.String
        , 
    var `memberInviteUid`: kotlin.ULong
        , 
    var `role`: kotlin.Int
        , 
    var `status`: kotlin.Int
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `robot`: kotlin.Int
        , 
    var `version`: kotlin.Long
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    var `extra`: kotlin.String
        , 
    var `forbiddenExpirationTime`: kotlin.Long
        , 
    var `memberAvatarCacheKey`: kotlin.String
        
) {
    
    companion object
}



data class UpsertFriendInput (
    var `userId`: kotlin.ULong
        , 
    var `tags`: kotlin.String?
         = null , 
    var `isPinned`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class UpsertGroupInput (
    var `groupId`: kotlin.ULong
        , 
    var `name`: kotlin.String?
         = null , 
    var `avatar`: kotlin.String
        , 
    var `ownerId`: kotlin.ULong?
         = null , 
    var `isDismissed`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class UpsertGroupMemberInput (
    var `groupId`: kotlin.ULong
        , 
    var `userId`: kotlin.ULong
        , 
    var `role`: kotlin.Int
        , 
    var `status`: kotlin.Int
        , 
    var `alias`: kotlin.String?
         = null , 
    var `isMuted`: kotlin.Boolean
        , 
    var `joinedAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



data class UpsertMessageReactionInput (
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `uid`: kotlin.ULong
        , 
    var `name`: kotlin.String
        , 
    var `emoji`: kotlin.String
        , 
    var `messageId`: kotlin.ULong
        , 
    var `seq`: kotlin.Long
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `createdAt`: kotlin.Long
        
) {
    
    companion object
}



data class UpsertReminderInput (
    var `reminderId`: kotlin.ULong
        , 
    var `messageId`: kotlin.ULong
        , 
    var `pts`: kotlin.Long
        , 
    var `channelId`: kotlin.ULong
        , 
    var `channelType`: kotlin.Int
        , 
    var `uid`: kotlin.ULong
        , 
    var `reminderType`: kotlin.Int
        , 
    var `text`: kotlin.String
        , 
    var `data`: kotlin.String
        , 
    var `isLocate`: kotlin.Boolean
        , 
    var `version`: kotlin.Long
        , 
    var `done`: kotlin.Boolean
        , 
    var `needUpload`: kotlin.Boolean
        , 
    var `publisher`: kotlin.ULong?
         = null 
) {
    
    companion object
}



data class UpsertUserInput (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String?
         = null , 
    var `nickname`: kotlin.String?
         = null , 
    var `alias`: kotlin.String?
         = null , 
    var `avatar`: kotlin.String
        , 
    var `userType`: kotlin.Int
        , 
    var `isDeleted`: kotlin.Boolean
        , 
    var `channelId`: kotlin.String
        , 
    var `updatedAt`: kotlin.Long
        
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `user/qrcode/get` 响应。
 */
data class UserQrCodeGetView (
    var `qrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `userId`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `user/qrcode/refresh` 响应。
 */
data class UserQrCodeRefreshView (
    var `oldQrKey`: kotlin.String
        , 
    var `newQrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `userId`: kotlin.ULong
        
) {
    
    companion object
}



/**
 * QR_CODE_SPEC v1.3 — `user/qrcode/resolve` 响应（最小用户卡片，**不含** qr_key）。
 */
data class UserQrCodeResolveView (
    var `userId`: kotlin.ULong
        , 
    var `username`: kotlin.String
        , 
    var `displayName`: kotlin.String?
         = null , 
    var `avatarUrl`: kotlin.String?
         = null , 
    var `userType`: kotlin.Short
        , 
    var `isFriend`: kotlin.Boolean
        , 
    var `isSelf`: kotlin.Boolean
        
) {
    
    companion object
}



data class UserStoragePaths (
    var `userRoot`: kotlin.String
        , 
    var `dbPath`: kotlin.String
        , 
    var `kvPath`: kotlin.String
        , 
    var `queueRoot`: kotlin.String
        , 
    var `normalQueuePath`: kotlin.String
        , 
    var `fileQueuePaths`: List<kotlin.String>
        , 
    var `mediaRoot`: kotlin.String
        
) {
    
    companion object
}





enum class ConnectionState {
    
    NEW,
    CONNECTED,
    LOGGED_IN,
    AUTHENTICATED,
    /**
     * 服务端判定本次登录态不可自愈（token 过期/撤销/设备不匹配）。
     * SDK 已断开并停止自动重连；UI 必须重新登录才能回到 New。
     */
    TERMINATED,
    SHUTDOWN;
    companion object
}







enum class ForcedLogoutSource {
    
    CONNECT_AUTH,
    RPC_AUTH,
    MANUAL;
    companion object
}






sealed class MediaDownloadState {
    
    @kotlinx.serialization.Serializable
    object Idle : MediaDownloadState() 
    
    
    
    data class Downloading(
        val `bytes`: kotlin.ULong  , 
        val `total`: kotlin.ULong?  = null  ) : MediaDownloadState() {
        
    }
    
    
    data class Paused(
        val `bytes`: kotlin.ULong  , 
        val `total`: kotlin.ULong?  = null  ) : MediaDownloadState() {
        
    }
    
    
    data class Done(
        val `path`: kotlin.String  ) : MediaDownloadState() {
        
    }
    
    
    data class Failed(
        val `code`: kotlin.UInt  , 
        val `message`: kotlin.String  ) : MediaDownloadState() {
        
    }
    
}







enum class MediaProcessOp {
    
    THUMBNAIL,
    COMPRESS;
    companion object
}







enum class NetworkHint {
    
    UNKNOWN,
    OFFLINE,
    WIFI,
    CELLULAR,
    ETHERNET;
    companion object
}







sealed class PrivchatFfiException: kotlin.Exception() {
    
    class SdkException(
        
        val `code`: kotlin.UInt, 
        
        val `detail`: kotlin.String
        ) : PrivchatFfiException() {
        override val message
            get() = "code=${ `code` }, detail=${ `detail` }"

        
    }
    
}





/**
 * Errors surfaced through UniFFI to Kotlin / Swift callers of
 * [`qr_decode_luma`]. See `crate::qr::QrDecodeError`.
 */
sealed class QrDecodeException: kotlin.Exception() {
    
    class InvalidDimensions(
        
        val `width`: kotlin.UInt, 
        
        val `height`: kotlin.UInt, 
        
        val `lumaLen`: kotlin.UInt
        ) : QrDecodeException() {
        override val message
            get() = "width=${ `width` }, height=${ `height` }, lumaLen=${ `lumaLen` }"

        
    }
    
    class DecoderException(
        
        val `detail`: kotlin.String
        ) : QrDecodeException() {
        override val message
            get() = "detail=${ `detail` }"

        
    }
    
}





/**
 * Errors surfaced through UniFFI to Kotlin / Swift callers of
 * [`qr_encode_matrix`]. See `crate::qr::QrEncodeError`.
 */
sealed class QrEncodeException: kotlin.Exception() {
    
    class EmptyText(
        ) : QrEncodeException() {
        override val message
            get() = ""

        
    }
    
    class EncoderException(
        
        val `detail`: kotlin.String
        ) : QrEncodeException() {
        override val message
            get() = "detail=${ `detail` }"

        
    }
    
}





enum class ResumeEscalationScope {
    
    RETRY,
    CHANNEL_SCOPED_RESYNC,
    ENTITY_SCOPED_RESYNC,
    FULL_REBUILD;
    companion object
}







enum class ResumeFailureClass {
    
    RETRYABLE_TEMPORARY_ERROR,
    CHANNEL_RESYNC_REQUIRED,
    ENTITY_RESYNC_REQUIRED,
    FULL_REBUILD_REQUIRED,
    FATAL_PROTOCOL_ERROR;
    companion object
}






sealed class SdkEvent {
    
    
    data class ConnectionStateChanged(
        val `from`: ConnectionState  , 
        val `to`: ConnectionState  ) : SdkEvent() {
        
    }
    
    
    data class BootstrapCompleted(
        val `userId`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class SyncStateChanged(
        val `state`: SyncStateSnapshot  ) : SdkEvent() {
        
    }
    
    @kotlinx.serialization.Serializable
    object ResumeSyncStarted : SdkEvent() 
    
    
    
    data class ResumeSyncCompleted(
        val `entityTypesSynced`: kotlin.ULong  , 
        val `channelsScanned`: kotlin.ULong  , 
        val `channelsApplied`: kotlin.ULong  , 
        val `channelFailures`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class ResumeSyncFailed(
        val `classification`: ResumeFailureClass  , 
        val `scope`: ResumeEscalationScope  , 
        val `errorCode`: kotlin.UInt  , 
        val `message`: kotlin.String  ) : SdkEvent() {
        
    }
    
    
    data class ResumeSyncEscalated(
        val `classification`: ResumeFailureClass  , 
        val `scope`: ResumeEscalationScope  , 
        val `reason`: kotlin.String  , 
        val `entityType`: kotlin.String?  = null  , 
        val `channelId`: kotlin.ULong?  = null  , 
        val `channelType`: kotlin.Int?  = null  ) : SdkEvent() {
        
    }
    
    
    data class ResumeSyncChannelStarted(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  ) : SdkEvent() {
        
    }
    
    
    data class ResumeSyncChannelCompleted(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `applied`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class ResumeSyncChannelFailed(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `classification`: ResumeFailureClass  , 
        val `scope`: ResumeEscalationScope  , 
        val `errorCode`: kotlin.UInt  , 
        val `message`: kotlin.String  ) : SdkEvent() {
        
    }
    
    
    data class SyncEntitiesApplied(
        val `entityType`: kotlin.String  , 
        val `scope`: kotlin.String?  = null  , 
        val `queued`: kotlin.ULong  , 
        val `applied`: kotlin.ULong  , 
        val `droppedDuplicates`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class SyncEntityChanged(
        val `entityType`: kotlin.String  , 
        val `entityId`: kotlin.String  , 
        val `deleted`: kotlin.Boolean  ) : SdkEvent() {
        
    }
    
    
    data class SyncChannelApplied(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `applied`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class SyncAllChannelsApplied(
        val `applied`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class NetworkHintChanged(
        val `from`: NetworkHint  , 
        val `to`: NetworkHint  ) : SdkEvent() {
        
    }
    
    
    data class OutboundQueueUpdated(
        val `kind`: kotlin.String  , 
        val `action`: kotlin.String  , 
        val `messageId`: kotlin.ULong?  = null  , 
        val `queueIndex`: kotlin.ULong?  = null  ) : SdkEvent() {
        
    }
    
    
    data class TimelineUpdated(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `messageId`: kotlin.ULong  , 
        val `reason`: kotlin.String  ) : SdkEvent() {
        
    }
    
    
    data class MessageSendStatusChanged(
        val `messageId`: kotlin.ULong  , 
        val `status`: kotlin.Int  , 
        val `serverMessageId`: kotlin.ULong?  = null  ) : SdkEvent() {
        
    }
    
    
    data class TypingSent(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `isTyping`: kotlin.Boolean  ) : SdkEvent() {
        
    }
    
    
    data class SubscriptionMessageReceived(
        val `channelId`: kotlin.ULong  , 
        val `topic`: kotlin.String?  = null  , 
        val `payload`: kotlin.ByteArray  , 
        val `publisher`: kotlin.String?  = null  , 
        val `serverMessageId`: kotlin.ULong?  = null  , 
        val `timestamp`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class PeerReadPtsAdvanced(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `readerId`: kotlin.ULong  , 
        val `readPts`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class MessageDelivered(
        val `channelId`: kotlin.ULong  , 
        val `channelType`: kotlin.Int  , 
        val `messageId`: kotlin.ULong  , 
        val `serverMessageId`: kotlin.ULong  , 
        val `deliveredAt`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    
    data class MediaDownloadStateChanged(
        val `messageId`: kotlin.ULong  , 
        val `state`: MediaDownloadState  ) : SdkEvent() {
        
    }
    
    
    data class MediaJobRequested(
        val `jobId`: kotlin.String  , 
        val `jobKind`: kotlin.String  , 
        val `sourcePath`: kotlin.String  , 
        val `outputPath`: kotlin.String  , 
        val `mimeType`: kotlin.String  , 
        val `messageId`: kotlin.ULong  , 
        val `timeoutMs`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    /**
     * Access token 已由 SDK 自动续期成功。不携带 token 内容——宿主如需使用，
     * 请调用 `PrivchatSdk::get_current_access_token()` 主动拉取。
     */
    
    data class TokenRefreshed(
        /**
         * 新 access_token 过期时间（Unix 毫秒，服务端下发）。
         */
        val `expiresAt`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    /**
     * auto-reconnect 握手撞到 Recoverable auth 错（典型 10002）；SDK 已暂停 auto-reconnect。
     * 业务层应调用自家 mode-aware refresh 入口（详见 TOKEN_REFRESH_SPEC §3.1）。
     */
    
    data class AccessTokenRefreshNeeded(
        /**
         * 服务端原始错误码，典型 10002。
         */
        val `code`: kotlin.UInt  , 
        /**
         * 服务端原始 message，仅作日志/审计。
         */
        val `message`: kotlin.String  ) : SdkEvent() {
        
    }
    
    
    data class ForcedLogout(
        /**
         * `privchat_protocol::ErrorCode` 对应的 u32 码；未携带时为 0。
         */
        val `code`: kotlin.UInt  , 
        val `message`: kotlin.String  , 
        val `source`: ForcedLogoutSource  ) : SdkEvent() {
        
    }
    
    @kotlinx.serialization.Serializable
    object ShutdownStarted : SdkEvent() 
    
    
    @kotlinx.serialization.Serializable
    object ShutdownCompleted : SdkEvent() 
    
    
}







enum class SyncPhase {
    
    IDLE,
    SYNCING,
    SYNCED,
    RETRYING,
    FAILED_TERMINAL;
    companion object
}







enum class SyncRunKind {
    
    BOOTSTRAP,
    RESUME;
    companion object
}







enum class TransportProtocol {
    
    QUIC,
    TCP,
    WEB_SOCKET;
    companion object
}







enum class TypingActionType {
    
    TYPING,
    RECORDING,
    UPLOADING_PHOTO,
    UPLOADING_VIDEO,
    UPLOADING_FILE,
    CHOOSING_STICKER;
    companion object
}







interface VideoProcessHook {
    
        @Throws(PrivchatFfiException::class)fun `process`(`op`: MediaProcessOp, `sourcePath`: kotlin.String, `metaPath`: kotlin.String, `outputPath`: kotlin.String): kotlin.Boolean
    
    companion object
}






















































































































































expect fun `buildTime`(): kotlin.String
    

expect fun `gitSha`(): kotlin.String
    


        /**
         * Decode a QR code from an 8-bit grayscale image (Y plane of a YUV
         * camera frame, or `0.299*R + 0.587*G + 0.114*B` of an RGB photo).
         *
         * `luma` must be exactly `width * height` bytes, row-major.
         *
         * - `Ok(Some(text))` — QR found
         * - `Ok(None)`       — no QR in this frame (steady-state during live scan)
         * - `Err(InvalidDimensions)` — caller-side dimensions / length mismatch
         * - `Err(DecoderError)` — rxing internal failure (rare)
         */
    @Throws(QrDecodeException::class)expect fun `qrDecodeLuma`(`width`: kotlin.UInt, `height`: kotlin.UInt, `luma`: kotlin.ByteArray): kotlin.String?
    


        /**
         * Encode `text` to a QR matrix at error-correction level **M**
         * (~15% redundancy, sensible for permanent name-card / group URLs).
         *
         * - `Ok(QrMatrixView)` — success
         * - `Err(EmptyText)`   — caller passed empty / whitespace-only text
         * - `Err(EncoderError)` — payload too long / unsupported charset / etc.
         */
    @Throws(QrEncodeException::class)expect fun `qrEncodeMatrix`(`text`: kotlin.String): QrMatrixView
    

expect fun `sdkVersion`(): kotlin.String
    


