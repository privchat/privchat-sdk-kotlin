package com.netonstream.privchat.sdk.call

import com.netonstream.privchat.sdk.dto.SdkEventPayload
import com.netonstream.privchat.sdk.dto.call.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.*
import kotlin.test.*

class CallControlTest {
    private val snapshot = """{"call_id":18446744073709551615,"channel_id":9,"initiator_id":1,"media_kind":"audio","state":"ringing","version":1,"created_at_ms":0,"expires_at_ms":30000,"participants":[{"user_id":1,"state":"joined"}]}"""
    @Test fun typedRoutesAndStableRetryBody() = runTest {
        assertEquals("com.netonstream.privchat.sdk.dto.call.CallInviteRequest", CallInviteRequest.serializer().descriptor.serialName)
        val sent = mutableListOf<Pair<String, String>>()
        val api = RpcCallControl(CallRpcTransport { route, body ->
            sent += route to body
            Result.success(when (route) {
                "call/capabilities" -> """{"enabled":true,"audio_participant_limit":8,"video_participant_limit":4,"ringing_timeout_seconds":30}"""
                "call/active" -> """{"calls":[$snapshot]}"""
                "call/join" -> """{"call":$snapshot,"url":"wss://example.invalid","token":"SECRET","expires_at_ms":10}"""
                else -> """{"call":$snapshot}"""
            })
        })
        val invite = CallInviteRequest("intent", 9u, CallMediaKind.AUDIO)
        assertEquals(ULong.MAX_VALUE, api.invite(invite).getOrThrow().call.callId)
        api.invite(invite).getOrThrow(); assertEquals(sent[0], sent[1])
        assertEquals("audio", Json.parseToJsonElement(sent[0].second).jsonObject["media_kind"]!!.jsonPrimitive.content)
        val mutation = CallMutationRequest("mutation", ULong.MAX_VALUE)
        assertFalse(api.join(mutation).getOrThrow().toString().contains("SECRET"))
        api.reject(mutation).getOrThrow(); api.cancel(mutation).getOrThrow(); api.leave(mutation).getOrThrow()
        api.end(mutation).getOrThrow(); api.get(ULong.MAX_VALUE).getOrThrow(); api.active().getOrThrow()
        api.capabilities().getOrThrow(); api.heartbeat(mutation).getOrThrow()
        assertEquals(setOf("invite", "join", "reject", "cancel", "leave", "end", "get", "active", "capabilities", "heartbeat"),
            sent.map { it.first.removePrefix("call/") }.toSet())
    }

    @Test fun preservesTransportFailureAndRejectsMalformedPayload() = runTest {
        val error = IllegalStateException("offline")
        assertSame(error, RpcCallControl(CallRpcTransport { _, _ -> Result.failure(error) }).active().exceptionOrNull())
        assertTrue(RpcCallControl(CallRpcTransport { _, _ -> Result.success("{}") }).active().isFailure)
        assertNull(SdkEventPayload("unrelated", topic = "call/state").callStateSnapshot())
        assertTrue(SdkEventPayload("subscription_message_received", topic = "call/state", payload = byteArrayOf()).callStateSnapshot()!!.isFailure)
        assertEquals(ULong.MAX_VALUE, SdkEventPayload("subscription_message_received", topic = "call/state",
            payload = """{"call":$snapshot}""".encodeToByteArray()).callStateSnapshot()!!.getOrThrow().callId)
    }
}
