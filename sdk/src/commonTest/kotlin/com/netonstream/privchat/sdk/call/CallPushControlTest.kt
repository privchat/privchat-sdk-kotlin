package com.netonstream.privchat.sdk.call

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.*
import kotlin.test.*

class CallPushControlTest {
    private fun transport(block: (String, String) -> Result<String>) = object : CallRpcTransport {
        override suspend fun call(route: String, body: String): Result<String> = block(route, body)
    }
    @Test fun registerUsesTypedPlatformAndAuthenticatedDeviceRoute() = runTest {
        val sent = mutableListOf<Pair<String, String>>()
        val control = RpcCallPushControl(transport { route, body ->
            sent += route to body
            Result.success("""{"registered":true,"future_field":1}""")
        })
        for ((platform, wire) in listOf(CallPushPlatform.FCM to "fcm", CallPushPlatform.APNS_VOIP to "apns_voip")) {
            assertTrue(control.register(CallPushRegisterRequest(platform, "private-token")).getOrThrow().registered)
            assertEquals("call/push/register", sent.last().first)
            val body = Json.parseToJsonElement(sent.last().second).jsonObject
            assertEquals(setOf("platform", "token"), body.keys)
            assertEquals(wire, body["platform"]!!.jsonPrimitive.content)
            assertEquals("private-token", body["token"]!!.jsonPrimitive.content)
        }
    }

    @Test fun unregisterUsesEmptyBodyAndPreservesFalseAcknowledgement() = runTest {
        val control = RpcCallPushControl(transport { route, body ->
            assertEquals("call/push/unregister", route)
            assertEquals("{}", body)
            Result.success("""{"registered":false}""")
        })
        assertFalse(control.unregister().getOrThrow().registered)
    }

    @Test fun unavailableAndMalformedResponsesNeverBecomeSuccess() = runTest {
        val unavailable = IllegalStateException("ServiceUnavailable")
        assertSame(unavailable, RpcCallPushControl(transport { _, _ -> Result.failure(unavailable) }).unregister().exceptionOrNull())
        for (body in listOf("{}", "null", "{\"registered\":\"yes\"}", "not-json")) {
            assertTrue(RpcCallPushControl(transport { _, _ -> Result.success(body) }).unregister().isFailure)
        }
        assertSame(unavailable, RpcCallPushControl(transport { _, _ -> throw unavailable }).unregister().exceptionOrNull())
    }

    @Test fun cancellationRemainsCancellationForThrownAndResultFailures() = runTest {
        for (returned in listOf(false, true)) {
            val cancel = CancellationException("cancelled")
            val control = RpcCallPushControl(transport { _, _ -> if (returned) Result.failure(cancel) else throw cancel })
            assertSame(cancel, assertFailsWith<CancellationException> { control.unregister() })
        }
    }

    @Test fun requestDescriptionRedactsToken() {
        assertFalse(CallPushRegisterRequest(CallPushPlatform.FCM, "secret-token").toString().contains("secret-token"))
    }
}
