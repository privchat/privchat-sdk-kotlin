package com.netonstream.privchat.sdk

import android.util.Log
import com.netonstream.privchat.sdk.dto.*
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
import uniffi.privchat_sdk_ffi.MediaDownloadState as CoreMediaDownloadState
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
            val state = c.connectionState()
            if (state == CoreConnectionState.NEW || state == CoreConnectionState.SHUTDOWN) {
                c.connect()
            }
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

    actual suspend fun lastTerminalReason(): Result<TerminalReason?> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.lastTerminalReason()?.let(::mapCoreTerminalReason) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("lastTerminalReason failed", it)) },
        )
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
        val stored = runCatching { c.getMessageById(messageId) }.getOrNull()
            ?: return Result.failure(toSdkError("revokeMessage failed", IllegalStateException("message not found: $messageId")))
        val serverMessageId = stored.serverMessageId
            ?: return Result.failure(toSdkError("revokeMessage failed", IllegalStateException("message has no serverMessageId, cannot recall")))
        return callAsync("revokeMessage failed") { c.recallMessage(serverMessageId, stored.channelId) }
    }

    actual suspend fun editMessage(messageId: ULong, newContent: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return callAsync("editMessage failed") { c.editMessage(messageId, newContent, 0) }
    }

    actual suspend fun deleteMessageLocal(messageId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.deleteMessageLocal(messageId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("deleteMessageLocal failed", it)) },
        )
    }

    actual suspend fun forwardMessage(
        srcMessageId: ULong,
        targetChannelId: ULong,
        targetChannelType: Int,
    ): Result<ULong> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.forwardMessage(srcMessageId, targetChannelId, targetChannelType)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("forwardMessage failed", it)) },
        )
    }

    actual suspend fun addReaction(messageId: ULong, emoji: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val stored = runCatching { c.getMessageById(messageId) }.getOrNull()
            ?: return Result.failure(toSdkError("addReaction failed", IllegalStateException("message not found: $messageId")))
        val serverMessageId = stored.serverMessageId
            ?: return Result.failure(toSdkError("addReaction failed", IllegalStateException("message has no serverMessageId")))
        return callAsync("addReaction failed") { c.addReaction(serverMessageId, stored.channelId, emoji) }
    }

    actual suspend fun removeReaction(messageId: ULong, emoji: String): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        val stored = runCatching { c.getMessageById(messageId) }.getOrNull()
            ?: return Result.failure(toSdkError("removeReaction failed", IllegalStateException("message not found: $messageId")))
        val serverMessageId = stored.serverMessageId
            ?: return Result.failure(toSdkError("removeReaction failed", IllegalStateException("message has no serverMessageId")))
        return callAsync("removeReaction failed") { c.removeReaction(serverMessageId, emoji) }
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
            val raw = c.getChannels(limit.toULong(), offset.toULong())
            raw.forEachIndexed { idx, ch ->
                Log.i(
                    "PrivchatClient",
                    "[DBG][rawChannel][$idx] id=${ch.channelId} type=${ch.channelType} channelName='${ch.channelName}' remark='${ch.channelRemark}' unread=${ch.unreadCount}"
                )
            }
            raw.map { it.toCommonChannel() }.also { mapped ->
                mapped.forEachIndexed { idx, ch ->
                    Log.i(
                        "PrivchatClient",
                        "[DBG][mappedChannel][$idx] id=${ch.channelId} type=${ch.channelType} isDm=${ch.isDm} name='${ch.name}'"
                    )
                }
            }
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
        return try {
            c.stopSupervisedSync()
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(toSdkError("stopSupervisedSync failed", t))
        }
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

    actual suspend fun setChannelHiddenLocal(channelId: ULong, hidden: Boolean): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.setChannelHiddenLocal(channelId, hidden) }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(toSdkError("setChannelHiddenLocal failed", it)) },
        )
    }

    actual suspend fun deleteChannelLocal(channelId: ULong): Result<Boolean> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.deleteChannelLocal(channelId) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("deleteChannelLocal failed", it)) },
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
            val srcFile = java.io.File(path)
            val originalFilename = srcFile.name
            val createdAtMs = System.currentTimeMillis()

            val mime = guessMimeFromFilename(originalFilename)
            val canonicalFilename = payloadFilename(mime, originalFilename)

            // snowflake 仍然用来写 local_message_id 列（历史语义保留），UI/文件路径不再用它，统一用 DB 自增 id。
            val localMessageId = c.generateLocalMessageId()

            // 1. 视频先读时长/宽高（不抽帧），把这些元信息带进 extra。抽帧留到 /{dbid}/ 目录建好之后。
            val videoDims = if (messageType == ContentMessageType.VIDEO.value) {
                kotlinx.coroutines.withContext(Dispatchers.IO) { readVideoDimensions(path) }
            } else null
            val attachmentDuration: UInt? = videoDims?.durationSec?.toUInt()
            val attachmentWidth: UInt? = videoDims?.width?.toUInt()
            val attachmentHeight: UInt? = videoDims?.height?.toUInt()
            val extraJson = if (videoDims != null) {
                mergeVideoMetaIntoExtra(
                    options?.extraJson,
                    VideoMeta(videoDims.durationSec, videoDims.width, videoDims.height, thumbReady = false),
                    originalFilename,
                    mime,
                )
            } else {
                options?.extraJson ?: "{}"
            }

            // 2. 静默 INSERT：只为了拿 DB 自增 id，不 emit 事件。
            //    真正让 UI 看到这条消息的是后面 finalize 的 outbound_prep_complete。
            //    这样气泡出现时就是完整态（content+thumb+media 都齐），不会有 "空气泡 → 带缩略图" 的闪动。
            val insertedId = c.createLocalAttachmentPlaceholder(
                NewMessage(
                    channelId = channelId,
                    channelType = channelType,
                    fromUid = uid,
                    messageType = messageType,
                    content = "",
                    searchableWord = originalFilename,
                    setting = 0,
                    extra = extraJson,
                    mediaDownloaded = false,
                    thumbStatus = 0,
                ),
                localMessageId,
            )

            // 3. 按 DB id 写文件 + 抽缩略图到规范目录。
            val (localFilePath, videoMeta) = kotlinx.coroutines.withContext(Dispatchers.IO) {
                val targetDir = c.getAttachmentTargetDir(uid, insertedId.toLong(), createdAtMs)
                val targetFile = java.io.File(targetDir, canonicalFilename)
                srcFile.copyTo(targetFile, overwrite = true)

                val meta = if (messageType == ContentMessageType.VIDEO.value) {
                    extractVideoMetadata(path, targetDir)
                } else null

                targetFile.absolutePath to meta
            }

            val thumbStatus = when {
                messageType != ContentMessageType.VIDEO.value -> 0
                videoMeta?.thumbReady == true -> 1
                else -> 3
            }

            // 4. 文件已经落到 /{insertedId}/ 目录，一次性回写 content/thumb_status/media_downloaded
            //    并 emit outbound_prep_complete，让 UI 把气泡里的路径换成真实文件。
            c.finalizeLocalAttachment(insertedId, "file://$localFilePath", thumbStatus)
            // 给 UI 订阅者一个 tick 吸收 outbound_prep_complete 事件，避免 modal dismiss
            // 后 MessageEntry 还停留在空 content 的瞬时态。
            kotlinx.coroutines.delay(32)

            // 5. 关闭 loading 弹窗，气泡此时已有真实路径。
            progress?.onPrepComplete()

            // 6. 入队发送。
            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentFromPath(insertedId, routeKey, localFilePath)
            progress?.onProgress(1uL, 1uL)
            queueRef.messageId to AttachmentInfo(
                url = localFilePath,
                mimeType = mime,
                size = srcFile.length().toULong(),
                thumbnailUrl = null,
                filename = originalFilename,
                fileId = null,
                width = attachmentWidth,
                height = attachmentHeight,
                duration = attachmentDuration,
            )
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("sendAttachmentFromPath failed", it)) },
        )
    }

    private data class VideoMeta(
        val durationSec: Long?,
        val width: Int?,
        val height: Int?,
        val thumbReady: Boolean,
    )

    private data class VideoDimensions(
        val durationSec: Long?,
        val width: Int?,
        val height: Int?,
    )

    /**
     * 只读取视频时长/宽高，不抽帧。用在 INSERT 之前，让 message.extra 能包含这些元信息。
     */
    private fun readVideoDimensions(path: String): VideoDimensions {
        val retriever = android.media.MediaMetadataRetriever()
        return try {
            retriever.setDataSource(path)
            val durationMs = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.toLongOrNull()
            val width = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                ?.toIntOrNull()
            val height = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                ?.toIntOrNull()
            VideoDimensions(
                durationSec = durationMs?.let { (it / 1000).coerceAtLeast(1) },
                width = width,
                height = height,
            )
        } catch (_: Throwable) {
            VideoDimensions(null, null, null)
        } finally {
            runCatching { retriever.release() }
        }
    }

    /**
     * 抽帧首帧，缩放到目标尺寸后以 WEBP 写入 targetDir/thumb.webp。
     * 整个过程 ~100–300ms（使用 getScaledFrameAtTime 直接拿缩略图，不解码 4K 全帧）。
     */
    private fun extractVideoMetadata(path: String, targetDir: String): VideoMeta {
        val retriever = android.media.MediaMetadataRetriever()
        return try {
            retriever.setDataSource(path)
            val durationMs = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.toLongOrNull()
            val width = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                ?.toIntOrNull()
            val height = retriever
                .extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                ?.toIntOrNull()

            val thumbMax = 480
            val (dstW, dstH) = computeThumbSize(width, height, thumbMax)
            val bitmap = runCatching {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1 && dstW > 0 && dstH > 0) {
                    retriever.getScaledFrameAtTime(
                        0L,
                        android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC,
                        dstW,
                        dstH,
                    )
                } else {
                    retriever.getFrameAtTime(0L, android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                }
            }.getOrNull()

            val thumbReady = if (bitmap != null) {
                runCatching {
                    val thumbFile = java.io.File(targetDir, "thumb.webp")
                    java.io.FileOutputStream(thumbFile).use { out ->
                        @Suppress("DEPRECATION")
                        bitmap.compress(android.graphics.Bitmap.CompressFormat.WEBP, 80, out)
                    }
                    thumbFile.length() > 0
                }.getOrDefault(false).also { bitmap.recycle() }
            } else false

            VideoMeta(
                durationSec = durationMs?.let { (it / 1000).coerceAtLeast(1) },
                width = width,
                height = height,
                thumbReady = thumbReady,
            )
        } catch (t: Throwable) {
            VideoMeta(null, null, null, false)
        } finally {
            runCatching { retriever.release() }
        }
    }

    private fun computeThumbSize(srcW: Int?, srcH: Int?, maxDim: Int): Pair<Int, Int> {
        if (srcW == null || srcH == null || srcW <= 0 || srcH <= 0) return maxDim to maxDim
        val scale = maxDim.toFloat() / maxOf(srcW, srcH).toFloat()
        if (scale >= 1f) return srcW to srcH
        return (srcW * scale).toInt().coerceAtLeast(1) to (srcH * scale).toInt().coerceAtLeast(1)
    }

    private fun mergeVideoMetaIntoExtra(
        existing: String?,
        meta: VideoMeta,
        filename: String,
        mime: String,
    ): String {
        val base = existing?.takeIf { it.isNotBlank() } ?: "{}"
        val parsed = runCatching { Json.parseToJsonElement(base).jsonObject }.getOrNull()
            ?: JsonObject(emptyMap())
        val merged = parsed.toMutableMap()
        meta.durationSec?.let { merged["duration"] = JsonPrimitive(it) }
        meta.width?.let { merged["width"] = JsonPrimitive(it) }
        meta.height?.let { merged["height"] = JsonPrimitive(it) }
        merged.putIfAbsent("filename", JsonPrimitive(filename))
        merged.putIfAbsent("mime", JsonPrimitive(mime))
        return JsonObject(merged).toString()
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
            val srcFile = java.io.File(path)
            val originalFilename = srcFile.name
            val createdAtMs = System.currentTimeMillis()

            // Spec §7.5 v2: canonical payload filename
            val mime = guessMimeFromFilename(originalFilename)
            val canonicalFilename = payloadFilename(mime, originalFilename)

            val messageId = c.createLocalMessage(
                NewMessage(
                    channelId = channelId,
                    channelType = channelType,
                    fromUid = uid,
                    messageType = ContentMessageType.VOICE.value,
                    content = "[语音] $durationSec\"",
                    searchableWord = "",
                    setting = 0,
                    extra = """{"url":"$path","duration":$durationSec,"mime":"$mime","filename":"$originalFilename"}""",
                    mediaDownloaded = false,
                    thumbStatus = 0,
                )
            )
            // 复制到 media_store 规范目录（使用 payload.{ext} 命名）
            val targetDir = c.getAttachmentTargetDir(uid, messageId.toLong(), createdAtMs)
            val targetFile = java.io.File(targetDir, canonicalFilename)
            srcFile.copyTo(targetFile, overwrite = true)
            val localFilePath = targetFile.absolutePath

            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentFromPath(messageId, routeKey, localFilePath)
            progress?.onProgress(1uL, 1uL)
            queueRef.messageId to AttachmentInfo(
                url = localFilePath,
                mimeType = mime,
                size = srcFile.length().toULong(),
                thumbnailUrl = null,
                filename = originalFilename,
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
            val createdAtMs = System.currentTimeMillis()

            // Spec §7.5 v2: canonical payload filename
            val canonicalFilename = payloadFilename(mimeType, filename)

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
            // 写入 media_store 规范目录（使用 payload.{ext} 命名）
            val targetDir = c.getAttachmentTargetDir(uid, messageId.toLong(), createdAtMs)
            val targetFile = java.io.File(targetDir, canonicalFilename)
            targetFile.writeBytes(data)
            val localFilePath = targetFile.absolutePath

            val routeKey = c.toClientEndpoint() ?: ""
            val queueRef = c.sendAttachmentFromPath(messageId, routeKey, localFilePath)
            progress?.onProgress(data.size.toULong(), data.size.toULong())
            queueRef.messageId to AttachmentInfo(
                url = localFilePath,
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

    actual suspend fun updateMediaDownloaded(messageId: ULong, downloaded: Boolean): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.updateMediaDownloaded(messageId, downloaded) }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(toSdkError("updateMediaDownloaded failed", it)) },
        )
    }

    actual suspend fun startMessageMediaDownload(
        messageId: ULong,
        downloadUrl: String,
        mime: String,
        filenameHint: String?,
        createdAtMs: Long,
    ): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching {
            c.startMessageMediaDownload(messageId, downloadUrl, mime, filenameHint, createdAtMs)
            Unit
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("startMessageMediaDownload failed", it)) },
        )
    }

    actual suspend fun pauseMessageMediaDownload(messageId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.pauseMessageMediaDownload(messageId); Unit }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("pauseMessageMediaDownload failed", it)) },
        )
    }

    actual suspend fun resumeMessageMediaDownload(messageId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.resumeMessageMediaDownload(messageId); Unit }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("resumeMessageMediaDownload failed", it)) },
        )
    }

    actual suspend fun cancelMessageMediaDownload(messageId: ULong): Result<Unit> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { c.cancelMessageMediaDownload(messageId); Unit }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("cancelMessageMediaDownload failed", it)) },
        )
    }

    actual suspend fun getMediaDownloadState(messageId: ULong): Result<MediaDownloadState> {
        val c = requireClient().getOrElse { return Result.failure(it) }
        return runCatching { mapCoreMediaDownloadState(c.getMediaDownloadState(messageId)) }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(toSdkError("getMediaDownloadState failed", it)) },
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

    actual fun submitMediaJobResult(jobId: String, result: MediaJobResult): Result<Unit> {
        val client = coreClient ?: return Result.failure(SdkError.NotInitialized)
        return runCatching {
            client.submitMediaJobResult(
                jobId = jobId,
                result = uniffi.privchat_sdk_ffi.MediaJobResult(
                    ok = result.ok,
                    outputPath = result.outputPath,
                    error = result.error,
                ),
            )
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(toSdkError("submitMediaJobResult failed", it)) },
        )
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

    /**
     * Spec §7.5 v2: Canonical file naming — MIME → extension mapping.
     */
    private fun extFromMime(mime: String): String = when (mime.trim().lowercase()) {
        "image/jpeg", "image/jpg" -> "jpg"
        "image/png" -> "png"
        "image/gif" -> "gif"
        "image/webp" -> "webp"
        "image/heic" -> "heic"
        "image/heif" -> "heif"
        "image/bmp", "image/x-bmp" -> "bmp"
        "image/svg+xml" -> "svg"
        "image/tiff" -> "tiff"
        "video/mp4" -> "mp4"
        "video/quicktime" -> "mov"
        "video/x-matroska" -> "mkv"
        "video/webm" -> "webm"
        "video/x-msvideo" -> "avi"
        "video/3gpp" -> "3gp"
        "audio/mpeg", "audio/mp3" -> "mp3"
        "audio/mp4", "audio/x-m4a" -> "m4a"
        "audio/aac" -> "aac"
        "audio/ogg", "audio/vorbis" -> "ogg"
        "audio/wav", "audio/x-wav" -> "wav"
        "audio/flac" -> "flac"
        "audio/opus" -> "opus"
        "audio/amr" -> "amr"
        "application/pdf" -> "pdf"
        "application/zip" -> "zip"
        "application/x-rar-compressed", "application/vnd.rar" -> "rar"
        "application/x-7z-compressed" -> "7z"
        "text/plain" -> "txt"
        else -> "bin"
    }

    /**
     * Guess MIME type from filename extension (best-effort).
     */
    private fun guessMimeFromFilename(filename: String): String {
        val ext = filename.substringAfterLast('.', "").lowercase()
        return when (ext) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            "heic" -> "image/heic"
            "heif" -> "image/heif"
            "bmp" -> "image/bmp"
            "svg" -> "image/svg+xml"
            "tiff", "tif" -> "image/tiff"
            "mp4" -> "video/mp4"
            "mov" -> "video/quicktime"
            "mkv" -> "video/x-matroska"
            "webm" -> "video/webm"
            "avi" -> "video/x-msvideo"
            "3gp" -> "video/3gpp"
            "mp3" -> "audio/mpeg"
            "m4a" -> "audio/mp4"
            "aac" -> "audio/aac"
            "ogg" -> "audio/ogg"
            "wav" -> "audio/wav"
            "flac" -> "audio/flac"
            "opus" -> "audio/opus"
            "amr" -> "audio/amr"
            "pdf" -> "application/pdf"
            "zip" -> "application/zip"
            "rar" -> "application/vnd.rar"
            "7z" -> "application/x-7z-compressed"
            "txt" -> "text/plain"
            else -> "application/octet-stream"
        }
    }

    /**
     * Spec §7.5 v2: Build canonical payload filename `payload.{ext}`.
     * Fallback chain: known MIME → original filename ext → .bin
     */
    private fun payloadFilename(mime: String, originalFilename: String? = null): String {
        val ext = extFromMime(mime)
        if (ext != "bin") return "payload.$ext"
        // MIME unknown, try original filename extension
        if (originalFilename != null) {
            val origExt = originalFilename.substringAfterLast('.', "").lowercase()
            if (origExt.isNotEmpty() && origExt.length <= 10 && !origExt.contains('/') && !origExt.contains('\\')) {
                return "payload.$origExt"
            }
        }
        return "payload.bin"
    }

    private fun inferAttachmentMessageType(pathOrName: String, mimeType: String?): Int {
        val mime = mimeType?.trim()?.lowercase().orEmpty()
        if (mime.startsWith("image/")) return ContentMessageType.IMAGE.value
        if (mime.startsWith("video/")) return ContentMessageType.VIDEO.value

        val ext = pathOrName
            .substringAfterLast('/', pathOrName)
            .substringAfterLast('.', "")
            .lowercase()
        return when (ext) {
            "jpg", "jpeg", "png", "gif", "webp", "bmp", "heic", "heif" -> ContentMessageType.IMAGE.value
            "mp4", "mov", "mkv", "avi", "webm", "m4v", "3gp" -> ContentMessageType.VIDEO.value
            else -> ContentMessageType.FILE.value
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
): MessageEntry {
    val resolvedThumb = if (c != null && uid != null &&
        (messageType == ContentMessageType.IMAGE.value || messageType == ContentMessageType.VIDEO.value)) {
        c.resolveThumbnailPath(uid, messageId.toLong(), createdAt)
    } else null
    val resolvedMedia = if (c != null && uid != null) {
        c.resolveAttachmentPath(uid, messageId.toLong(), createdAt, null)
    } else null
    return MessageEntry(
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
        localThumbnailPath = resolvedThumb,
        localMediaPath = resolvedMedia,
        delivered = delivered,
        pts = pts,
        replyToServerMessageId = replyToMessageId,
        mentionedUserIds = mentionedUserIds,
    )
}

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
    // Terminated = 认证终局：SDK 已断开且不会自动重连；UI 必须靠 forced_logout 事件处理，
    // ConnectionState 这一层仅报"断开"以避免把 Reconnecting/Failed 的语义误传到上层。
    CoreConnectionState.TERMINATED -> ConnectionState.Disconnected
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
    val element: JsonElement? = when (value) {
        null -> null
        is JsonElement -> value
        is String -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        is ULong -> JsonPrimitive(value.toString())
        is UInt -> JsonPrimitive(value.toString())
        is List<*> -> JsonArray(value.mapNotNull { it?.let { e -> JsonPrimitive(e.toString()) } })
        else -> JsonPrimitive(value.toString())
    }
    put(key, element)
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

    is CoreSdkEvent.MediaDownloadStateChanged -> {
        val st = event.state
        var kind = "idle"
        var bytes: ULong? = null
        var total: ULong? = null
        var path: String? = null
        var errCode: UInt? = null
        var errMsg: String? = null
        when (st) {
            is CoreMediaDownloadState.Idle -> kind = "idle"
            is CoreMediaDownloadState.Downloading -> { kind = "downloading"; bytes = st.bytes; total = st.total }
            is CoreMediaDownloadState.Paused -> { kind = "paused"; bytes = st.bytes; total = st.total }
            is CoreMediaDownloadState.Done -> { kind = "done"; path = st.path }
            is CoreMediaDownloadState.Failed -> { kind = "failed"; errCode = st.code; errMsg = st.message }
        }
        SdkEventPayload(
            type = "media_download_state_changed",
            messageId = event.messageId,
            mediaDownloadStateKind = kind,
            mediaDownloadBytes = bytes,
            mediaDownloadTotal = total,
            mediaDownloadPath = path,
            errorCode = errCode,
            reason = errMsg,
        )
    }

    is CoreSdkEvent.MediaJobRequested -> SdkEventPayload(
        type = "media_job_requested",
        messageId = event.messageId,
        jobId = event.jobId,
        jobKind = event.jobKind,
        sourcePath = event.sourcePath,
        outputPath = event.outputPath,
        mimeType = event.mimeType,
        timeoutMs = event.timeoutMs,
    )

    is CoreSdkEvent.ForcedLogout -> SdkEventPayload(
        type = "forced_logout",
        code = event.code,
        reason = event.message,
        source = event.source.name,
    )

    CoreSdkEvent.ShutdownStarted -> SdkEventPayload(type = "shutdown_started")
    CoreSdkEvent.ShutdownCompleted -> SdkEventPayload(type = "shutdown_completed")
}

