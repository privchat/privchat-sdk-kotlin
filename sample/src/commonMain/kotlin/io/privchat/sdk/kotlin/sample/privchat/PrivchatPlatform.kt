package io.privchat.sdk.kotlin.sample.privchat

/**
 * 平台相关路径与设备 ID
 */
expect object PrivchatPlatform {
    /** 初始化（Android: Application，iOS: 可选） */
    fun init(context: Any?)

    /** 数据目录（SQLite 等） */
    fun dataDir(): String

    /** 资源目录（可选，未使用可返回 "."） */
    fun assetsDir(): String

    /** 设备 ID（用于 login/register） */
    fun deviceId(): String
}
