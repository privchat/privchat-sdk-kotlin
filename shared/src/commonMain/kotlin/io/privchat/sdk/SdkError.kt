package io.privchat.sdk

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
}
