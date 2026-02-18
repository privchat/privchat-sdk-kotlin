package om.netonstream.privchat.sdk

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
    val fileApiBaseUrl: String? = null,
)
