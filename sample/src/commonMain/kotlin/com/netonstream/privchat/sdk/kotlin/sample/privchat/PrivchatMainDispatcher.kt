package om.netonstream.privchat.sdk.kotlin.sample.privchat

/**
 * 在主线程执行（用于将 SDK 异步结果回写到 observable）
 */
expect fun runOnMain(block: () -> Unit)
