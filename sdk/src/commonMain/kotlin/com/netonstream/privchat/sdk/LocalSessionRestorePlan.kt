package com.netonstream.privchat.sdk

import uniffi.privchat_sdk_ffi.ConnectionState as CoreConnectionState

internal enum class LocalSessionRestorePlan {
    ReuseAuthenticated,
    ConnectAndResume,
    AuthenticatePersistedSession,
}

internal fun planLocalSessionRestore(
    state: CoreConnectionState,
    transportConnected: Boolean,
): LocalSessionRestorePlan = when {
    state == CoreConnectionState.AUTHENTICATED && transportConnected ->
        LocalSessionRestorePlan.ReuseAuthenticated

    !transportConnected ||
        state == CoreConnectionState.NEW ||
        state == CoreConnectionState.SHUTDOWN ->
        LocalSessionRestorePlan.ConnectAndResume

    else -> LocalSessionRestorePlan.AuthenticatePersistedSession
}
