package com.netonstream.privchat.sdk.dto

// 群审批（P6-3）：直接复用 uniffi 生成的 View 类型，与 GroupSettingsView 同理（无自定义包装）。
// GroupApprovalItemView.requestId 是 server 侧 UUID 字符串，handleGroupApproval 用它。
typealias GroupApprovalListView = uniffi.privchat_sdk_ffi.GroupApprovalListView
typealias GroupApprovalItemView = uniffi.privchat_sdk_ffi.GroupApprovalItemView
