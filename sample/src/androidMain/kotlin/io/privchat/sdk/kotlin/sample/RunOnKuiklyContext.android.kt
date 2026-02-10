package io.privchat.sdk.kotlin.sample

import io.privchat.sdk.kotlin.sample.privchat.runOnMain

actual fun runOnKuiklyContext(block: () -> Unit) {
    runOnMain(block)
}
