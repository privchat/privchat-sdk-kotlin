package io.privchat.sdk.kotlin.sample.privchat

import io.privchat.sdk.PrivchatClient

/**
 * 全局 Privchat 客户端持有者
 * 登录成功后由 LoginPage 设置，SessionsPage / FriendsPage 等读取
 */
internal object PrivchatClientHolder {
    var client: PrivchatClient? = null
        private set

    fun setClient(c: PrivchatClient?) {
        client = c
    }

    fun clear() {
        client = null
    }
}
