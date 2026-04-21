package com.netonstream.privchat.sdk.kotlin.sample

import com.netonstream.privchat.sdk.kotlin.sample.privchat.runOnMain

actual fun runOnKuiklyContext(block: () -> Unit) {
    runOnMain(block)
}
