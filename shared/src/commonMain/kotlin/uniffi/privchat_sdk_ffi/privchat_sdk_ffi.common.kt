

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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `authRefreshRemote`(`payload`: AuthRefreshInput): LoginResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `authenticate`(`userId`: kotlin.ULong, `token`: kotlin.String, `deviceId`: kotlin.String)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `batchGetPresence`(`userIds`: List<kotlin.ULong>): List<PresenceStatus>
    fun `build`(): kotlin.String
    fun `builder`(): kotlin.String
    
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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `createLocalMessage`(`input`: NewMessage): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `currentUserId`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `dataDir`(): kotlin.String
    fun `debugMode`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteBlacklistEntry`(`blockedUserId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `deleteFriend`(`friendId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `disconnect`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `dmPeerUserId`(`channelId`: kotlin.ULong): kotlin.ULong?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `downloadAttachmentToCache`(`sourcePath`: kotlin.String, `fileName`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `downloadAttachmentToMessageDir`(`sourcePath`: kotlin.String, `messageId`: kotlin.ULong, `fileName`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `downloadAttachmentToPath`(`sourcePath`: kotlin.String, `targetPath`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `editMessage`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `editMessageBlocking`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueOutboundFile`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueOutboundMessage`(`messageId`: kotlin.ULong, `payload`: kotlin.ByteArray): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `enqueueText`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String): kotlin.ULong
    fun `enterBackground`()
    fun `enterForeground`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `entitySyncRemote`(`payload`: SyncEntitiesInput): SyncEntitiesView
    fun `eventConfig`(): EventConfigView
    fun `eventStreamCursor`(): kotlin.ULong
    fun `eventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fetchGroupMembersRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?): GroupMemberRemoteList
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fetchPresence`(`userIds`: List<kotlin.ULong>): List<PresenceStatus>
    fun `fileApiBaseUrl`(): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fileRequestUploadTokenRemote`(`payload`: FileRequestUploadTokenInput): FileRequestUploadTokenView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `fileUploadCallbackRemote`(`payload`: FileUploadCallbackInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getAllUnreadMentionCounts`(`userId`: kotlin.ULong): List<UnreadMentionCount>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getAllUserSettings`(): UserSettingsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getBlacklist`(): List<StoredBlacklistEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelById`(`channelId`: kotlin.ULong): StoredChannel?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelExtra`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): StoredChannelExtra?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelListEntries`(`page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelSyncState`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): ChannelSyncState
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannelUnreadCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getConnectionState`(): ConnectionState
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getConnectionSummary`(): ConnectionSummary
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getDevicePushStatus`(`deviceId`: kotlin.String?): DevicePushStatusView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getEarliestId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.ULong?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getFriendPendingRequests`(): List<FriendPendingEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredFriend>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupById`(`groupId`: kotlin.ULong): StoredGroup?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupInfo`(`groupId`: kotlin.ULong): GroupInfoView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroupMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroup>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessageById`(`messageId`: kotlin.ULong): StoredMessage?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessageExtra`(`messageId`: kotlin.ULong): StoredMessageExtra?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getMessagesRemote`(`channelId`: kotlin.ULong, `beforeServerMessageId`: kotlin.ULong?, `limit`: kotlin.UInt?): MessageHistoryView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getOrCreateDirectChannel`(`peerUserId`: kotlin.ULong): DirectChannelResult
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPresence`(`userId`: kotlin.ULong): PresenceStatus?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPresenceStats`(): PresenceStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getPrivacySettings`(): PrivacySettingsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getProfile`(): ProfileView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getTotalUnreadCount`(`excludeMuted`: kotlin.Boolean): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getTypingStats`(): TypingStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getUnreadMentionCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getUserById`(`userId`: kotlin.ULong): StoredUser?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `getUserSetting`(`key`: kotlin.String): kotlin.String?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupAddMembersRemote`(`groupId`: kotlin.ULong, `userIds`: List<kotlin.ULong>): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupApprovalHandleRemote`(`approvalId`: kotlin.ULong, `approved`: kotlin.Boolean, `reason`: kotlin.String?): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupApprovalListRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?): GroupApprovalListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupGetSettingsRemote`(`groupId`: kotlin.ULong): GroupSettingsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupLeaveRemote`(`groupId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupMuteAllRemote`(`groupId`: kotlin.ULong, `enabled`: kotlin.Boolean): GroupMuteAllView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupMuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `durationSeconds`: kotlin.ULong?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupQrcodeGenerateRemote`(`groupId`: kotlin.ULong, `expireSeconds`: kotlin.ULong?): GroupQrCodeGenerateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `groupQrcodeJoinRemote`(`qrKey`: kotlin.String, `token`: kotlin.String?): GroupQrCodeJoinResult
    
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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `kvGet`(`key`: kotlin.String): kotlin.ByteArray?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `kvPut`(`key`: kotlin.String, `value`: kotlin.ByteArray)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `leaveChannel`(`channelId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `leaveGroup`(`groupId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listBlacklistEntries`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredBlacklistEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannelMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredFriend>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroupMember>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `listGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong): List<StoredGroup>
    
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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markAsRead`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markAsReadBlocking`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markChannelRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markFullyReadAt`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markMentionRead`(`messageId`: kotlin.ULong, `userId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markMessageSent`(`messageId`: kotlin.ULong, `serverMessageId`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `markReminderDone`(`reminderId`: kotlin.ULong, `done`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageReadList`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): MessageReadListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageReadStats`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): MessageReadStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `messageUnreadCountRemote`(`channelId`: kotlin.ULong): MessageUnreadCountView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `muteChannel`(`channelId`: kotlin.ULong, `muted`: kotlin.Boolean): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `needsSync`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextEvent`(`timeoutMs`: kotlin.ULong): SdkEvent?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `nextEventEnvelope`(`timeoutMs`: kotlin.ULong): SequencedSdkEvent?
    fun `onAppBackground`()
    fun `onAppForeground`()
    fun `onConnectionStateChanged`()
    fun `onMessageReceived`()
    fun `onReactionChanged`()
    fun `onTypingIndicator`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ownLastRead`(`channelId`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `paginateBack`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `paginateForward`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `peekOutboundFiles`(`queueIndex`: kotlin.ULong, `limit`: kotlin.ULong): List<QueueMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `peekOutboundMessages`(`limit`: kotlin.ULong): List<QueueMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `pinChannel`(`channelId`: kotlin.ULong, `pinned`: kotlin.Boolean): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `ping`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeGenerate`(`qrType`: kotlin.String, `payload`: kotlin.String, `expireSeconds`: kotlin.ULong?): QrCodeGenerateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeList`(`qrType`: kotlin.String?): QrCodeJsonView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeRefresh`(`qrKey`: kotlin.String, `expireSeconds`: kotlin.ULong?): QrCodeJsonView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeResolve`(`qrKey`: kotlin.String, `token`: kotlin.String?): QrCodeResolveView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `qrcodeRevoke`(`qrKey`: kotlin.String): QrCodeJsonView
    fun `queueConfig`(): QueueConfigView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactionStats`(`serverMessageId`: kotlin.ULong): MessageReactionStatsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactions`(`serverMessageId`: kotlin.ULong): MessageReactionListView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `reactionsBatch`(`serverMessageIds`: List<kotlin.ULong>): ReactionsBatchView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recallMessage`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recallMessageBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong): kotlin.Boolean
    fun `recentEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `recordMention`(`input`: MentionInput): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `register`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String): LoginResult
    fun `registerLifecycleHook`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `rejectFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeFromBlacklist`(`blockedUserId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeGroupMember`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `removeReaction`(`serverMessageId`: kotlin.ULong, `emoji`: kotlin.String): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `resolveChannelIdByServerMessageId`(`serverMessageId`: kotlin.ULong): kotlin.ULong
    suspend fun `resolveChannelType`(`channelId`: kotlin.ULong): kotlin.Int
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `resolveLocalMessageIdByServerMessageId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `serverMessageId`: kotlin.ULong): kotlin.ULong?
    fun `retryConfig`(): RetryConfigView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `retryMessage`(`messageId`: kotlin.ULong): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `rpcCall`(`route`: kotlin.String, `bodyJson`: kotlin.String): kotlin.String
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `runBootstrapSync`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchChannel`(`keyword`: kotlin.String): List<StoredChannel>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `keyword`: kotlin.String): List<StoredMessage>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchUserByQrcode`(`qrKey`: kotlin.String, `token`: kotlin.String?): AccountSearchResultView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `searchUsers`(`query`: kotlin.String): List<SearchUserEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `seenByForEvent`(`serverMessageId`: kotlin.ULong): List<SeenByEntry>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendAttachmentBytes`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendAttachmentFromPath`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `path`: kotlin.String): FileQueueRef
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `sendFriendRequest`(`targetUserId`: kotlin.ULong, `message`: kotlin.String?, `source`: kotlin.String?, `sourceId`: kotlin.String?): FriendRequestResult
    
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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelLowPriority`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setChannelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `mode`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setMessagePinned`(`messageId`: kotlin.ULong, `isPinned`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setMessageRead`(`messageId`: kotlin.ULong, `channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isRead`: kotlin.Boolean)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setMessageRevoke`(`messageId`: kotlin.ULong, `revoked`: kotlin.Boolean, `revoker`: kotlin.ULong?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `setUserSetting`(`key`: kotlin.String, `value`: kotlin.String)
    fun `setVideoProcessHook`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `shutdown`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `shutdownBlocking`()
    
        @Throws(PrivchatFfiException::class)fun `startSupervisedSync`(`intervalSecs`: kotlin.ULong)
    fun `startTransportDisconnectListener`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `startTypingBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stickerPackageDetailRemote`(`payload`: StickerPackageDetailInput): StickerPackageDetailView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stickerPackageListRemote`(`payload`: StickerPackageListInput): StickerPackageListView
    fun `stopSupervisedSync`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `stopTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `storage`(): UserStoragePaths
    fun `subscribeEvents`(): kotlin.Boolean
    fun `subscribeNetworkStatus`(): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `subscribePresence`(`userIds`: List<kotlin.ULong>): List<PresenceStatus>
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncAllChannels`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncBatchGetChannelPtsRemote`(`payload`: BatchGetChannelPtsInput): BatchGetChannelPtsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncEntities`(`entityType`: kotlin.String, `scope`: kotlin.String?): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncEntitiesInBackground`(`entityType`: kotlin.String, `scope`: kotlin.String?)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncGetChannelPtsRemote`(`payload`: GetChannelPtsInput): ChannelPtsView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncGetDifferenceRemote`(`payload`: GetDifferenceInput): GetDifferenceView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncMessages`(): kotlin.ULong
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncMessagesInBackground`()
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `syncSubmitRemote`(`payload`: SyncSubmitInput): SyncSubmitView
    fun `timezoneHours`(): kotlin.Int
    fun `timezoneLocal`(): kotlin.String
    fun `timezoneMinutes`(): kotlin.Int
    fun `timezoneSeconds`(): kotlin.Int
    fun `toClientEndpoint`(): kotlin.String?
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `unsubscribePresence`(`userIds`: List<kotlin.ULong>)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateDevicePushState`(`deviceId`: kotlin.String, `apnsArmed`: kotlin.Boolean, `pushToken`: kotlin.String?): DevicePushUpdateView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateMessageStatus`(`messageId`: kotlin.ULong, `status`: kotlin.Int)
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updatePrivacySettings`(`payload`: AccountPrivacyUpdateInput): kotlin.Boolean
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `updateProfile`(`payload`: ProfileUpdateInput): ProfileView
    
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
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeGenerate`(`expireSeconds`: kotlin.ULong?): QrCodeJsonView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeGet`(): QrCodeJsonView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userQrcodeRefresh`(`expireSeconds`: kotlin.ULong?): QrCodeJsonView
    
        @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)suspend fun `userStoragePaths`(): UserStoragePaths
    
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
    override suspend fun `authRefreshRemote`(`payload`: AuthRefreshInput) : LoginResult

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `authenticate`(`userId`: kotlin.ULong, `token`: kotlin.String, `deviceId`: kotlin.String)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `batchGetPresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus>

    override fun `build`(): kotlin.String
    

    override fun `builder`(): kotlin.String
    

    
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
    override suspend fun `createLocalMessage`(`input`: NewMessage) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `currentUserId`() : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `dataDir`() : kotlin.String

    override fun `debugMode`(): kotlin.Boolean
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteBlacklistEntry`(`blockedUserId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `deleteFriend`(`friendId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `disconnect`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `dmPeerUserId`(`channelId`: kotlin.ULong) : kotlin.ULong?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `downloadAttachmentToCache`(`sourcePath`: kotlin.String, `fileName`: kotlin.String) : kotlin.String

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `downloadAttachmentToMessageDir`(`sourcePath`: kotlin.String, `messageId`: kotlin.ULong, `fileName`: kotlin.String) : kotlin.String

    
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
    override suspend fun `enqueueOutboundFile`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray) : FileQueueRef

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueOutboundMessage`(`messageId`: kotlin.ULong, `payload`: kotlin.ByteArray) : kotlin.ULong

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `enqueueText`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong

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
    override suspend fun `fetchPresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus>

    override fun `fileApiBaseUrl`(): kotlin.String
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `fileRequestUploadTokenRemote`(`payload`: FileRequestUploadTokenInput) : FileRequestUploadTokenView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `fileUploadCallbackRemote`(`payload`: FileUploadCallbackInput) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getAllUnreadMentionCounts`(`userId`: kotlin.ULong) : List<UnreadMentionCount>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getAllUserSettings`() : UserSettingsView

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessageById`(`messageId`: kotlin.ULong) : StoredMessage?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessageExtra`(`messageId`: kotlin.ULong) : StoredMessageExtra?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getMessagesRemote`(`channelId`: kotlin.ULong, `beforeServerMessageId`: kotlin.ULong?, `limit`: kotlin.UInt?) : MessageHistoryView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `getOrCreateDirectChannel`(`peerUserId`: kotlin.ULong) : DirectChannelResult

    
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
    override suspend fun `getUserSetting`(`key`: kotlin.String) : kotlin.String?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupAddMembersRemote`(`groupId`: kotlin.ULong, `userIds`: List<kotlin.ULong>) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupApprovalHandleRemote`(`approvalId`: kotlin.ULong, `approved`: kotlin.Boolean, `reason`: kotlin.String?) : kotlin.Boolean

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupQrcodeGenerateRemote`(`groupId`: kotlin.ULong, `expireSeconds`: kotlin.ULong?) : GroupQrCodeGenerateView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `groupQrcodeJoinRemote`(`qrKey`: kotlin.String, `token`: kotlin.String?) : GroupQrCodeJoinResult

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `kvGet`(`key`: kotlin.String) : kotlin.ByteArray?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `kvPut`(`key`: kotlin.String, `value`: kotlin.ByteArray)

    
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
    override suspend fun `markAsRead`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markAsReadBlocking`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markChannelRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markFullyReadAt`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markMentionRead`(`messageId`: kotlin.ULong, `userId`: kotlin.ULong)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `markMessageSent`(`messageId`: kotlin.ULong, `serverMessageId`: kotlin.ULong)

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextEvent`(`timeoutMs`: kotlin.ULong) : SdkEvent?

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `nextEventEnvelope`(`timeoutMs`: kotlin.ULong) : SequencedSdkEvent?

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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `peekOutboundFiles`(`queueIndex`: kotlin.ULong, `limit`: kotlin.ULong) : List<QueueMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `peekOutboundMessages`(`limit`: kotlin.ULong) : List<QueueMessage>

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `pinChannel`(`channelId`: kotlin.ULong, `pinned`: kotlin.Boolean) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `ping`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeGenerate`(`qrType`: kotlin.String, `payload`: kotlin.String, `expireSeconds`: kotlin.ULong?) : QrCodeGenerateView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeList`(`qrType`: kotlin.String?) : QrCodeJsonView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeRefresh`(`qrKey`: kotlin.String, `expireSeconds`: kotlin.ULong?) : QrCodeJsonView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeResolve`(`qrKey`: kotlin.String, `token`: kotlin.String?) : QrCodeResolveView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `qrcodeRevoke`(`qrKey`: kotlin.String) : QrCodeJsonView

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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recallMessage`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recallMessageBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean

    override fun `recentEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent>
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `recordMention`(`input`: MentionInput) : kotlin.ULong

    
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
    override suspend fun `resolveChannelIdByServerMessageId`(`serverMessageId`: kotlin.ULong) : kotlin.ULong

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveChannelType`(`channelId`: kotlin.ULong) : kotlin.Int

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `resolveLocalMessageIdByServerMessageId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `serverMessageId`: kotlin.ULong) : kotlin.ULong?

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
    override suspend fun `sendFriendRequest`(`targetUserId`: kotlin.ULong, `message`: kotlin.String?, `source`: kotlin.String?, `sourceId`: kotlin.String?) : FriendRequestResult

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelLowPriority`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setChannelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `mode`: kotlin.Int)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setMessagePinned`(`messageId`: kotlin.ULong, `isPinned`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setMessageRead`(`messageId`: kotlin.ULong, `channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isRead`: kotlin.Boolean)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setMessageRevoke`(`messageId`: kotlin.ULong, `revoked`: kotlin.Boolean, `revoker`: kotlin.ULong?)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `setUserSetting`(`key`: kotlin.String, `value`: kotlin.String)

    override fun `setVideoProcessHook`()
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `shutdown`()

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `shutdownBlocking`()

    
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

    override fun `subscribeEvents`(): kotlin.Boolean
    

    override fun `subscribeNetworkStatus`(): kotlin.Boolean
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `subscribePresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus>

    
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
    override suspend fun `syncSubmitRemote`(`payload`: SyncSubmitInput) : SyncSubmitView

    override fun `timezoneHours`(): kotlin.Int
    

    override fun `timezoneLocal`(): kotlin.String
    

    override fun `timezoneMinutes`(): kotlin.Int
    

    override fun `timezoneSeconds`(): kotlin.Int
    

    override fun `toClientEndpoint`(): kotlin.String?
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `unsubscribePresence`(`userIds`: List<kotlin.ULong>)

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `updateDevicePushState`(`deviceId`: kotlin.String, `apnsArmed`: kotlin.Boolean, `pushToken`: kotlin.String?) : DevicePushUpdateView

    
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

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeGenerate`(`expireSeconds`: kotlin.ULong?) : QrCodeJsonView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeGet`() : QrCodeJsonView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userQrcodeRefresh`(`expireSeconds`: kotlin.ULong?) : QrCodeJsonView

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    override suspend fun `userStoragePaths`() : UserStoragePaths

    

    
    
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
    var `usersJson`: kotlin.String
        , 
    var `total`: kotlin.ULong
        , 
    var `query`: kotlin.String
        
) {
    
    companion object
}



data class AccountUserDetailView (
    var `userJson`: kotlin.String
        
) {
    
    companion object
}



data class AccountUserShareCardView (
    var `shareKey`: kotlin.String
        , 
    var `shareUrl`: kotlin.String
        , 
    var `expireAt`: kotlin.String?
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



data class AuthRefreshInput (
    var `refreshToken`: kotlin.String
        , 
    var `deviceId`: kotlin.String?
         = null 
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



data class ChannelBroadcastCreateInput (
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelBroadcastCreateView (
    var `responseJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelBroadcastListInput (
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelBroadcastListView (
    var `responseJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelBroadcastSubscribeInput (
    var `channelId`: kotlin.ULong
        
) {
    
    companion object
}



data class ChannelContentListInput (
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelContentListView (
    var `responseJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelContentPublishInput (
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class ChannelContentPublishView (
    var `responseJson`: kotlin.String
        
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
    var `message`: kotlin.String?
         = null , 
    var `createdAt`: kotlin.String
        
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
    var `addedAt`: kotlin.String
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



data class GroupApprovalListView (
    var `approvalsJson`: kotlin.String
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
    var `createdAt`: kotlin.String
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
    var `createdAt`: kotlin.String
        , 
    var `updatedAt`: kotlin.String
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
    var `responseJson`: kotlin.String
        
) {
    
    companion object
}



data class GroupQrCodeGenerateView (
    var `qrKey`: kotlin.String
        , 
    var `qrCode`: kotlin.String
        , 
    var `expireAt`: kotlin.String?
         = null , 
    var `groupId`: kotlin.ULong
        , 
    var `createdAt`: kotlin.String
        
) {
    
    companion object
}



data class GroupQrCodeJoinResult (
    var `status`: kotlin.String
        , 
    var `groupId`: kotlin.ULong
        , 
    var `requestId`: kotlin.String?
         = null , 
    var `message`: kotlin.String?
         = null , 
    var `expiresAt`: kotlin.String?
         = null , 
    var `userId`: kotlin.ULong?
         = null , 
    var `joinedAt`: kotlin.String?
         = null 
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
    var `updatedAt`: kotlin.String?
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
         = null 
) {
    
    companion object
}



data class GroupSettingsView (
    var `settingsJson`: kotlin.String
        
) {
    
    companion object
}



data class GroupTransferOwnerView (
    var `groupId`: kotlin.ULong
        , 
    var `newOwnerId`: kotlin.ULong
        , 
    var `transferredAt`: kotlin.String?
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



data class LoginResult (
    var `userId`: kotlin.ULong
        , 
    var `token`: kotlin.String
        , 
    var `deviceId`: kotlin.String
        , 
    var `refreshToken`: kotlin.String?
         = null , 
    var `expiresAt`: kotlin.String
        
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



data class MessageHistoryView (
    var `messagesJson`: kotlin.String
        , 
    var `hasMore`: kotlin.Boolean
        
) {
    
    companion object
}



data class MessageReactionListView (
    var `reactionsJson`: kotlin.String
        
) {
    
    companion object
}



data class MessageReactionStatsView (
    var `statsJson`: kotlin.String
        
) {
    
    companion object
}



data class MessageReadListView (
    var `readersJson`: kotlin.String
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



data class MessageUnreadCountView (
    var `unreadCount`: kotlin.Int
        , 
    var `channelId`: kotlin.String?
         = null 
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
    var `status`: kotlin.String
        , 
    var `lastSeen`: kotlin.Long
        , 
    var `onlineDevices`: List<kotlin.String>
        
) {
    
    companion object
}



data class PrivacySettingsView (
    var `settingsJson`: kotlin.String
        
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
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class ProfileView (
    var `profileJson`: kotlin.String
        
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
    var `createdAt`: kotlin.String
        , 
    var `expireAt`: kotlin.String?
         = null , 
    var `maxUsage`: kotlin.UInt?
         = null , 
    var `usedCount`: kotlin.UInt
        
) {
    
    companion object
}



data class QrCodeJsonView (
    var `json`: kotlin.String
        
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
    var `expireAt`: kotlin.String?
         = null 
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
    var `reactionsJson`: kotlin.String
        
) {
    
    companion object
}



data class ReactionsBatchView (
    var `items`: List<ReactionsBatchItemView>
        
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
        
) {
    
    companion object
}



data class SeenByEntry (
    var `userId`: kotlin.ULong
        , 
    var `readAt`: kotlin.String?
         = null 
) {
    
    companion object
}



data class SendMessageOptionsInput (
    var `optionsJson`: kotlin.String?
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



data class StickerPackageDetailInput (
    var `packageId`: kotlin.String
        
) {
    
    companion object
}



data class StickerPackageDetailView (
    var `responseJson`: kotlin.String
        
) {
    
    companion object
}



data class StickerPackageListInput (
    var `fieldsJson`: kotlin.String
        
) {
    
    companion object
}



data class StickerPackageListView (
    var `responseJson`: kotlin.String
        
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
    var `lastMsgContent`: kotlin.String
        , 
    var `updatedAt`: kotlin.Long
        
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
    var `status`: kotlin.Int
        , 
    var `createdAt`: kotlin.Long
        , 
    var `updatedAt`: kotlin.Long
        , 
    var `extra`: kotlin.String
        
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
    var `payloadJson`: kotlin.String
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



data class UserSettingsView (
    var `settingsJson`: kotlin.String
        
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
    SHUTDOWN;
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




sealed class SdkEvent {
    
    
    data class ConnectionStateChanged(
        val `from`: ConnectionState  , 
        val `to`: ConnectionState  ) : SdkEvent() {
        
    }
    
    
    data class BootstrapCompleted(
        val `userId`: kotlin.ULong  ) : SdkEvent() {
        
    }
    
    @kotlinx.serialization.Serializable
    object ShutdownStarted : SdkEvent() 
    
    
    @kotlinx.serialization.Serializable
    object ShutdownCompleted : SdkEvent() 
    
    
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







































































































expect fun `sdkVersion`(): kotlin.String
    


