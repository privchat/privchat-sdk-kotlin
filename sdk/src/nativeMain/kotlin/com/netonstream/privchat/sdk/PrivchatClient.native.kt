package om.netonstream.privchat.sdk

import om.netonstream.privchat.sdk.dto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.contentOrNull
import uniffi.privchat_sdk_ffi.ConnectionState as CoreConnectionState
import uniffi.privchat_sdk_ffi.FileQueueRef
import uniffi.privchat_sdk_ffi.GetChannelPtsInput
import uniffi.privchat_sdk_ffi.LoginResult
import uniffi.privchat_sdk_ffi.NewMessage
import uniffi.privchat_sdk_ffi.PresenceStatus
import uniffi.privchat_sdk_ffi.PrivchatClient as CorePrivchatClient
import uniffi.privchat_sdk_ffi.PrivchatConfig as CoreConfig
import uniffi.privchat_sdk_ffi.PrivchatFfiException
import uniffi.privchat_sdk_ffi.SendMessageOptionsInput
import uniffi.privchat_sdk_ffi.ServerEndpoint as CoreEndpoint
import uniffi.privchat_sdk_ffi.StoredChannel
import uniffi.privchat_sdk_ffi.StoredFriend
import uniffi.privchat_sdk_ffi.StoredGroup
import uniffi.privchat_sdk_ffi.StoredGroupMember
import uniffi.privchat_sdk_ffi.StoredMessage
import uniffi.privchat_sdk_ffi.StoredUser
import uniffi.privchat_sdk_ffi.AccountUserDetailView
import uniffi.privchat_sdk_ffi.UpsertUserInput as CoreUpsertUserInput
import uniffi.privchat_sdk_ffi.TransportProtocol as CoreProtocol
import uniffi.privchat_sdk_ffi.TypingActionType
import uniffi.privchat_sdk_ffi.SdkEvent as CoreSdkEvent
import uniffi.privchat_sdk_ffi.sdkVersion

private val json = Json { ignoreUnknownKeys = true }

actual fun parseServerUrl(url: String): ServerEndpoint? {
    val regex = Regex("^(quic|wss|ws|tcp)://([^:/]+)(?::(\\d+))?(/.*)?$")
    val match = regex.matchEntire(url) ?: return null

    val scheme = match.groupValues[1]
    val host = match.groupValues[2]
    val portStr = match.groupValues[3]
    val path = match.groupValues[4].takeIf { it.isNotEmpty() }

    val protocol = when (scheme) {
        "quic" -> TransportProtocol.Quic
        "wss", "ws" -> TransportProtocol.WebSocket
        "tcp" -> TransportProtocol.Tcp
        else -> return null
    }

    val defaultPort = when (scheme) {
        "quic" -> 9001
        "wss" -> 443
        "ws" -> 80
        "tcp" -> 9000
        else -> 9001
    }

    return ServerEndpoint(
        protocol = protocol,
        host = host,
        port = portStr.toIntOrNull() ?: defaultPort,
        path = path,
        useTls = scheme == "wss",
    )
}

