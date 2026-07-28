package com.netonstream.privchat.sdk.dto

data class LocalAccountInfo(
    val uid: String,
    val createdAt: Long,
    val lastLoginAt: Long,
    val isActive: Boolean,
    /** 展示名（nickname）。 */
    val displayName: String? = null,
    /** username。 */
    val username: String? = null,
    /** 上次使用的登录方式（"BUILTIN" / "PLATFORM"）。 */
    val loginMode: String? = null,
    /** 上次登录填的标识：账密=username，短信=手机号。会话失效时用来回填表单。 */
    val loginIdentifier: String? = null,
) {
    /**
     * 界面显示名。优先级 displayName > username > uid —— uid 是协议标识，
     * 只有前两者都没有时才允许露出来。
     */
    val displayLabel: String
        get() = displayName?.takeIf { it.isNotBlank() }
            ?: username?.takeIf { it.isNotBlank() }
            ?: uid
}
