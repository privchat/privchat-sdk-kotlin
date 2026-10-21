package com.netonstream.privchat.sdk.call

import com.netonstream.privchat.sdk.dto.SdkEventPayload
import com.netonstream.privchat.sdk.dto.call.*
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** Retain a command ID when retrying the same intent; never reuse it for a different intent. */
interface CallControl {
    suspend fun invite(request: CallInviteRequest): Result<CallResponse>
    suspend fun join(request: CallMutationRequest): Result<CallJoinResponse>
    suspend fun reject(request: CallMutationRequest): Result<CallResponse>
    suspend fun cancel(request: CallMutationRequest): Result<CallResponse>
    suspend fun leave(request: CallMutationRequest): Result<CallResponse>
    suspend fun end(request: CallMutationRequest): Result<CallResponse>
    suspend fun get(callId: ULong): Result<CallResponse>
    suspend fun active(): Result<CallActiveResponse>
    suspend fun capabilities(): Result<CallCapabilitiesResponse>
    suspend fun heartbeat(request: CallMutationRequest): Result<CallResponse>
    suspend fun peerSignal(request: CallPeerSignalRequest): Result<Unit> = Result.failure(UnsupportedOperationException("Peer signalling unavailable"))
    suspend fun peerEvents(request: CallPeerEventsRequest): Result<CallPeerEventsResponse> = Result.failure(UnsupportedOperationException("Peer signalling unavailable"))
}

fun interface CallRpcTransport {
    suspend fun call(route: String, body: String): Result<String>
}

private val callJson = Json { ignoreUnknownKeys = true; encodeDefaults = true }

class RpcCallControl(private val transport: CallRpcTransport) : CallControl {
    private suspend inline fun <reified T> rpc(action: String, body: String = "{}"): Result<T> {
        return try {
            transport.call("call/$action", body).mapCatching { callJson.decodeFromString<T>(it) }
        } catch (error: CancellationException) { throw error }
        catch (error: Exception) { Result.failure(error) }
    }

    override suspend fun invite(request: CallInviteRequest): Result<CallResponse> = rpc("invite", callJson.encodeToString(request))
    override suspend fun join(request: CallMutationRequest): Result<CallJoinResponse> = rpc("join", callJson.encodeToString(request))
    override suspend fun reject(request: CallMutationRequest): Result<CallResponse> = rpc("reject", callJson.encodeToString(request))
    override suspend fun cancel(request: CallMutationRequest): Result<CallResponse> = rpc("cancel", callJson.encodeToString(request))
    override suspend fun leave(request: CallMutationRequest): Result<CallResponse> = rpc("leave", callJson.encodeToString(request))
    override suspend fun end(request: CallMutationRequest): Result<CallResponse> = rpc("end", callJson.encodeToString(request))
    override suspend fun get(callId: ULong): Result<CallResponse> = rpc("get", callJson.encodeToString(CallGetRequest(callId)))
    override suspend fun active(): Result<CallActiveResponse> = rpc("active")
    override suspend fun capabilities(): Result<CallCapabilitiesResponse> = rpc("capabilities")
    override suspend fun heartbeat(request: CallMutationRequest): Result<CallResponse> = rpc("heartbeat", callJson.encodeToString(request))
    override suspend fun peerSignal(request: CallPeerSignalRequest): Result<Unit> = try {
        transport.call("call/peer/signal", callJson.encodeToString(request)).map { Unit }
    } catch (cancelled: CancellationException) { throw cancelled }
    catch (failure: Exception) { Result.failure(failure) }
    override suspend fun peerEvents(request: CallPeerEventsRequest): Result<CallPeerEventsResponse> =
        rpc("peer/events", callJson.encodeToString(request))
}

/** Decode server RPC notifications only. The host forwards its single event stream. */
fun SdkEventPayload.callStateSnapshot(): Result<CallSnapshot>? {
    if (type != "rpc_notification_received" || route != "call/state") return null
    return runCatching { callJson.decodeFromString<CallResponse>(requireNotNull(body).decodeToString()).call }
}
