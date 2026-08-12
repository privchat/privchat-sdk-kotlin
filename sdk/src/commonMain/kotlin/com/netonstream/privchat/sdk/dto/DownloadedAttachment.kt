package com.netonstream.privchat.sdk.dto

/**
 * 一份下载到本地的附件。
 *
 * 🔴 [localPath] 是**磁盘位置的唯一真源**——由 SDK 下载、解密、命名、落盘之后给出。
 * 上层直接用它，不要按 id/扩展名再拼一次路径、不要复制后猜路径、不要扫描目录：
 * 那样就有了第二套命名规则，两套一旦分叉，表现是「文件明明下下来了却打不开」。
 *
 * 其余三个字段描述**这份内容是什么**，只进消息（展示名、MIME、消息类型），
 * 永远不参与构造 [localPath]。
 */
data class DownloadedAttachment(
    val localPath: String,
    /** 服务端记录的原始文件名，已清洗。没有就是空串。 */
    val displayFileName: String,
    /** 服务端记录的 MIME。没有就是空串。 */
    val mimeType: String,
    /** 服务端记录的类型：image / video / voice / file。没有就是空串。 */
    val fileType: String,
)
