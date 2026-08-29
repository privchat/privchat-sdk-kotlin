package com.netonstream.privchat.sdk

/**
 * 配置 - 与 SDK_API_CONTRACT 对齐，commonMain 使用纯 Kotlin 类型
 */
data class ServerEndpoint(
    val protocol: TransportProtocol,
    val host: String,
    val port: Int,
    val path: String? = null,
    val useTls: Boolean = false,
)

enum class TransportProtocol { Quic, Tcp, WebSocket }

data class PrivchatConfig(
    val dataDir: String,
    /** 可选。不设置时 SDK 使用内置 embedded migrations，无需外部 SQL 文件 */
    val assetsDir: String? = null,
    val serverEndpoints: List<ServerEndpoint>,
    val connectionTimeout: ULong = 30u,
    val heartbeatInterval: ULong = 30u,
    val debugMode: Boolean = false,
    /**
     * 允许的服务端 SPKI pin（base64 SHA-256），QUIC 与 TLS/TCP 共用。
     *
     * 🔴 正式构建缺 pin 时 Rust 侧直接拒绝连接：裸 IP + 自签证书部署下证书链
     * 校验必然失败，pinning 是唯一能证明「对面就是我们的网关」的手段。
     * 支持多个是为了密钥轮换：先发同时接受 current+next 的客户端，再切服务端
     * 密钥，旧 pin 下一版删除。
     */
    val spkiPins: List<String> = emptyList(),
)