actual class PrivchatClient private actual constructor() {
    private val backgroundScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var coreClient: CorePrivchatClient? = null
    private var connectionMonitorJob: Job? = null
    private var cachedConnectionState: ConnectionState = ConnectionState.Disconnected
    private var cachedUserId: ULong? = null
    private var videoHook: VideoProcessHook? = null

    internal constructor(core: CorePrivchatClient) : this() {
        this.coreClient = core
        startConnectionMonitor(core)
    }

    private fun requireClient(): Result<CorePrivchatClient> {
        val c = coreClient ?: return Result.failure(SdkError.NotInitialized)
        return Result.success(c)
    }

    actual suspend fun connect(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("Connection failed") {
            c.connect()
            cachedConnectionState = ConnectionState.Connected
        }
    }

    actual suspend fun disconnect(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("Disconnect failed") {
            c.disconnect()
            cachedConnectionState = ConnectionState.Disconnected
            runCatching { c.clearPresenceCache() }
        }
    }

    actual suspend fun setNetworkHint(hint: NetworkHint): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("setNetworkHint failed") {
            c.setNetworkHint(hint.toCoreNetworkHint())
        }
    }

    actual fun close() {
        connectionMonitorJob?.cancel()
        connectionMonitorJob = null
        coreClient?.let { runCatching { it.destroy() } }
        coreClient = null
        backgroundScope.coroutineContext.cancel()
        cachedConnectionState = ConnectionState.Disconnected
        cachedUserId = null
    }

    actual fun isConnected(): Boolean = cachedConnectionState == ConnectionState.Connected

    actual fun connectionState(): ConnectionState = cachedConnectionState

    actual fun currentUserId(): ULong? = cachedUserId

    actual fun enterBackground(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callSync("enterBackground failed") { c.enterBackground() }
    }

    actual fun enterForeground(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callSync("enterForeground failed") { c.enterForeground() }
    }

    private fun startConnectionMonitor(core: CorePrivchatClient) {
        connectionMonitorJob?.cancel()
        connectionMonitorJob = backgroundScope.launch {
            while (isActive) {
                if (coreClient !== core) break
                runCatching { core.connectionState() }
                    .onSuccess { state -> cachedConnectionState = state.toCommonConnectionState() }
                    .onFailure { cachedConnectionState = ConnectionState.Disconnected }
                delay(1500)
            }
        }
    }

    actual suspend fun shutdown(): Result<Unit> =
        requireClient().fold(
            onSuccess = {
                callAsync("Shutdown failed") {
                    it.shutdown()
                    cachedConnectionState = ConnectionState.Disconnected
                    cachedUserId = null
                }
            },
            onFailure = { Result.failure(it) },
        )

    actual suspend fun nextEvent(timeoutMs: ULong): Result<SdkEventEnvelope?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val envelope = c.nextEventEnvelope(timeoutMs) ?: return@runCatching null
            SdkEventEnvelope(
                sequenceId = envelope.sequenceId,
                timestampMs = envelope.timestampMs,
                event = mapSdkEvent(envelope.event),
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("nextEvent failed", it)) },
        )
    }

    actual suspend fun recentEvents(limit: ULong): Result<List<SdkEventEnvelope>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.recentEvents(limit).map { envelope ->
                SdkEventEnvelope(
                    sequenceId = envelope.sequenceId,
                    timestampMs = envelope.timestampMs,
                    event = mapSdkEvent(envelope.event),
                )
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("recentEvents failed", it)) },
        )
    }

    actual fun observeEvents(timeoutMs: ULong): Flow<SdkEventEnvelope> = flow {
        while (currentCoroutineContext().isActive) {
            val next = nextEvent(timeoutMs).getOrElse { throw it }
            if (next != null) emit(next)
        }
    }

    actual suspend fun register(username: String, password: String, deviceId: String): Result<AuthResult> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val result = c.register(username, password, deviceId)
            cachedUserId = result.userId
            AuthResult(userId = result.userId, token = result.token)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("Register failed", it)) },
        )
    }

    actual suspend fun login(username: String, password: String, deviceId: String): Result<AuthResult> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            println("[FFI] login_future: enter")
            val result = c.login(username, password, deviceId)
            cachedUserId = result.userId
            cachedConnectionState = ConnectionState.Connected
            AuthResult(userId = result.userId, token = result.token)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("Login failed", it)) },
        )
    }

    actual suspend fun authenticate(userId: ULong, token: String, deviceId: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("Authenticate failed") {
            c.authenticate(userId, token, deviceId)
            cachedUserId = userId
            cachedConnectionState = ConnectionState.Connected
        }
    }

    actual suspend fun updateProfile(displayName: String?, avatarUrl: String?, bio: String?): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("updateProfile failed") {
            c.updateProfile(
                uniffi.privchat_sdk_ffi.ProfileUpdateInput(
                    displayName = displayName,
                    avatarUrl = avatarUrl,
                    bio = bio,
                )
            )
        }
    }

    actual suspend fun updateDevicePushState(
        deviceId: String,
        apnsArmed: Boolean,
        pushToken: String?,
    ): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("updateDevicePushState failed") {
            c.updateDevicePushState(deviceId, apnsArmed, pushToken)
        }
    }

    actual suspend fun restoreLocalSession(): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val snapshot = c.sessionSnapshot() ?: return@runCatching false
            c.authenticate(snapshot.userId, snapshot.token, snapshot.deviceId)
            cachedUserId = snapshot.userId
            cachedConnectionState = ConnectionState.Connected
            true
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("restoreLocalSession failed", it)) },
        )
    }

    actual suspend fun logout(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("Logout failed") {
            c.logout()
            cachedUserId = null
            cachedConnectionState = ConnectionState.Disconnected
            runCatching { c.clearPresenceCache() }
        }
    }

    actual fun generateLocalMessageId(): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.generateLocalMessageId() }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("generateLocalMessageId failed", it)) },
        )
    }

    actual suspend fun sendText(channelId: ULong, channelType: Int, text: String): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        return runCatching { c.enqueueText(channelId, channelType, uid, text) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendText failed", it)) },
        )
    }

    actual suspend fun sendTextWithLocalId(
        channelId: ULong,
        channelType: Int,
        text: String,
        localMessageId: ULong
    ): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        return runCatching { c.enqueueTextWithLocalId(channelId, channelType, uid, text, localMessageId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendTextWithLocalId failed", it)) },
        )
    }

    actual suspend fun sendText(
        channelId: ULong,
        channelType: Int,
        text: String,
        options: SendMessageOptions
    ): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        val input = NewMessage(
            channelId = channelId,
            channelType = channelType,
            fromUid = uid,
            messageType = 0,
            content = text,
            searchableWord = text,
            setting = 0,
            extra = options.extraJson ?: "{}",
            mediaDownloaded = false,
            thumbStatus = 0,
        )
        val optionsJson = buildJsonObject {
            this["in_reply_to_message_id"] = options.inReplyToMessageId
            this["mentions"] = options.mentions
            this["silent"] = options.silent
            this["extra_json"] = options.extraJson
        }
        val typedOptions = SendMessageOptionsInput(optionsJson = optionsJson)
        return runCatching { c.sendMessageWithOptions(input, typedOptions) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendText(options) failed", it)) },
        )
    }

    actual suspend fun sendMedia(
        channelId: ULong,
        filePath: String,
        options: SendMessageOptions?
    ): Result<Pair<ULong, AttachmentInfo>> {
        return sendAttachmentFromPath(channelId, filePath, options, null)
    }

    actual suspend fun retryMessage(messageId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("retryMessage failed") { c.retryMessage(messageId) }
    }

    actual suspend fun markReadToPts(channelId: ULong, readPts: ULong): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.markReadToPts(channelId, readPts) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("markReadToPts failed", it)) },
        )
    }

    actual suspend fun markAsRead(channelId: ULong, messageId: ULong): Result<Unit> {
        return markReadToPts(channelId, messageId).map { Unit }
    }

    actual suspend fun markFullyReadAt(channelId: ULong, messageId: ULong): Result<Unit> {
        return markReadToPts(channelId, messageId).map { Unit }
    }

    actual suspend fun revokeMessage(messageId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val channelId = runCatching { c.getMessageById(messageId)?.channelId ?: 0uL }.getOrDefault(0uL)
        return callAsync("revokeMessage failed") { c.recallMessage(messageId, channelId) }
    }

    actual suspend fun editMessage(messageId: ULong, newContent: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("editMessage failed") { c.editMessage(messageId, newContent, 0) }
    }

    actual suspend fun addReaction(messageId: ULong, emoji: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("addReaction failed") { c.addReaction(messageId, null, emoji) }
    }

    actual suspend fun removeReaction(messageId: ULong, emoji: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("removeReaction failed") { c.removeReaction(messageId, emoji) }
    }

    actual suspend fun reactions(channelId: ULong, messageId: ULong): Result<List<ReactionChip>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            listMessageReactionsAsChips(c, messageId)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("reactions failed", it)) },
        )
    }

    actual suspend fun reactionsBatch(
        channelId: ULong,
        messageIds: List<ULong>
    ): Result<Map<ULong, List<ReactionChip>>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            messageIds.associateWith { listMessageReactionsAsChips(c, it) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("reactionsBatch failed", it)) },
        )
    }

    actual suspend fun isEventReadBy(channelId: ULong, messageId: ULong, userId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.isEventReadBy(messageId, userId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("isEventReadBy failed", it)) },
        )
    }

    actual suspend fun seenByForEvent(channelId: ULong, messageId: ULong, limit: UInt?): Result<List<SeenByEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val entries = c.seenByForEvent(messageId)
            val mapped = entries.map {
                SeenByEntry(
                    userId = it.userId,
                    readAt = it.readAt ?: 0uL,
                )
            }
            limit?.let { mapped.take(it.toInt()) } ?: mapped
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("seenByForEvent failed", it)) },
        )
    }

    actual suspend fun searchMessages(query: String, channelId: String?): Result<List<MessageEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val id = channelId?.toULongOrNull()
            if (id != null) {
                val channelType = resolveChannelType(c, id)
                c.searchMessages(id, channelType, query).map { it.toCommonMessage(c, cachedUserId) }
            } else {
                c.searchChannel(query).flatMap { ch ->
                    c.searchMessages(ch.channelId, ch.channelType, query)
                }.map { it.toCommonMessage(c, cachedUserId) }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("searchMessages failed", it)) },
        )
    }

    actual suspend fun getMessages(channelId: ULong, limit: UInt, beforeSeq: ULong?): Result<List<MessageEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            val offset = 0uL
            val raw = c.getMessages(channelId, channelType, limit.toULong(), offset)
            val filtered = beforeSeq?.let { seq -> raw.filter { it.messageId < seq } } ?: raw
            filtered.map { it.toCommonMessage(c, cachedUserId) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getMessages failed", it)) },
        )
    }

    actual suspend fun getMessagesByType(
        channelId: ULong,
        channelType: Int,
        limit: UInt,
        beforeSeq: ULong?
    ): Result<List<MessageEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val offset = 0uL
            val raw = c.getMessages(channelId, channelType, limit.toULong(), offset)
            val filtered = beforeSeq?.let { seq -> raw.filter { it.messageId < seq } } ?: raw
            filtered.map { it.toCommonMessage(c, cachedUserId) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getMessagesByType failed", it)) },
        )
    }

    actual suspend fun getMessageById(messageId: ULong): Result<MessageEntry?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.getMessageById(messageId)?.toCommonMessage(c, cachedUserId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getMessageById failed", it)) },
        )
    }

    actual suspend fun paginateBack(channelId: ULong, beforeSeq: ULong, limit: UInt): Result<List<MessageEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            c.paginateBack(channelId, channelType, beforeSeq, limit.toULong()).map { it.toCommonMessage(c, cachedUserId) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("paginateBack failed", it)) },
        )
    }

    actual suspend fun paginateForward(channelId: ULong, afterSeq: ULong, limit: UInt): Result<List<MessageEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            c.paginateForward(channelId, channelType, afterSeq, limit.toULong()).map { it.toCommonMessage(c, cachedUserId) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("paginateForward failed", it)) },
        )
    }

    actual suspend fun listLocalAccounts(): Result<List<LocalAccountInfo>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.listLocalAccounts().map {
                LocalAccountInfo(
                    uid = it.uid,
                    createdAt = it.createdAt,
                    lastLoginAt = it.lastLoginAt,
                    isActive = it.isActive,
                )
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("listLocalAccounts failed", it)) },
        )
    }

    actual suspend fun setCurrentUid(uid: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("setCurrentUid failed") {
            c.setCurrentUid(uid)
            cachedUserId = uid.toULongOrNull()
        }
    }

    actual suspend fun getChannels(limit: UInt, offset: UInt): Result<List<ChannelListEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.getChannels(limit.toULong(), offset.toULong()).map { it.toCommonChannel() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getChannels failed", it)) },
        )
    }

    actual suspend fun getChannelById(channelId: ULong): Result<ChannelListEntry?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.getChannelById(channelId)?.toCommonChannel()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getChannelById failed", it)) },
        )
    }

    actual suspend fun getFriends(limit: UInt?, offset: UInt?): Result<List<FriendEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val list = c.getFriends((limit ?: 200u).toULong(), (offset ?: 0u).toULong())
            val ids = list.map { it.userId }.distinct()
            val userMap = if (ids.isNotEmpty()) {
                c.listUsersByIds(ids).associateBy { it.userId }
            } else {
                emptyMap()
            }
            list.map { it.toCommonFriend(userMap[it.userId]) }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getFriends failed", it)) },
        )
    }

    actual suspend fun getGroups(limit: UInt?, offset: UInt?): Result<List<GroupEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.getGroups((limit ?: 200u).toULong(), (offset ?: 0u).toULong()).map { it.toCommonGroup() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getGroups failed", it)) },
        )
    }

    actual suspend fun getGroupMembers(groupId: ULong, limit: UInt?, offset: UInt?): Result<List<GroupMemberEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.getGroupMembers(groupId, (limit ?: 200u).toULong(), (offset ?: 0u).toULong())
                .map { it.toCommonGroupMember() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getGroupMembers failed", it)) },
        )
    }

    actual suspend fun runBootstrapSync(): Result<Unit> =
        requireClient().fold(
            onSuccess = { callAsync("runBootstrapSync failed") { it.runBootstrapSync() } },
            onFailure = { Result.failure(it) },
        )

    actual fun runBootstrapSyncInBackground() {
        backgroundScope.launch { runBootstrapSync() }
    }

    actual suspend fun isBootstrapCompleted(): Result<Boolean> =
        requireClient().fold(
            onSuccess = {
                runCatching { it.isBootstrapCompleted() }.fold(
                    onSuccess = { done -> Result.success(done) },
                    onFailure = { e -> Result.failure(toSdkError("isBootstrapCompleted failed", e)) },
                )
            },
            onFailure = { Result.failure(it) },
        )

    actual suspend fun syncEntities(type: String, scope: String?): Result<UInt> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.syncEntities(type, scope).toUInt() }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("syncEntities failed", it)) },
        )
    }

    actual suspend fun syncChannel(channelId: ULong, channelType: UByte): Result<SyncStateEntry> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.syncChannel(channelId, channelType.toInt())
            val state = c.getChannelSyncState(channelId, channelType.toInt())
            SyncStateEntry(
                channelId = state.channelId,
                channelType = state.channelType,
                localPts = state.unread.toULong(),
                serverPts = state.unread.toULong(),
                needsSync = state.unread > 0,
                lastSyncAt = null,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("syncChannel failed", it)) },
        )
    }

    actual suspend fun syncAllChannels(): Result<List<SyncStateEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.syncAllChannels()
            c.getChannels(200u, 0u).map { channel ->
                runCatching {
                    val state = c.getChannelSyncState(channel.channelId, channel.channelType)
                    SyncStateEntry(
                        channelId = state.channelId,
                        channelType = state.channelType,
                        localPts = state.unread.toULong(),
                        serverPts = state.unread.toULong(),
                        needsSync = state.unread > 0,
                        lastSyncAt = null,
                    )
                }
                    .getOrElse {
                        SyncStateEntry(
                            channelId = channel.channelId,
                            channelType = channel.channelType,
                            localPts = 0uL,
                            serverPts = 0uL,
                            needsSync = false,
                            lastSyncAt = null,
                        )
                    }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("syncAllChannels failed", it)) },
        )
    }

    actual suspend fun getChannelSyncState(channelId: ULong, channelType: UByte): Result<SyncStateEntry> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val state = c.getChannelSyncState(channelId, channelType.toInt())
            SyncStateEntry(
                channelId = state.channelId,
                channelType = state.channelType,
                localPts = state.unread.toULong(),
                serverPts = state.unread.toULong(),
                needsSync = state.unread > 0,
                lastSyncAt = null,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getChannelSyncState failed", it)) },
        )
    }

    actual suspend fun getChannelCurrentPts(channelId: ULong, channelType: UByte): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.syncGetChannelPtsRemote(
                GetChannelPtsInput(
                    channelId = channelId,
                    channelType = channelType,
                )
            ).currentPts
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getChannelCurrentPts failed", it)) },
        )
    }

    actual suspend fun needsSync(channelId: ULong, channelType: UByte): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.needsSync() }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("needsSync failed", it)) },
        )
    }

    actual suspend fun startSupervisedSync(observer: SyncObserver): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.startSupervisedSync(5u)
            observer.onState(SyncStatus(SyncPhase.Running, "started"))
            Result.success(Unit)
        }.getOrElse { Result.failure(toSdkError("startSupervisedSync failed", it)) }
    }

    actual suspend fun stopSupervisedSync(): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.stopSupervisedSync() }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(toSdkError("stopSupervisedSync failed", it)) },
        )
    }

    actual suspend fun getPeerReadPts(channelId: ULong, channelType: Int): Result<ULong?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val pts = c.getPeerReadPts(channelId, channelType)
            if (pts == 0uL) null else pts
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getPeerReadPts failed", it)) },
        )
    }

    actual suspend fun markChannelRead(channelId: ULong, channelType: Int): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val serverPts = c.syncGetChannelPtsRemote(
                GetChannelPtsInput(channelId = channelId, channelType = channelType.toUByte())
            ).currentPts
            markReadToPts(channelId, serverPts).getOrThrow()
            Unit
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(toSdkError("markChannelRead failed", it)) },
        )
    }

    actual suspend fun pinChannel(channelId: ULong, pin: Boolean): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.pinChannel(channelId, pin); true }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("pinChannel failed", it)) },
        )
    }

    actual suspend fun hideChannel(channelId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.hideChannel(channelId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("hideChannel failed", it)) },
        )
    }

    actual suspend fun muteChannel(channelId: ULong, muted: Boolean): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.muteChannel(channelId, muted) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("muteChannel failed", it)) },
        )
    }

    actual suspend fun channelUnreadStats(channelId: ULong): Result<UnreadStats> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            val messages = c.channelUnreadStats(channelId, channelType).toULong()
            UnreadStats(messages = messages, notifications = messages, mentions = 0uL)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("channelUnreadStats failed", it)) },
        )
    }

    actual suspend fun ownLastRead(channelId: ULong): Result<LastReadPosition> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val id = c.ownLastRead(channelId)
            LastReadPosition(serverMessageId = id, timestamp = null)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("ownLastRead failed", it)) },
        )
    }

    actual suspend fun setChannelNotificationMode(channelId: ULong, mode: NotificationMode): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("setChannelNotificationMode failed") {
            val channelType = resolveChannelType(c, channelId)
            val modeInt = when (mode) {
                NotificationMode.All -> 0
                NotificationMode.Mentions -> 1
                NotificationMode.None -> 2
            }
            c.setChannelNotificationMode(channelId, channelType, modeInt)
        }
    }

    actual suspend fun getOrCreateDirectChannel(peerUserId: ULong): Result<GetOrCreateDirectChannelResult> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val resp = c.getOrCreateDirectChannel(peerUserId)
            GetOrCreateDirectChannelResult(
                channelId = resp.channelId,
                created = resp.created,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getOrCreateDirectChannel failed", it)) },
        )
    }

    actual suspend fun dmPeerUserId(channelId: ULong): Result<ULong?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.dmPeerUserId(channelId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("dmPeerUserId failed", it)) },
        )
    }

    actual suspend fun searchUsers(query: String): Result<List<UserEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.searchUsers(query).map {
                UserEntry(
                    userId = it.userId,
                    username = it.username,
                    nickname = it.nickname.takeIf { v -> v.isNotBlank() } ?: it.username,
                    avatarUrl = it.avatarUrl,
                    userType = it.userType.toShort(),
                    isFriend = it.isFriend,
                    canSendMessage = it.canSendMessage,
                    searchSessionId = it.searchSessionId,
                    isOnline = null,
                )
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("searchUsers failed", it)) },
        )
    }

    actual suspend fun getUserProfileLocalFirst(userId: ULong): Result<SearchedUserDto> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val local = c.getUserById(userId)
            if (local != null) {
                // local-first: return immediately, refresh remote in background.
                backgroundScope.launch {
                    runCatching {
                        val remote = c.accountUserDetailRemote(userId)
                        c.upsertUser(remote.toCoreUpsertUserInput(local))
                    }
                }
                local.toSearchedUserDto()
            } else {
                val remote = c.accountUserDetailRemote(userId)
                c.upsertUser(remote.toCoreUpsertUserInput(null))
                remote.toSearchedUserDto()
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getUserProfileLocalFirst failed", it)) },
        )
    }

    actual suspend fun listUsersByIds(userIds: List<ULong>): Result<List<UserEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            if (userIds.isEmpty()) emptyList() else c.listUsersByIds(userIds).map { it.toCommonUser() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("listUsersByIds failed", it)) },
        )
    }

    actual suspend fun sendFriendRequest(toUserId: ULong, remark: String?, searchSessionId: String?): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val resp = c.sendFriendRequest(toUserId, remark, "search", searchSessionId)
            resp.userId
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendFriendRequest failed", it)) },
        )
    }

    actual suspend fun acceptFriendRequest(fromUserId: ULong): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.acceptFriendRequest(fromUserId, null)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("acceptFriendRequest failed", it)) },
        )
    }

    actual suspend fun rejectFriendRequest(fromUserId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.rejectFriendRequest(fromUserId, null) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("rejectFriendRequest failed", it)) },
        )
    }

    actual suspend fun deleteFriend(userId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.deleteFriend(userId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("deleteFriend failed", it)) },
        )
    }

    actual suspend fun updateUserAlias(userId: ULong, alias: String?): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("updateUserAlias failed") { c.updateUserAlias(userId, alias) }
    }

    actual suspend fun listFriendPendingRequests(): Result<List<FriendPendingEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.getFriendPendingRequests().map {
                FriendPendingEntry(
                    fromUserId = it.fromUserId,
                    user = SearchedUserDto(
                        userId = it.user.userId,
                        username = it.user.username,
                        nickname = it.user.nickname,
                        avatarUrl = it.user.avatarUrl,
                        userType = it.user.userType.toShort(),
                        searchSessionId = it.user.searchSessionId,
                        isFriend = it.user.isFriend,
                        canSendMessage = it.user.canSendMessage,
                    ),
                    message = it.message,
                    createdAt = it.createdAt,
                )
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("listFriendPendingRequests failed", it)) },
        )
    }

    actual suspend fun createGroup(name: String, memberIds: List<ULong>): Result<GroupCreateResult> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val obj = c.createGroup(name, null, memberIds)
            GroupCreateResult(
                groupId = obj.groupId,
                name = obj.name,
                description = obj.description,
                memberCount = obj.memberCount,
                createdAt = obj.createdAt,
                creatorId = obj.creatorId,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("createGroup failed", it)) },
        )
    }

    actual suspend fun inviteToGroup(groupId: ULong, userIds: List<ULong>): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.inviteToGroup(groupId, userIds) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("inviteToGroup failed", it)) },
        )
    }

    actual suspend fun removeGroupMember(groupId: ULong, userId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.removeGroupMember(groupId, userId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("removeGroupMember failed", it)) },
        )
    }

    actual suspend fun leaveGroup(groupId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.leaveGroup(groupId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("leaveGroup failed", it)) },
        )
    }

    actual suspend fun joinGroupByQrcode(qrcode: String): Result<GroupQrCodeJoinResult> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val obj = c.joinGroupByQrcode(qrcode)
            GroupQrCodeJoinResult(
                status = obj.status,
                groupId = obj.groupId,
                requestId = obj.requestId,
                message = obj.message,
                expiresAt = obj.expiresAt,
                userId = obj.userId,
                joinedAt = obj.joinedAt,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("joinGroupByQrcode failed", it)) },
        )
    }

    actual fun getPresence(userId: ULong): PresenceEntry? =
        requireClient().getOrNull()?.let { client ->
            runCatching { runBlocking { client.getPresence(userId) } }
                .getOrNull()
                ?.toCommonPresence()
        }

    actual fun batchGetPresence(userIds: List<ULong>): List<PresenceEntry> =
        requireClient().getOrNull()?.let { client ->
            runCatching { userIds.mapNotNull { runBlocking { client.getPresence(it) } } }
                .getOrDefault(emptyList())
                .map { it.toCommonPresence() }
        } ?: emptyList()

    actual suspend fun fetchPresence(userIds: List<ULong>): Result<List<PresenceEntry>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.batchGetPresence(userIds).map { it.toCommonPresence() } }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("fetchPresence failed", it)) },
        )
    }

    actual suspend fun sendTyping(channelId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val channelType = runCatching { resolveChannelType(c, channelId) }.getOrDefault(1)
        return callAsync("sendTyping failed") { c.sendTyping(channelId, channelType, true, TypingActionType.TYPING) }
    }

    actual suspend fun stopTyping(channelId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val channelType = runCatching { resolveChannelType(c, channelId) }.getOrDefault(1)
        return callAsync("stopTyping failed") { c.sendTyping(channelId, channelType, false, TypingActionType.TYPING) }
    }

    actual suspend fun subscribeChannel(channelId: ULong, channelType: UByte, token: String?): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("subscribeChannel failed") { c.subscribeChannel(channelId, channelType, token) }
    }

    actual suspend fun unsubscribeChannel(channelId: ULong, channelType: UByte): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("unsubscribeChannel failed") { c.unsubscribeChannel(channelId, channelType) }
    }

    actual suspend fun sendAttachmentFromPath(
        channelId: ULong,
        path: String,
        options: SendMessageOptions?,
        progress: ProgressObserver?
    ): Result<Pair<ULong, AttachmentInfo>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            val messageType = inferAttachmentMessageType(path, null)
            val messageId = c.createLocalMessage(
                NewMessage(
                    channelId = channelId,
                    channelType = channelType,
                    fromUid = uid,
                    messageType = messageType,
                    content = path,
                    searchableWord = path,
                    setting = 0,
                    extra = options?.extraJson ?: "{}",
                    mediaDownloaded = false,
                    thumbStatus = 0,
                )
            )
            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentFromPath(messageId, routeKey, path)
            progress?.onProgress(1uL, 1uL)
            queueRef.messageId to AttachmentInfo(
                url = path,
                mimeType = "application/octet-stream",
                size = 0uL,
                thumbnailUrl = null,
                filename = path.substringAfterLast('/'),
                fileId = null,
                width = null,
                height = null,
                duration = null,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendAttachmentFromPath failed", it)) },
        )
    }

    actual suspend fun sendVoiceFromPath(
        channelId: ULong,
        path: String,
        durationMs: Long,
        options: SendMessageOptions?,
        progress: ProgressObserver?
    ): Result<Pair<ULong, AttachmentInfo>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            val durationSec = (durationMs / 1000).coerceAtLeast(1)
            val filename = path.substringAfterLast('/')
            val messageId = c.createLocalMessage(
                NewMessage(
                    channelId = channelId,
                    channelType = channelType,
                    fromUid = uid,
                    messageType = ContentMessageType.VOICE.value,
                    content = "[语音] $durationSec\"",
                    searchableWord = "",
                    setting = 0,
                    extra = """{"url":"$path","duration":$durationSec,"mime":"audio/mp4","filename":"$filename"}""",
                    mediaDownloaded = false,
                    thumbStatus = 0,
                )
            )
            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentFromPath(messageId, routeKey, path)
            progress?.onProgress(1uL, 1uL)
            queueRef.messageId to AttachmentInfo(
                url = path,
                mimeType = "audio/mp4",
                size = 0uL,
                thumbnailUrl = null,
                filename = filename,
                fileId = null,
                width = null,
                height = null,
                duration = durationSec.toUInt(),
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendVoiceFromPath failed", it)) },
        )
    }

    actual suspend fun sendAttachmentBytes(
        channelId: ULong,
        filename: String,
        mimeType: String,
        data: ByteArray,
        options: SendMessageOptions?,
        progress: ProgressObserver?
    ): Result<Pair<ULong, AttachmentInfo>> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val uid = cachedUserId ?: return Result.failure(SdkError.NotInitialized)
        return runCatching {
            val channelType = resolveChannelType(c, channelId)
            val messageType = inferAttachmentMessageType(filename, mimeType)
            val messageId = c.createLocalMessage(
                NewMessage(
                    channelId = channelId,
                    channelType = channelType,
                    fromUid = uid,
                    messageType = messageType,
                    content = filename,
                    searchableWord = filename,
                    setting = 0,
                    extra = options?.extraJson ?: "{}",
                    mediaDownloaded = false,
                    thumbStatus = 0,
                )
            )
            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentBytes(messageId, routeKey, data)
            progress?.onProgress(data.size.toULong(), data.size.toULong())
            queueRef.messageId to AttachmentInfo(
                url = filename,
                mimeType = mimeType,
                size = data.size.toULong(),
                thumbnailUrl = null,
                filename = filename,
                fileId = null,
                width = null,
                height = null,
                duration = null,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendAttachmentBytes failed", it)) },
        )
    }

    actual suspend fun downloadAttachmentToCache(
        fileId: String,
        fileUrl: String,
        progress: ProgressObserver?
    ): Result<String> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            val source = fileId.trim().ifEmpty { fileUrl.trim() }
            if (source.isEmpty()) {
                throw IllegalArgumentException("fileId/fileUrl is empty")
            }
            val cacheName = fileId.trim().ifEmpty {
                fileUrl.trim().substringAfterLast('/').ifEmpty { "attachment.bin" }
            }
            val path = c.downloadAttachmentToCache(source, cacheName)
            progress?.onProgress(1uL, 1uL)
            path
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("downloadAttachmentToCache failed", it)) },
        )
    }

    actual suspend fun downloadAttachmentToPath(
        fileUrl: String,
        outputPath: String,
        progress: ProgressObserver?
    ): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.downloadAttachmentToPath(fileUrl, outputPath)
            progress?.onProgress(1uL, 1uL)
            Unit
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("downloadAttachmentToPath failed", it)) },
        )
    }

    actual fun setVideoProcessHook(hook: VideoProcessHook?) {
        videoHook = hook
        val client = coreClient ?: return
        val coreHook = if (hook != null) {
            object : uniffi.privchat_sdk_ffi.VideoProcessHook {
                override fun process(
                    op: uniffi.privchat_sdk_ffi.MediaProcessOp,
                    sourcePath: String,
                    metaPath: String,
                    outputPath: String
                ): Boolean {
                    val dtoOp = when (op) {
                        uniffi.privchat_sdk_ffi.MediaProcessOp.THUMBNAIL -> MediaProcessOp.Thumbnail
                        uniffi.privchat_sdk_ffi.MediaProcessOp.COMPRESS -> MediaProcessOp.Compress
                    }
                    return hook.process(dtoOp, sourcePath, metaPath, outputPath).getOrDefault(false)
                }
            }
        } else null
        backgroundScope.launch {
            runCatching { client.setVideoProcessHook(coreHook) }
        }
    }

    actual fun removeVideoProcessHook() {
        videoHook = null
    }

    actual companion object {
        actual fun create(config: PrivchatConfig): Result<PrivchatClient> {
            if (config.serverEndpoints.isEmpty()) {
                return Result.failure(SdkError.InvalidParameter("serverEndpoints", "At least one endpoint required"))
            }

            return try {
                val coreConfig = CoreConfig(
                    endpoints = config.serverEndpoints.map { it.toCore() },
                    connectionTimeoutSecs = config.connectionTimeout,
                    dataDir = config.dataDir,
                )
                val coreClient = CorePrivchatClient(coreConfig)
                println("[FFI] PrivchatClient::new")
                println("[SDK] Rust SDK version=${sdkVersion()}")
                println("[SDK.iOS] create() ok")
                Result.success(PrivchatClient(coreClient))
            } catch (e: Throwable) {
                Result.failure(toSdkErrorStatic("SDK create failed", e))
            }
        }

        private fun ServerEndpoint.toCore(): CoreEndpoint = CoreEndpoint(
            protocol = when (protocol) {
                TransportProtocol.Quic -> CoreProtocol.QUIC
                TransportProtocol.Tcp -> CoreProtocol.TCP
                TransportProtocol.WebSocket -> CoreProtocol.WEB_SOCKET
            },
            host = host,
            port = port.toUShort(),
            path = path,
            useTls = useTls,
        )

        private fun toSdkErrorStatic(prefix: String, t: Throwable): SdkError {
            return when (t) {
                is SdkError -> t
                is PrivchatFfiException.SdkException -> mapFfiCodeToSdkError(prefix, t.code.toUInt(), t.detail)
                else -> SdkError.Generic("$prefix: ${t.message ?: t::class.simpleName ?: "unknown"}")
            }
        }
    }

    private suspend inline fun callAsync(message: String, crossinline block: suspend () -> Unit): Result<Unit> {
        return try {
            block()
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(toSdkError(message, e))
        }
    }

    private inline fun callSync(message: String, block: () -> Unit): Result<Unit> {
        return try {
            block()
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(toSdkError(message, e))
        }
    }

    actual fun getAttachmentTargetDir(uid: ULong, messageId: Long, createdAtMs: Long): String {
        val c = coreClient ?: throw IllegalStateException("SDK not initialized")
        return c.getAttachmentTargetDir(uid, messageId, createdAtMs)
    }

    actual fun resolveAttachmentPath(uid: ULong, messageId: Long, createdAtMs: Long, filename: String?): String? {
        val c = coreClient ?: return null
        return c.resolveAttachmentPath(uid, messageId, createdAtMs, filename)
    }

    private suspend fun resolveChannelType(client: CorePrivchatClient, channelId: ULong): Int =
        client.getChannelById(channelId)?.channelType ?: 1

    private fun inferAttachmentMessageType(pathOrName: String, mimeType: String?): Int {
        val mime = mimeType?.trim()?.lowercase().orEmpty()
        if (mime.startsWith("image/")) return 1
        if (mime.startsWith("video/")) return 4

        val ext = pathOrName
            .substringAfterLast('/', pathOrName)
            .substringAfterLast('.', "")
            .lowercase()
        return when (ext) {
            "jpg", "jpeg", "png", "gif", "webp", "bmp", "heic", "heif" -> 1
            "mp4", "mov", "mkv", "avi", "webm", "m4v", "3gp" -> 4
            else -> 2
        }
    }

    private fun toSdkError(prefix: String, t: Throwable): SdkError {
        return when (t) {
            is SdkError -> t
            is PrivchatFfiException.SdkException -> mapFfiCodeToSdkError(prefix, t.code.toUInt(), t.detail)
            else -> SdkError.Generic("$prefix: ${t.message ?: t::class.simpleName ?: "unknown"}")
        }
    }
}

