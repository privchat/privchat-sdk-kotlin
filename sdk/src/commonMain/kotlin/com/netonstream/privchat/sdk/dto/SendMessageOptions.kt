package om.netonstream.privchat.sdk.dto

data class SendMessageOptions(
    val inReplyToMessageId: ULong? = null,
    val mentions: List<ULong> = emptyList(),
    val silent: Boolean = false,
    val extraJson: String? = null,
) {
    companion object {
        fun default() = SendMessageOptions()
    }
}
