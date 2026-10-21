package com.netonstream.privchat.sdk.dto.call

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CallIceServer(val urls: List<String>, val username: String? = null, val credential: String? = null) {
    override fun toString(): String = "CallIceServer(credentials=<redacted>)"
}

@Serializable
data class CallPeerConfig(
    @SerialName("self_user_id") val selfUserId: ULong,
    @SerialName("peer_user_id") val peerUserId: ULong,
    val offerer: Boolean,
    @SerialName("ice_servers") val iceServers: List<CallIceServer>,
    @SerialName("credential_expires_at_ms") val credentialExpiresAtMs: Long,
) {
    override fun toString(): String = "CallPeerConfig(selfUserId=$selfUserId, peerUserId=$peerUserId, offerer=$offerer, credentials=<redacted>)"
}

@Serializable
enum class CallPeerSignalKind {
    @SerialName("offer") OFFER, @SerialName("answer") ANSWER, @SerialName("ice") ICE,
}

/** SDP is raw text; ICE is a JSON string with candidate/sdpMid/sdpMLineIndex. Never log either. */
@Serializable
data class CallPeerSignal(
    val revision: UInt,
    val kind: CallPeerSignalKind,
    val payload: String,
) {
    override fun toString(): String = "CallPeerSignal(revision=$revision, kind=$kind, payload=<redacted>)"
}

@Serializable
data class CallPeerSignalRequest(
    @SerialName("command_id") val commandId: String,
    @SerialName("call_id") val callId: ULong,
    val revision: UInt,
    val kind: CallPeerSignalKind,
    val payload: String,
) {
    override fun toString(): String = "CallPeerSignalRequest(callId=$callId, revision=$revision, kind=$kind, payload=<redacted>)"
}

@Serializable
data class CallPeerEvent(
    val seq: ULong,
    val revision: UInt,
    val kind: CallPeerSignalKind,
    val payload: String,
    @SerialName("from_user_id") val fromUserId: ULong,
) {
    override fun toString(): String = "CallPeerEvent(seq=$seq, revision=$revision, kind=$kind, fromUserId=$fromUserId, payload=<redacted>)"
}

@Serializable
data class CallPeerEventsRequest(@SerialName("call_id") val callId: ULong, @SerialName("after_seq") val afterSeq: ULong = 0u)
@Serializable
data class CallPeerEventsResponse(val events: List<CallPeerEvent>, @SerialName("next_seq") val nextSeq: ULong)
