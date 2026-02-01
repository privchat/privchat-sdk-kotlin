package io.privchat.sdk

import io.privchat.sdk.dto.SyncStatus

/**
 * 同步状态观察者 - 用于受监督同步
 * 各平台 actual 负责适配到 FFI 的 SyncObserver
 */
interface SyncObserver {
    fun onState(status: SyncStatus)
}
