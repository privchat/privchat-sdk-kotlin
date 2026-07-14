package com.netonstream.privchat.sdk

/**
 * Public façade over the small, non-`PrivchatClient` Rust SDK entrypoints so that consumers
 * (the app) never import the generated `uniffi.privchat_sdk_ffi` package directly — keeping the
 * dependency chain strictly App → privchat-sdk-kotlin → privchat-sdk → privchat-protocol.
 */

/** Version string of the underlying Rust SDK (`privchat-sdk`). */
fun privchatSdkVersion(): String = uniffi.privchat_sdk_ffi.sdkVersion()

/**
 * Decode a QR code from a grayscale **luma** buffer (`width * height` bytes) via the shared Rust
 * `rxing` decoder. Platform image acquisition + luma extraction stay in the caller (they are genuine
 * platform capabilities); only the cross-platform decode is delegated here.
 *
 * Returns the decoded text, or `null` for the two *recoverable* decode outcomes — an invalid frame
 * size or no decodable QR in the frame. Any other error propagates.
 */
fun privchatQrDecodeLuma(width: Int, height: Int, luma: ByteArray): String? =
    try {
        uniffi.privchat_sdk_ffi.qrDecodeLuma(width.toUInt(), height.toUInt(), luma)
    } catch (_: uniffi.privchat_sdk_ffi.QrDecodeException.InvalidDimensions) {
        null
    } catch (_: uniffi.privchat_sdk_ffi.QrDecodeException.DecoderException) {
        null
    }
