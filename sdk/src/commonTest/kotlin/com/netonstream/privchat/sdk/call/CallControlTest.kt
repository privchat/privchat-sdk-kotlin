package com.netonstream.privchat.sdk.call

import com.netonstream.privchat.sdk.dto.SdkEventPayload
import com.netonstream.privchat.sdk.dto.call.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.*
import kotlin.test.*

class CallControlTest {
    @Test fun transportCapabilitiesDecodeExplicitFlagsAndLegacyDefaults() = runTest {
        val base = """{"enabled":true,"audio_participant_limit":8,"video_participant_limit":4,"ringing_timeout_seconds":30"""
        suspend fun capabilities(fields: String): CallCapabilitiesResponse =
            RpcCallControl(CallRpcTransport { _, _ -> Result.success("$base$fields}") })
                .capabilities().getOrThrow()
        val legacy = capabilities("")
        assertTrue(legacy.enabled)
        assertFalse(legacy.peerEnabled)
        assertFalse(legacy.livekitEnabled)
        val peerOnly = capabilities(""", "peer_enabled":true,"livekit_enabled":false""")
        assertTrue(peerOnly.peerEnabled)
        assertFalse(peerOnly.livekitEnabled)
        val livekitOnly = capabilities(""", "peer_enabled":false,"livekit_enabled":true""")
        assertFalse(livekitOnly.peerEnabled)
        assertTrue(livekitOnly.livekitEnabled)
    }

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
        assertNull(SdkEventPayload("subscription_message_received", topic = "call/state",
            payload = """{"call":$snapshot}""".encodeToByteArray()).callStateSnapshot())
        assertNull(SdkEventPayload("subscription_message_received", route = "call/state",
            body = """{"call":$snapshot}""".encodeToByteArray()).callStateSnapshot())
        assertNull(SdkEventPayload("rpc_notification_received", route = "other").callStateSnapshot())
        assertTrue(SdkEventPayload("rpc_notification_received", route = "call/state", body = byteArrayOf()).callStateSnapshot()!!.isFailure)
        assertTrue(SdkEventPayload("rpc_notification_received", route = "call/state").callStateSnapshot()!!.isFailure)
        assertEquals(ULong.MAX_VALUE, SdkEventPayload("rpc_notification_received", route = "call/state",
            body = """{"call":$snapshot}""".encodeToByteArray()).callStateSnapshot()!!.getOrThrow().callId)
    }

    @Test fun peerDtosAndReliableRpcKeepIdsAndAdvertiseCapability() = runTest {
        val sent = mutableListOf<Pair<String, String>>()
        val peerJson = """{"self_user_id":18446744073709551615,"peer_user_id":9007199254740993,"offerer":true,"ice_servers":[{"urls":["turn:example.invalid"],"username":"USER_SECRET","credential":"TURN_SECRET"}],"credential_expires_at_ms":10000}"""
        val peerSnapshot = snapshot.dropLast(1) + """, "transport":"webrtc"}"""
        val api = RpcCallControl(CallRpcTransport { route, body ->
            sent += route to body
            Result.success(when (route) {
                "call/join" -> """{"call":$peerSnapshot,"peer":$peerJson}"""
                "call/peer/events" -> """{"events":[{"seq":1,"revision":1,"kind":"answer","payload":"RAW_SDP","from_user_id":9007199254740993}],"next_seq":1}"""
                "call/peer/signal" -> "ignored acknowledgement"
                else -> """{"call":$snapshot}"""
            })
        })
        api.invite(CallInviteRequest("invite", 9u, CallMediaKind.AUDIO)).getOrThrow()
        val result = api.join(CallMutationRequest("join", 1u)).getOrThrow()
        assertEquals(CallTransport.WEBRTC, result.call.transport)
        assertEquals(ULong.MAX_VALUE, result.peer!!.selfUserId)
        assertEquals("", result.url); assertEquals("", result.token)
        assertFalse(result.peer.toString().contains("TURN_SECRET"))
        assertFalse(result.peer.iceServers.toString().contains("USER_SECRET"))
        for ((_, body) in sent.take(2)) assertTrue(Json.parseToJsonElement(body).jsonObject["supports_peer"]!!.jsonPrimitive.boolean)
        val request = CallPeerSignalRequest("signal-intent", 1u, 1u, CallPeerSignalKind.OFFER, "RAW_SDP")
        api.peerSignal(request).getOrThrow(); api.peerSignal(request).getOrThrow()
        assertEquals(sent[2], sent[3]); assertFalse(request.toString().contains("RAW_SDP"))
        val events = api.peerEvents(CallPeerEventsRequest(1u)).getOrThrow()
        assertEquals(9007199254740993uL, events.events.single().fromUserId)
        assertFalse(events.toString().contains("RAW_SDP"))
        assertEquals(CallTransport.LIVEKIT, Json.decodeFromString<CallSnapshot>(snapshot).transport)
    }
}
