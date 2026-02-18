package om.netonstream.privchat.sdk.kotlin.sample.privchat

import android.os.Handler
import android.os.Looper

actual fun runOnMain(block: () -> Unit) {
    Handler(Looper.getMainLooper()).post(block)
}
