package com.netonstream.privchat.sdk.dto.call

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CallMediaKind { @SerialName("audio") AUDIO, @SerialName("video") VIDEO }
@Serializable
enum class CallTransport { @SerialName("livekit") LIVEKIT, @SerialName("webrtc") WEBRTC }
@Serializable
enum class CallState { @SerialName("ringing") RINGING, @SerialName("active") ACTIVE, @SerialName("ended") ENDED }
@Serializable
enum class CallEndReason {
    @SerialName("normal") NORMAL, @SerialName("rejected") REJECTED,
    @SerialName("timeout") TIMEOUT, @SerialName("busy") BUSY,
    @SerialName("cancelled") CANCELLED, @SerialName("failed") FAILED,
}
@Serializable
enum class CallParticipantState {
    @SerialName("invited") INVITED, @SerialName("joined") JOINED,
    @SerialName("left") LEFT, @SerialName("rejected") REJECTED,
}
@Serializable
data class CallParticipant(@SerialName("user_id") val userId: ULong, val state: CallParticipantState)
@Serializable
data class CallSnapshot(
    @SerialName("call_id") val callId: ULong,
    @SerialName("channel_id") val channelId: ULong,
    @SerialName("initiator_id") val initiatorId: ULong,
    @SerialName("media_kind") val mediaKind: CallMediaKind,
    val state: CallState,
    @SerialName("end_reason") val endReason: CallEndReason? = null,
    val version: ULong,
    @SerialName("created_at_ms") val createdAtMs: Long,
    @SerialName("answered_at_ms") val answeredAtMs: Long? = null,
    @SerialName("ended_at_ms") val endedAtMs: Long? = null,
    @SerialName("expires_at_ms") val expiresAtMs: Long,
    val participants: List<CallParticipant>,
    val transport: CallTransport = CallTransport.LIVEKIT,
)
@Serializable
data class CallInviteRequest(
    @SerialName("command_id") val commandId: String,
    @SerialName("channel_id") val channelId: ULong,
    @SerialName("media_kind") val mediaKind: CallMediaKind,
    @SerialName("invitee_ids") val inviteeIds: List<ULong> = emptyList(),
    @SerialName("supports_peer") val supportsPeer: Boolean = true,
)
@Serializable
data class CallMutationRequest(
    @SerialName("command_id") val commandId: String,
    @SerialName("call_id") val callId: ULong,
    @SerialName("supports_peer") val supportsPeer: Boolean = true,
)
@Serializable
data class CallGetRequest(@SerialName("call_id") val callId: ULong)
@Serializable
data class CallResponse(val call: CallSnapshot)
@Serializable
data class CallJoinResponse(
    val call: CallSnapshot,
    val url: String = "",
    val token: String = "",
    @SerialName("expires_at_ms") val expiresAtMs: Long = 0,
    val peer: CallPeerConfig? = null,
) {
    override fun toString(): String = "CallJoinResponse(call=$call, credentials=<redacted>)"
}
@Serializable
data class CallActiveResponse(val calls: List<CallSnapshot>)
@Serializable
data class CallCapabilitiesResponse(
    val enabled: Boolean,
    @SerialName("audio_participant_limit") val audioParticipantLimit: UInt,
    @SerialName("video_participant_limit") val videoParticipantLimit: UInt,
    @SerialName("ringing_timeout_seconds") val ringingTimeoutSeconds: UInt,
    @SerialName("heartbeat_lease_seconds") val heartbeatLeaseSeconds: UInt = 60u,
    @SerialName("peer_enabled") val peerEnabled: Boolean = false,
    @SerialName("livekit_enabled") val livekitEnabled: Boolean = false,
)