private fun mapFfiCodeToSdkError(prefix: String, code: UInt, detail: String): SdkError = when (code) {
    SdkErrorCodes.NETWORK_DISCONNECTED,
    SdkErrorCodes.SHUTDOWN -> SdkError.Disconnected

    SdkErrorCodes.TRANSPORT_FAILURE -> SdkError.Network("$prefix: $detail", code.toInt())
    SdkErrorCodes.STORAGE_FAILURE -> SdkError.Database("$prefix: $detail")
    SdkErrorCodes.AUTH_FAILURE -> SdkError.Authentication("$prefix: $detail")
    SdkErrorCodes.INVALID_STATE -> SdkError.InvalidParameter("state", "$prefix: $detail")
    else -> {
        when (SdkErrorCodes.domain(code)) {
            SdkErrorCodes.DOMAIN_TRANSPORT -> SdkError.Network("$prefix: $detail", code.toInt())
            SdkErrorCodes.DOMAIN_STORAGE -> SdkError.Database("$prefix: $detail")
            SdkErrorCodes.DOMAIN_AUTH -> SdkError.Authentication("$prefix: $detail")
            else -> SdkError.Generic("$prefix [${code.toString(16)}]: $detail")
        }
    }
}

private fun StoredMessage.toCommonMessage(
    c: CorePrivchatClient? = null,
    uid: ULong? = null,
) = MessageEntry(
    id = messageId,
    serverMessageId = serverMessageId,
    localMessageId = localMessageId,
    channelId = channelId,
    channelType = channelType,
    fromUid = fromUid,
    content = content,
    status = when (status) {
        0 -> MessageStatus.Pending
        1 -> MessageStatus.Sending
        2 -> MessageStatus.Sent
        3 -> MessageStatus.Failed
        else -> MessageStatus.Read
    },
    timestamp = createdAt.toULong(),
    messageType = messageType,
    extra = extra,
    isRevoked = revoked,
    revoker = revokedBy,
    mimeType = mimeType,
    mediaDownloaded = mediaDownloaded,
    thumbStatus = thumbStatus,
    localThumbnailPath = if (c != null && uid != null && (messageType == 1 || messageType == 4))
        c.resolveThumbnailPath(uid, messageId.toLong(), createdAt) else null,
    localMediaPath = if (c != null && uid != null)
        c.resolveAttachmentPath(uid, messageId.toLong(), createdAt, null) else null,
    delivered = delivered,
    pts = pts,
)

