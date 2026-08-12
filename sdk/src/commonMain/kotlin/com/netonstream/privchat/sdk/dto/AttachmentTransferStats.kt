package com.netonstream.privchat.sdk.dto

/**
 * 附件正文传输计数。都是**成功**次数：claim 换到自己的 file_id 且零字节；
 * upload 是字节真的传上去并被服务端接收。
 */
data class AttachmentTransferStats(
    val claims: ULong,
    val bodyUploads: ULong,
    val thumbnailUploads: ULong,
)
