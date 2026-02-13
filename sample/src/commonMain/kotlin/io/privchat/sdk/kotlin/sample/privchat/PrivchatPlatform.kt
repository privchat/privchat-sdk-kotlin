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

    /** 本地已保存账号 uid 列表（来自 dataDir/users 目录） */
    fun localAccountUids(): List<String>

    /** 设置当前账号 uid（写入 dataDir/current_user） */
    fun setCurrentUid(uid: String): Boolean

    /** 当前账号 uid（读取 dataDir/current_user） */
    fun currentUid(): String?

    /**
     * 登录成功后由平台侧直接跳转到主页面。
     * 返回 true 表示已处理跳转；false 表示调用方需自行通过 Kuikly Router 跳转。
     */
    fun navigateToMainTabPage(): Boolean
}