private fun StoredChannel.toCommonChannel() = ChannelListEntry(
    channelId = channelId,
    channelType = channelType,
    name = channelName,
    lastTs = lastMsgTimestamp.toULong(),
    notifications = unreadCount.coerceAtLeast(0).toUInt(),
    messages = unreadCount.coerceAtLeast(0).toUInt(),
    mentions = 0u,
    markedUnread = unreadCount > 0,
    isFavourite = top > 0,
    isLowPriority = mute > 0,
    avatarUrl = avatar.takeIf { it.isNotBlank() },
    isDm = channelType == 1,
    isEncrypted = false,
    memberCount = 0u,
    topic = channelRemark.takeIf { it.isNotBlank() },
    latestEvent = lastMsgContent.takeIf { it.isNotBlank() }?.let {
        LatestChannelEvent(
            eventType = "message",
            content = it,
            timestamp = lastMsgTimestamp.toULong(),
        )
    },
    peerUserId = peerUserId,
)

private fun StoredFriend.toCommonFriend(user: StoredUser?) = FriendEntry(
    userId = userId,
    username = user?.username?.takeIf { it.isNotBlank() } ?: userId.toString(),
    nickname = user?.nickname?.takeIf { it.isNotBlank() },
    avatarUrl = user?.avatar?.takeIf { it.isNotBlank() },
    userType = (user?.userType ?: 0).toShort(),
    status = "accepted",
    addedAt = createdAt,
    remark = user?.alias?.takeIf { it.isNotBlank() } ?: tags,
)

