package io.privchat.sdk.kotlin.sample.privchat

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

    actual fun navigateToMainTabPage(): Boolean {
        val appCtx = ctx ?: return false
        return runCatching {
            val intent = Intent().apply {
                setClassName(appCtx.packageName, "io.privchat.sdk.kotlin.sample.KuiklyRenderActivity")
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