private fun mapCoreForcedLogoutSource(source: uniffi.privchat_sdk_ffi.ForcedLogoutSource): ForcedLogoutSource = when (source) {
    uniffi.privchat_sdk_ffi.ForcedLogoutSource.CONNECT_AUTH -> ForcedLogoutSource.ConnectAuth
    uniffi.privchat_sdk_ffi.ForcedLogoutSource.RPC_AUTH -> ForcedLogoutSource.RpcAuth
    uniffi.privchat_sdk_ffi.ForcedLogoutSource.MANUAL -> ForcedLogoutSource.Manual
}

private fun mapCoreTerminalReason(reason: uniffi.privchat_sdk_ffi.TerminalReason): TerminalReason = TerminalReason(
    code = reason.code,
    message = reason.message,
    source = mapCoreForcedLogoutSource(reason.source),
    atMs = reason.atMs,
)

private fun mapCoreMediaDownloadState(st: CoreMediaDownloadState): MediaDownloadState = when (st) {
    is CoreMediaDownloadState.Idle -> MediaDownloadState.Idle
    is CoreMediaDownloadState.Downloading -> MediaDownloadState.Downloading(st.bytes, st.total)
    is CoreMediaDownloadState.Paused -> MediaDownloadState.Paused(st.bytes, st.total)
    is CoreMediaDownloadState.Done -> MediaDownloadState.Done(st.path)
    is CoreMediaDownloadState.Failed -> MediaDownloadState.Failed(st.code, st.message)
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
