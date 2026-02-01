package io.privchat.sdk

/** 连接状态 - 与 SDK_API_CONTRACT 对齐 */
enum class ConnectionState {
    Disconnected,
    Connecting,
    Connected,
    Reconnecting,
    Failed,
}
