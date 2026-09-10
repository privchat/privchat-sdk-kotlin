package com.netonstream.privchat.sdk.dto

/**
 * 群已读明细 DTO type aliases（READ_STATUS_SPEC §6.5）。
 *
 * 与 [GroupSettingsView] 同思路：uniffi 生成的数据类已是最终形态，直接在 dto
 * 包下 re-export，UI 不必认识 `uniffi.privchat_sdk_ffi` 包。
 */

typealias MessageReadStatsView = uniffi.privchat_sdk_ffi.MessageReadStatsView
typealias MessageReadListView = uniffi.privchat_sdk_ffi.MessageReadListView
typealias MessageReadUserView = uniffi.privchat_sdk_ffi.MessageReadUserView
