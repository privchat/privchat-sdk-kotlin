package com.netonstream.privchat.sdk.dto

data class GroupCreateResult(
    val groupId: ULong,
    val name: String,
    val description: String?,
    val memberCount: UInt,
    val createdAt: ULong,
    val creatorId: ULong,
)