private fun StoredUser.toCommonUser() = UserEntry(
    userId = userId,
    username = username?.takeIf { it.isNotBlank() } ?: userId.toString(),
    nickname = nickname?.takeIf { it.isNotBlank() },
    avatarUrl = avatar?.takeIf { it.isNotBlank() },
    userType = userType.toShort(),
    isFriend = false,
    canSendMessage = true,
    searchSessionId = null,
    isOnline = null,
)

private fun StoredUser.toSearchedUserDto() = SearchedUserDto(
    userId = userId,
    username = username?.takeIf { it.isNotBlank() } ?: userId.toString(),
    nickname = nickname?.takeIf { it.isNotBlank() }
        ?: username?.takeIf { it.isNotBlank() }
        ?: userId.toString(),
    avatarUrl = avatar.takeIf { it.isNotBlank() },
    userType = userType.toShort(),
    searchSessionId = 0u,
    isFriend = false,
    canSendMessage = true,
)

private fun AccountUserDetailView.toSearchedUserDto() = SearchedUserDto(
    userId = userId,
    username = username,
    nickname = nickname.ifBlank { username },
    avatarUrl = avatarUrl,
    userType = userType,
    searchSessionId = 0u,
    isFriend = isFriend,
    canSendMessage = canSendMessage,
)

