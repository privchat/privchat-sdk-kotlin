@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.netonstream.privchat.sdk

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.AVFoundation.AVURLAsset
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileSize
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToFile

internal actual fun nativeNowMillis(): Long = (NSDate().timeIntervalSince1970 * 1000.0).toLong()

internal actual fun nativeFileExists(path: String): Boolean =
    NSFileManager.defaultManager.fileExistsAtPath(path)

internal actual fun nativeRemoveFile(path: String) {
    NSFileManager.defaultManager.removeItemAtPath(path, null)
}

internal actual fun nativeCopyFile(from: String, to: String): Boolean =
    NSFileManager.defaultManager.copyItemAtPath(from, to, null)

internal actual fun nativeFileSize(path: String): ULong? =
    (NSFileManager.defaultManager.attributesOfItemAtPath(path, null)
        ?.get(NSFileSize) as? NSNumber)?.unsignedLongLongValue

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal actual fun nativeWriteFileAtomically(path: String, data: ByteArray): Boolean {
    val nativeData = if (data.isEmpty()) {
        NSData()
    } else {
        data.usePinned { pinned -> NSData.create(bytes = pinned.addressOf(0), length = data.size.toULong()) }
    }
    return nativeData.writeToFile(path, atomically = true)
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun nativeVideoDurationSeconds(path: String): Long? {
    val asset = AVURLAsset.URLAssetWithURL(NSURL.fileURLWithPath(path), null)
    return asset.duration.useContents {
        val ts = timescale
        if (ts != 0) {
            val secs = value.toDouble() / ts.toDouble()
            if (secs.isFinite() && secs > 0.0) secs.toLong().coerceAtLeast(1) else null
        } else null
    }
}
