package com.netonstream.privchat.sdk.kotlin.sample

import com.tencent.kuikly.core.manager.PagerManager
import com.netonstream.privchat.sdk.kotlin.sample.pages.DetailPage
import com.netonstream.privchat.sdk.kotlin.sample.pages.MainTabPage
import com.netonstream.privchat.sdk.kotlin.sample.pages.friend.FriendsPage
import com.netonstream.privchat.sdk.kotlin.sample.pages.login.LoginPage
import com.netonstream.privchat.sdk.kotlin.sample.pages.profile.ProfilePage
import com.netonstream.privchat.sdk.kotlin.sample.pages.profile.SwitchAccountPage
import com.netonstream.privchat.sdk.kotlin.sample.pages.session.SessionsPage

/**
 * 手动注册 Kuikly 页面（KSP 未正确生成 triggerRegisterPages 时的兜底方案）
 */
object PageRegistrar {
    fun register() {
        PagerManager.registerPageRouter("LoginPage") { LoginPage() }
        PagerManager.registerPageRouter("MainTabPage") { MainTabPage() }
        PagerManager.registerPageRouter("SessionsPage") { SessionsPage() }
        PagerManager.registerPageRouter("FriendsPage") { FriendsPage() }
        PagerManager.registerPageRouter("ProfilePage") { ProfilePage() }
        PagerManager.registerPageRouter("SwitchAccountPage") { SwitchAccountPage() }
        PagerManager.registerPageRouter("DetailPage") { DetailPage() }
        PagerManager.registerPageRouter("router") { RouterPage() }
    }
}
