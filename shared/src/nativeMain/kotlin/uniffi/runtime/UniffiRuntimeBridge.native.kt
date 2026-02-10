@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package uniffi.runtime

import uniffi.privchat_sdk_ffi.UniffiLib as PrivchatUniffiLib

internal object UniffiLib {
    internal object INSTANCE {
        fun ffi_uniffi_runtime_rustbuffer_alloc(
            size: Long,
            uniffiCallStatus: UniffiRustCallStatus,
        ): RustBufferByValue =
            PrivchatUniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rustbuffer_alloc(size, uniffiCallStatus)

        fun ffi_uniffi_runtime_rustbuffer_free(
            buf: RustBufferByValue,
            uniffiCallStatus: UniffiRustCallStatus,
        ) {
            PrivchatUniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rustbuffer_free(buf, uniffiCallStatus)
        }
    }
}
