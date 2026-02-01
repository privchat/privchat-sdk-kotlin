package io.privchat.sdk.kotlin.sample

import android.app.Application
import io.privchat.sdk.kotlin.sample.privchat.PrivchatPlatform

class KRApplication : Application() {

    init {
        application = this
    }

    override fun onCreate() {
        super.onCreate()
        PrivchatPlatform.init(applicationContext)
        // 初始化混合直播桥接
        LiveStreamBridge.initialize(this)
        println("✅ Android LiveStreamBridge 已初始化")
    }

    companion object {
        lateinit var application: Application
    }
}