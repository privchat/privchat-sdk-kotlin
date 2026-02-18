package om.netonstream.privchat.sdk.kotlin.sample.privchat

import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDefaults

actual object PrivchatPlatform {
    actual fun init(context: Any?) {
        // iOS: 可选，暂无
    }

    actual fun dataDir(): String {
        return "${NSHomeDirectory()}/Library/Application Support"
    }

    actual fun assetsDir(): String {
        return "."
    }

    actual fun deviceId(): String {
        val prefs = NSUserDefaults.standardUserDefaults
        var id = prefs.stringForKey("privchat_device_id")
        if (id == null) {
            id = platform.Foundation.NSUUID().UUIDString()
            prefs.setObject(id, "privchat_device_id")
            prefs.synchronize()
        }
        return id
    }

    actual fun localAccountUids(): List<String> = emptyList()

    actual fun setCurrentUid(uid: String): Boolean = false

    actual fun currentUid(): String? = null

    actual fun navigateToMainTabPage(): Boolean = false
}
