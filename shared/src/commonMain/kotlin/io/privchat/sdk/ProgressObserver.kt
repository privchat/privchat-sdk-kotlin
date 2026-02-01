package io.privchat.sdk

/**
 * 进度观察者 - 用于文件上传/下载进度回调
 * 各平台 actual 负责适配到 FFI 的 ProgressObserver
 */
interface ProgressObserver {
    fun onProgress(current: ULong, total: ULong?)
}
