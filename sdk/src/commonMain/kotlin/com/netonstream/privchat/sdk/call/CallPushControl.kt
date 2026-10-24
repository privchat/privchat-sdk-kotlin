package com.netonstream.privchat.sdk.call

import com.netonstream.privchat.sdk.PrivchatClient
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
enum class CallPushPlatform { @SerialName("fcm") FCM, @SerialName("apns_voip") APNS_VOIP }

@Serializable
data class CallPushRegisterRequest(val platform: CallPushPlatform, val token: String) {
    override fun toString(): String = "CallPushRegisterRequest(platform=$platform, token=<redacted>)"
}

@Serializable
data class CallPushRegistrationResponse(val registered: Boolean)

/** Device/account ownership is taken from the authenticated transport, never request body IDs. */
interface CallPushControl {
    suspend fun register(request: CallPushRegisterRequest): Result<CallPushRegistrationResponse>
    suspend fun unregister(): Result<CallPushRegistrationResponse>
}

fun createCallPushControl(client: PrivchatClient): CallPushControl =
    RpcCallPushControl(CallRpcTransport { route, body -> client.callRpc(route, body) })

class RpcCallPushControl(private val transport: CallRpcTransport) : CallPushControl {
    private val json = Json { ignoreUnknownKeys = true }
    override suspend fun register(request: CallPushRegisterRequest): Result<CallPushRegistrationResponse> =
        request("call/push/register", json.encodeToString(request))
    override suspend fun unregister(): Result<CallPushRegistrationResponse> = request("call/push/unregister", "{}")
    private suspend fun request(route: String, body: String): Result<CallPushRegistrationResponse> = try {
        val result = transport.call(route, body)
        result.exceptionOrNull()?.let { if (it is CancellationException) throw it }
        result.mapCatching { json.decodeFromString<CallPushRegistrationResponse>(it) }
    } catch (cancel: CancellationException) { throw cancel }
      catch (error: Exception) { Result.failure(error) }
}
