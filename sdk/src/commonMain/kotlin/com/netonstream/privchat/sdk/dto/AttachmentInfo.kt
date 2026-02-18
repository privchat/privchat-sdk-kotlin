package om.netonstream.privchat.sdk.dto

data class AttachmentInfo(
    val url: String,
    val mimeType: String,
    val size: ULong,
    val thumbnailUrl: String?,
    val filename: String?,
    val fileId: String?,
    val width: UInt?,
    val height: UInt?,
    val duration: UInt?,
)