private fun AccountUserDetailView.toCoreUpsertUserInput(local: StoredUser?) = CoreUpsertUserInput(
    userId = userId,
    username = username.takeIf { it.isNotBlank() },
    nickname = nickname.takeIf { it.isNotBlank() },
    alias = local?.alias?.takeIf { it.isNotBlank() },
    avatar = avatarUrl.orEmpty(),
    userType = userType.toInt(),
    isDeleted = false,
    channelId = local?.channelId.orEmpty(),
    updatedAt = ((local?.updatedAt ?: 0L) + 1L).coerceAtLeast(1L),
)

private fun StoredGroup.toCommonGroup() = GroupEntry(
    groupId = groupId,
    name = name,
    avatar = avatar,
    ownerId = ownerId,
    isDismissed = isDismissed,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun StoredGroupMember.toCommonGroupMember() = GroupMemberEntry(
    userId = userId,
    channelId = groupId,
    channelType = 2,
    name = alias ?: userId.toString(),
    remark = alias ?: "",
    avatar = "",
    role = role,
    status = status,
    inviteUserId = 0uL,
)

private fun PresenceStatus.toCommonPresence() = PresenceEntry(
    userId = userId,
    isOnline = isOnline,
    lastSeen = lastSeenAt,
    deviceType = null,
)

private fun CoreConnectionState.toCommonConnectionState(): ConnectionState = when (this) {
    CoreConnectionState.NEW -> ConnectionState.Disconnected
    CoreConnectionState.CONNECTED -> ConnectionState.Connected
    CoreConnectionState.LOGGED_IN -> ConnectionState.Connected
    CoreConnectionState.AUTHENTICATED -> ConnectionState.Connected
    CoreConnectionState.SHUTDOWN -> ConnectionState.Disconnected
}

private fun NetworkHint.toCoreNetworkHint(): uniffi.privchat_sdk_ffi.NetworkHint = when (this) {
    NetworkHint.Unknown -> uniffi.privchat_sdk_ffi.NetworkHint.UNKNOWN
    NetworkHint.Offline -> uniffi.privchat_sdk_ffi.NetworkHint.OFFLINE
    NetworkHint.Wifi -> uniffi.privchat_sdk_ffi.NetworkHint.WIFI
    NetworkHint.Cellular -> uniffi.privchat_sdk_ffi.NetworkHint.CELLULAR
    NetworkHint.Ethernet -> uniffi.privchat_sdk_ffi.NetworkHint.ETHERNET
}

private fun decodeSeenBy(raw: String, limit: UInt?): List<SeenByEntry> {
    val arr = parseJsonObject(raw).array("items")
        ?: parseJsonElement(raw).takeIf { it is JsonArray } as? JsonArray
        ?: return emptyList()
    val all = arr.mapNotNull { elem ->
        val obj = elem as? JsonObject ?: return@mapNotNull null
        SeenByEntry(
            userId = obj.ulong("user_id") ?: obj.ulong("userId") ?: return@mapNotNull null,
            readAt = obj.ulong("read_at") ?: obj.ulong("readAt") ?: 0uL,
        )
    }
    return limit?.let { all.take(it.toInt()) } ?: all
}

private fun decodeSyncState(raw: String, channelId: ULong, channelType: Int): SyncStateEntry {
    val obj = parseJsonObject(raw)
    return SyncStateEntry(
        channelId = obj.ulong("channel_id") ?: obj.ulong("channelId") ?: channelId,
        channelType = (obj.long("channel_type") ?: obj.long("channelType") ?: channelType.toLong()).toInt(),
        localPts = obj.ulong("local_pts") ?: obj.ulong("localPts") ?: 0uL,
        serverPts = obj.ulong("server_pts") ?: obj.ulong("serverPts") ?: 0uL,
        needsSync = obj.bool("needs_sync") ?: obj.bool("needsSync") ?: false,
        lastSyncAt = obj.long("last_sync_at") ?: obj.long("lastSyncAt"),
    )
}

private suspend fun listMessageReactionsAsChips(c: CorePrivchatClient, messageId: ULong): List<ReactionChip> {
    val rows = c.listMessageReactions(messageId, 500uL, 0uL).filter { !it.isDeleted }
    if (rows.isEmpty()) return emptyList()
    return rows.groupBy { it.emoji }
        .map { (emoji, group) ->
            val userIds = group.map { it.uid }.distinct()
            ReactionChip(
                emoji = emoji,
                userIds = userIds,
                count = userIds.size.toULong(),
            )
        }
}

private fun parseJsonElement(raw: String): JsonElement? = runCatching { Json.parseToJsonElement(raw) }.getOrNull()

private fun parseJsonObject(raw: String): JsonObject =
    (parseJsonElement(raw) as? JsonObject) ?: JsonObject(emptyMap())

private fun JsonObject.obj(key: String): JsonObject? = this[key] as? JsonObject
private fun JsonObject.array(key: String): JsonArray? = this[key] as? JsonArray
private fun JsonObject.prim(key: String): JsonPrimitive? = this[key] as? JsonPrimitive
private fun JsonObject.string(key: String): String? = prim(key)?.contentOrNull
private fun JsonObject.bool(key: String): Boolean? = prim(key)?.booleanOrNull
private fun JsonObject.long(key: String): Long? = prim(key)?.longOrNull
private fun JsonObject.uint(key: String): UInt? = prim(key)?.contentOrNull?.toUIntOrNull()
private fun JsonObject.ulong(key: String): ULong? = prim(key)?.contentOrNull?.toULongOrNull()
private fun JsonElement.asUlong(): ULong? = (this as? JsonPrimitive)?.contentOrNull?.toULongOrNull()

private fun buildJsonObject(builder: MutableMap<String, JsonElement?>.() -> Unit): String {
    val map = linkedMapOf<String, JsonElement?>().apply(builder)
    val actual = map.mapNotNull { (k, v) -> if (v == null) null else k to v }.toMap()
    return JsonObject(actual).toString()
}

private operator fun MutableMap<String, JsonElement?>.set(key: String, value: Any?) {
    this[key] = when (value) {
        null -> null
        is String -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        is ULong -> JsonPrimitive(value.toString())
        is UInt -> JsonPrimitive(value.toString())
        is List<*> -> JsonArray(value.mapNotNull { it?.let { e -> JsonPrimitive(e.toString()) } })
        else -> JsonPrimitive(value.toString())
    }
}

private fun mapSdkEvent(event: CoreSdkEvent): SdkEventPayload = when (event) {
    is CoreSdkEvent.ConnectionStateChanged -> SdkEventPayload(
        type = "connection_state_changed",
        fromState = event.from.name,
        toState = event.to.name,
    )

    is CoreSdkEvent.BootstrapCompleted -> SdkEventPayload(
        type = "bootstrap_completed",
        userId = event.userId,
    )

    CoreSdkEvent.ResumeSyncStarted -> SdkEventPayload(type = "resume_sync_started")
    is CoreSdkEvent.ResumeSyncCompleted -> SdkEventPayload(
        type = "resume_sync_completed",
        entityTypesSynced = event.entityTypesSynced,
        channelsScanned = event.channelsScanned,
        channelsApplied = event.channelsApplied,
        channelFailures = event.channelFailures,
    )

    is CoreSdkEvent.ResumeSyncFailed -> SdkEventPayload(
        type = "resume_sync_failed",
        classification = event.classification.name,
        scope = event.scope.name,
        errorCode = event.errorCode.toUInt(),
        reason = event.message,
    )

    is CoreSdkEvent.ResumeSyncEscalated -> SdkEventPayload(
        type = "resume_sync_escalated",
        classification = event.classification.name,
        scope = event.scope.name,
        reason = event.reason,
        entityType = event.entityType,
        channelId = event.channelId,
        channelType = event.channelType,
    )

    is CoreSdkEvent.ResumeSyncChannelStarted -> SdkEventPayload(
        type = "resume_sync_channel_started",
        channelId = event.channelId,
        channelType = event.channelType,
    )

    is CoreSdkEvent.ResumeSyncChannelCompleted -> SdkEventPayload(
        type = "resume_sync_channel_completed",
        channelId = event.channelId,
        channelType = event.channelType,
        applied = event.applied,
    )

    is CoreSdkEvent.ResumeSyncChannelFailed -> SdkEventPayload(
        type = "resume_sync_channel_failed",
        channelId = event.channelId,
        channelType = event.channelType,
        classification = event.classification.name,
        scope = event.scope.name,
        errorCode = event.errorCode.toUInt(),
        reason = event.message,
    )

    is CoreSdkEvent.SyncEntitiesApplied -> SdkEventPayload(
        type = "sync_entities_applied",
        entityType = event.entityType,
        scope = event.scope,
        queued = event.queued,
        applied = event.applied,
        droppedDuplicates = event.droppedDuplicates,
    )

    is CoreSdkEvent.SyncEntityChanged -> SdkEventPayload(
        type = "sync_entity_changed",
        entityType = event.entityType,
        entityId = event.entityId,
        deleted = event.deleted,
    )

    is CoreSdkEvent.SyncChannelApplied -> SdkEventPayload(
        type = "sync_channel_applied",
        channelId = event.channelId,
        channelType = event.channelType,
        applied = event.applied,
    )

    is CoreSdkEvent.SyncAllChannelsApplied -> SdkEventPayload(
        type = "sync_all_channels_applied",
        applied = event.applied,
    )

    is CoreSdkEvent.NetworkHintChanged -> SdkEventPayload(
        type = "network_hint_changed",
        fromNetworkHint = event.from.name,
        toNetworkHint = event.to.name,
    )

    is CoreSdkEvent.OutboundQueueUpdated -> SdkEventPayload(
        type = "outbound_queue_updated",
        kind = event.kind,
        action = event.action,
        messageId = event.messageId,
        queueIndex = event.queueIndex,
    )

    is CoreSdkEvent.TimelineUpdated -> SdkEventPayload(
        type = "timeline_updated",
        channelId = event.channelId,
        channelType = event.channelType,
        messageId = event.messageId,
        reason = event.reason,
    )

    is CoreSdkEvent.MessageSendStatusChanged -> SdkEventPayload(
        type = "message_send_status_changed",
        messageId = event.messageId,
        status = event.status,
        serverMessageId = event.serverMessageId,
    )

    is CoreSdkEvent.TypingSent -> SdkEventPayload(
        type = "typing_sent",
        channelId = event.channelId,
        channelType = event.channelType,
        isTyping = event.isTyping,
    )

    is CoreSdkEvent.SubscriptionMessageReceived -> SdkEventPayload(
        type = "subscription_message_received",
        channelId = event.channelId,
        topic = event.topic,
        payload = event.payload,
        publisher = event.publisher,
        serverMessageId = event.serverMessageId,
        timestamp = event.timestamp,
    )

    is CoreSdkEvent.PeerReadPtsAdvanced -> SdkEventPayload(
        type = "peer_read_pts_advanced",
        channelId = event.channelId,
        channelType = event.channelType,
        readerId = event.readerId,
        readPts = event.readPts,
    )

    is CoreSdkEvent.MessageDelivered -> SdkEventPayload(
        type = "message_delivered",
        channelId = event.channelId,
        channelType = event.channelType,
        serverMessageId = event.serverMessageId,
        deliveredAt = event.deliveredAt,
    )

    CoreSdkEvent.ShutdownStarted -> SdkEventPayload(type = "shutdown_started")
    CoreSdkEvent.ShutdownCompleted -> SdkEventPayload(type = "shutdown_completed")
}

private fun parseReadPtsAck(raw: String, fallbackReadPts: ULong): ULong {
    val obj = parseJsonObject(raw)
    val data = obj.obj("data") ?: obj
    return data.ulong("last_read_pts")
        ?: data.ulong("read_pts")
        ?: fallbackReadPts
}

private fun parseTimestampUlongOrNull(raw: String?): ULong? {
    if (raw.isNullOrBlank()) return null
    return raw.toULongOrNull()
}

private fun parseTimestampUlong(raw: String): ULong = parseTimestampUlongOrNull(raw) ?: 0uL
