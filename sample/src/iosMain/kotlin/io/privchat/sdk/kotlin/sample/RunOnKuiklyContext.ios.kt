package io.privchat.sdk.kotlin.sample

import io.privchat.sdk.kotlin.sample.privchat.runOnMain

actual fun runOnKuiklyContext(block: () -> Unit) {
    KuiklyContextScheduler.scheduler?.invoke(block) ?: runOnMain(block)
}
