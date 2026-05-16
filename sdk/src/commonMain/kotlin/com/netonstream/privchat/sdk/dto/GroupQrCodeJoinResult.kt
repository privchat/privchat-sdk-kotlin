package com.netonstream.privchat.sdk.dto

/**
 * `group/join/qrcode` 响应（QR_CODE_SPEC v1.4）。
 *
 * v1.4 删除了 `expiresAt` 字段——永久 qr_key 模型下不存在"邀请链接过期时间"
 * 的概念。审批排队（status="pending"）也不带过期，由 server 内部的审批 TTL
 * 自己治理。
 */
data class GroupQrCodeJoinResult(
    val status: String,
    val groupId: ULong,
    val requestId: String?,
    val message: String?,
    val userId: ULong?,
    val joinedAt: ULong?,
)
