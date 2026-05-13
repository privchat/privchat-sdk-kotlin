package com.netonstream.privchat.sdk.dto

/**
 * Channel Transfer client→app reply (decoded from wire `TransferResponse`).
 * See spec: privchat-docs/spec/02-server/CHANNEL_TRANSFER_SPEC.md v2.0
 * and privchat-docs/spec/07-application/BOT_INTERACTION_SPEC.md.
 */
data class TransferReply(
    val requestId: String,
    val channelId: ULong,
    val code: Int,
    val message: String,
    val data: ByteArray,
) {
    val isOk: Boolean get() = code == 0
}
