package io.privchat.sdk.kotlin.sample.privchat

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual fun runOnMain(block: () -> Unit) {
    dispatch_async(dispatch_get_main_queue()) {
        block()
    }
}
