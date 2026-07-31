package com.netonstream.privchat.sdk

import kotlin.test.Test
import kotlin.test.assertEquals
import uniffi.privchat_sdk_ffi.ConnectionState as CoreConnectionState

class LocalSessionRestorePlanTest {
    @Test
    fun authenticated_state_is_reused_only_while_transport_is_alive() {
        assertEquals(
            LocalSessionRestorePlan.ReuseAuthenticated,
            planLocalSessionRestore(CoreConnectionState.AUTHENTICATED, true),
        )
        assertEquals(
            LocalSessionRestorePlan.ConnectAndResume,
            planLocalSessionRestore(CoreConnectionState.AUTHENTICATED, false),
        )
    }

    @Test
    fun stale_or_new_session_reconnects_before_authentication() {
        assertEquals(
            LocalSessionRestorePlan.ConnectAndResume,
            planLocalSessionRestore(CoreConnectionState.NEW, false),
        )
        assertEquals(
            LocalSessionRestorePlan.ConnectAndResume,
            planLocalSessionRestore(CoreConnectionState.CONNECTED, false),
        )
    }

    @Test
    fun live_connected_session_can_authenticate_without_an_extra_connect() {
        assertEquals(
            LocalSessionRestorePlan.AuthenticatePersistedSession,
            planLocalSessionRestore(CoreConnectionState.CONNECTED, true),
        )
        assertEquals(
            LocalSessionRestorePlan.AuthenticatePersistedSession,
            planLocalSessionRestore(CoreConnectionState.LOGGED_IN, true),
        )
    }
}
