package com.netonstream.privchat.sdk

/**
 * 统一 SDK 错误模型，符合 [SDK_API_CONTRACT](docs/SDK_API_CONTRACT.md)
 */
sealed class SdkError(message: String, cause: Throwable? = null) : Exception(message, cause) {
    data class Generic(val msg: String) : SdkError(msg)
    data class Database(val msg: String) : SdkError(msg)
    data class Network(val msg: String, val code: Int) : SdkError("$msg (code: $code)")
    data class Authentication(val reason: String) : SdkError(reason)
    data class InvalidParameter(val field: String, val msg: String) : SdkError("$field: $msg")
    data class Timeout(val timeoutSecs: ULong) : SdkError("Timeout after ${timeoutSecs}s")
    object Disconnected : SdkError("Disconnected")
    object NotInitialized : SdkError("Not initialized")
    data class UploadFailed(val msg: String) : SdkError(msg)
    /**
     * 重发附件时本地源文件已不在（被系统清理/用户删除）。上层应提示重新选择文件，
     * 而不是笼统的「发送失败」——重试再多次也不会成功。
     */
    data class AttachmentSourceMissing(val msg: String) : SdkError(msg)
    /**
     * 会话尚未鉴权（连接中/重连中）就发起业务 RPC。**可重试**：等待连接就绪后重试即可。
     * 上层必须显示本地化的「连接中」提示，不得把内部状态名透给用户。
     */
    data class SessionNotReady(val msg: String) : SdkError(msg)
}
