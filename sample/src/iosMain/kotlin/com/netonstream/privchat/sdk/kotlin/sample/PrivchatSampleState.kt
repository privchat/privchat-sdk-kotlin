package om.netonstream.privchat.sdk.kotlin.sample

/**
 * iOS 平台的 Sample 状态管理
 * 用于从 Swift 层传递路径配置到 Kotlin 层
 */
object PrivchatSampleState {
    var dataDir: String = ""
        private set
    var assetsDir: String = ""
        private set
    var deviceId: String = ""
        private set

    fun setPaths(dataDir: String, assetsDir: String, deviceId: String) {
        this.dataDir = dataDir
        this.assetsDir = assetsDir
        this.deviceId = deviceId
    }
}
