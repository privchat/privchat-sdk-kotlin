package com.netonstream.privchat.sdk.dto

/**
 * 群设置 / 群成员禁言 / 群消息置顶 DTO type aliases（P0-4 / P0-5 / P1）。
 *
 * 与 [QrCodeTypes] 同思路：uniffi 生成的数据类已是最终形态（lowerCamelCase +
 * 合理可空性），直接在 dto 包下 re-export，使 UI 只依赖
 * `com.netonstream.privchat.sdk.dto.*`，不必认识 `uniffi.privchat_sdk_ffi` 包。
 */

typealias GroupSettingsView = uniffi.privchat_sdk_ffi.GroupSettingsView
typealias GroupSettingsUpdateInput = uniffi.privchat_sdk_ffi.GroupSettingsUpdateInput
typealias GroupPinMessageView = uniffi.privchat_sdk_ffi.GroupPinMessageView
typealias GroupPinnedMessageView = uniffi.privchat_sdk_ffi.GroupPinnedMessageView
typealias GroupMuteAllView = uniffi.privchat_sdk_ffi.GroupMuteAllView
