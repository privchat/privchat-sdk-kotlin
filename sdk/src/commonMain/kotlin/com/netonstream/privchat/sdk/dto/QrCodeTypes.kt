package com.netonstream.privchat.sdk.dto

/**
 * QR_CODE_SPEC v1.4 — DTO type aliases.
 *
 * The uniffi-generated data classes under `uniffi.privchat_sdk_ffi.*` are
 * already in their final shape (qrKey / qrCode / userId / ... lowerCamelCase
 * with sensible nullability). Re-exporting them under this dto package
 * keeps the public Kotlin SDK surface in a single import root
 * (`com.netonstream.privchat.sdk.dto.*`), without forcing UI code to learn
 * the `uniffi.privchat_sdk_ffi` package.
 *
 * `GroupQrCodeJoinResult` deliberately stays as a hand-written DTO
 * (its own file, predates v1.4) because [PrivchatClient.joinGroupByQrcode]
 * already maps from FFI shape there — keeping the alias for it would
 * collide with the existing data class.
 */

typealias UserQrCodeGetView = uniffi.privchat_sdk_ffi.UserQrCodeGetView
typealias UserQrCodeRefreshView = uniffi.privchat_sdk_ffi.UserQrCodeRefreshView
typealias UserQrCodeResolveView = uniffi.privchat_sdk_ffi.UserQrCodeResolveView
typealias GroupQrCodeGetView = uniffi.privchat_sdk_ffi.GroupQrCodeGetView
typealias GroupQrCodeRefreshView = uniffi.privchat_sdk_ffi.GroupQrCodeRefreshView

/**
 * Local QR matrix produced by [com.netonstream.privchat.sdk.PrivchatClient.qrEncodeMatrix].
 *
 * Wire shape:
 * - `size`  — modules per side (always > 0)
 * - `cells` — row-major, length `size * size`. `0` = light, `1` = dark.
 *
 * Quiet zone is NOT baked in — the UI layer should add the standard
 * 4-module margin via container padding when rendering.
 */
typealias QrMatrixView = uniffi.privchat_sdk_ffi.QrMatrixView
