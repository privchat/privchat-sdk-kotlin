package om.netonstream.privchat.sdk.kotlin.sample

import om.netonstream.privchat.sdk.kotlin.sample.privchat.runOnMain

actual fun runOnKuiklyContext(block: () -> Unit) {
    runOnMain(block)
}
