package com.netonstream.privchat.sdk

// The few things the native client needs from the OS that Kotlin/Native has no common
// API for. Apple implements them with Foundation/AVFoundation (appleMain); HarmonyOS
// with POSIX (ohosArm64Main, built by privchat-app's ohos build).

/** Wall-clock time in epoch milliseconds. */
internal expect fun nativeNowMillis(): Long

internal expect fun nativeFileExists(path: String): Boolean

internal expect fun nativeRemoveFile(path: String)

/**
 * Copy through the file system, never through memory: attachments can be videos of
 * hundreds of MB, and an extra in-memory copy gets the app killed on a phone.
 */
internal expect fun nativeCopyFile(from: String, to: String): Boolean

internal expect fun nativeFileSize(path: String): ULong?

internal expect fun nativeWriteFileAtomically(path: String, data: ByteArray): Boolean

/** Whole seconds, at least 1; null when unknown. */
internal expect fun nativeVideoDurationSeconds(path: String): Long?
