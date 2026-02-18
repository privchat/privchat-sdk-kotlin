package om.netonstream.privchat.sdk.kotlin.sample.privchat

import android.content.Context
import android.content.Intent
import java.io.File
import java.util.UUID

actual object PrivchatPlatform {
    @Volatile
    private var ctx: Context? = null

    actual fun init(context: Any?) {
        ctx = context as? Context
    }

    actual fun dataDir(): String {
        return ctx?.filesDir?.absolutePath ?: "/data/local/tmp"
    }

    /** 已废弃：SDK 使用内置 embedded migrations，无需外部 SQL 文件 */
    actual fun assetsDir(): String = "."

    actual fun deviceId(): String {
        val prefs = ctx?.getSharedPreferences("privchat", Context.MODE_PRIVATE)
            ?: return UUID.randomUUID().toString()
        var id = prefs.getString("device_id", null)
        if (id.isNullOrEmpty()) {
            id = UUID.randomUUID().toString()
            prefs.edit().putString("device_id", id).apply()
        }
        return id
    }

    actual fun localAccountUids(): List<String> {
        val usersDir = File(dataDir(), "users")
        if (!usersDir.exists() || !usersDir.isDirectory) return emptyList()
        return usersDir.listFiles()
            ?.asSequence()
            ?.filter { it.isDirectory }
            ?.map { it.name }
            ?.sorted()
            ?.toList()
            ?: emptyList()
    }

    actual fun setCurrentUid(uid: String): Boolean {
        if (uid.isBlank()) return false
        return runCatching {
            val f = File(dataDir(), "current_user")
            f.parentFile?.mkdirs()
            f.writeText(uid)
            true
        }.getOrDefault(false)
    }

    actual fun currentUid(): String? {
        return runCatching {
            val f = File(dataDir(), "current_user")
            if (!f.exists() || !f.isFile) return null
            f.readText().trim().ifBlank { null }
        }.getOrNull()
    }

    actual fun navigateToMainTabPage(): Boolean {
        val appCtx = ctx ?: return false
        return runCatching {
            val intent = Intent().apply {
                setClassName(appCtx.packageName, "om.netonstream.privchat.sdk.kotlin.sample.KuiklyRenderActivity")
                putExtra("pageName", "MainTabPage")
                putExtra("pageData", "{}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            appCtx.startActivity(intent)
            true
        }.getOrElse {
            false
        }
    }
}
