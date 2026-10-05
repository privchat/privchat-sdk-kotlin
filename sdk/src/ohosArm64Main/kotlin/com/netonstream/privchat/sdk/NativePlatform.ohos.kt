@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.netonstream.privchat.sdk

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import platform.posix.CLOCK_REALTIME
import platform.posix.F_OK
import platform.posix.O_CREAT
import platform.posix.O_RDONLY
import platform.posix.O_TRUNC
import platform.posix.O_WRONLY
import platform.posix.access
import platform.posix.clock_gettime
import platform.posix.close
import platform.posix.open
import platform.posix.read
import platform.posix.rename
import platform.posix.stat
import platform.posix.timespec
import platform.posix.unlink
import platform.posix.write
import kotlinx.cinterop.ByteVar

@OptIn(ExperimentalForeignApi::class)
internal actual fun nativeNowMillis(): Long = memScoped {
    val ts = alloc<timespec>()
    clock_gettime(CLOCK_REALTIME, ts.ptr)
    ts.tv_sec * 1000L + ts.tv_nsec / 1_000_000L
}

internal actual fun nativeFileExists(path: String): Boolean = access(path, F_OK) == 0

internal actual fun nativeRemoveFile(path: String) {
    unlink(path)
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun nativeCopyFile(from: String, to: String): Boolean {
    val src = open(from, O_RDONLY)
    if (src < 0) return false
    val dst = open(to, O_WRONLY or O_CREAT or O_TRUNC, 0x1A4) // 0644
    if (dst < 0) {
        close(src)
        return false
    }
    var ok = true
    memScoped {
        val bufSize = 256 * 1024
        val buf = allocArray<ByteVar>(bufSize)
        while (true) {
            val n = read(src, buf, bufSize.toULong())
            if (n < 0) { ok = false; break }
            if (n == 0L) break
            var off = 0L
            while (off < n) {
                val w = write(dst, buf.plus(off), (n - off).toULong())
                if (w <= 0) { ok = false; break }
                off += w
            }
            if (!ok) break
        }
    }
    close(src)
    if (close(dst) != 0) ok = false
    if (!ok) unlink(to)
    return ok
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun nativeFileSize(path: String): ULong? = memScoped {
    val st = alloc<stat>()
    if (stat(path, st.ptr) == 0) st.st_size.toULong() else null
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun nativeWriteFileAtomically(path: String, data: ByteArray): Boolean {
    // Same contract as Foundation's atomically = true: write a temp file, then rename.
    val tmp = "$path.tmp"
    val fd = open(tmp, O_WRONLY or O_CREAT or O_TRUNC, 0x1A4) // 0644
    if (fd < 0) return false
    var ok = true
    if (data.isNotEmpty()) {
        data.usePinned { pinned ->
            var off = 0
            while (off < data.size) {
                val w = write(fd, pinned.addressOf(off), (data.size - off).toULong())
                if (w <= 0) { ok = false; break }
                off += w.toInt()
            }
        }
    }
    if (close(fd) != 0) ok = false
    if (ok && rename(tmp, path) != 0) ok = false
    if (!ok) unlink(tmp)
    return ok
}

// HarmonyOS has no POSIX-level media API. Duration is optional metadata (iOS does not
// fill width and height either); the receiver still plays the video.
internal actual fun nativeVideoDurationSeconds(path: String): Long? = null
