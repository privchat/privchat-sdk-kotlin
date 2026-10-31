package com.netonstream.privchat.sdk

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

/**
 * Every client call maps its failure through [sdkErrorOf]. Production, 2026-10-31: the contacts
 * tab restarts its refresh when the connection state changes; the cancelled `syncEntities` came
 * back as `SdkError.Generic("syncEntities failed: The coroutine scope left the composition")`,
 * the app could no longer tell it was a cancellation, and showed "operation failed".
 */
class SdkErrorCancellationTest {
    private fun noPlatform(t: Throwable): SdkError? = null

    @Test
    fun cancellationIsRethrownNotMapped() {
        val cancelled = CancellationException("The coroutine scope left the composition")

        val thrown = assertFailsWith<CancellationException> { sdkErrorOf("syncEntities failed", cancelled, ::noPlatform) }

        assertSame(cancelled, thrown, "the same cancellation, not a wrapped copy")
    }

    @Test
    fun realFailuresStillBecomeSdkErrors() {
        val network = SdkError.Network("transport down", 0x0201)
        assertSame(network, sdkErrorOf("syncEntities failed", network, ::noPlatform))

        val mapped = SdkError.Disconnected
        assertSame(mapped, sdkErrorOf("syncEntities failed", IllegalStateException("ffi"), { mapped }))

        assertEquals(SdkError.Generic("syncEntities failed: boom"),
            sdkErrorOf("syncEntities failed", IllegalStateException("boom"), ::noPlatform))
    }

    /** The client's own shape: runCatching around the FFI call, failures mapped in fold. */
    private suspend fun clientCall(ffi: suspend () -> Unit): Result<Unit> =
        runCatching { ffi() }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(sdkErrorOf("syncEntities failed", it, ::noPlatform)) },
        )

    @Test
    fun aCancelledCallEndsTheCoroutineInsteadOfReturningAFailure() = runTest {
        val started = CompletableDeferred<Unit>()
        var result: Result<Unit>? = null
        var continuedAfterCall = false
        val job = launch(start = CoroutineStart.UNDISPATCHED) {
            result = clientCall { started.complete(Unit); awaitCancellation() }
            continuedAfterCall = true
        }
        started.await()
        job.cancel()
        job.join()

        assertTrue(job.isCancelled)
        assertEquals(null, result, "a cancelled call must not hand back a failure to report")
        assertFalse(continuedAfterCall, "nothing after the call may run once it was cancelled")
    }

    @Test
    fun aNetworkFailureIsStillReturnedToTheCaller() = runTest {
        val result = clientCall { yield(); throw SdkError.Network("transport down", 0x0201) }

        assertTrue(result.isFailure)
        assertEquals(SdkError.Network("transport down", 0x0201), result.exceptionOrNull())
    }
}
