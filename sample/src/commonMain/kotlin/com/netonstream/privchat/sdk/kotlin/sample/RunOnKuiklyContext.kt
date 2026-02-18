package om.netonstream.privchat.sdk.kotlin.sample

/**
 * 在 Kuikly 渲染上下文（context queue）上执行 block。
 * 用于在任意线程执行完逻辑后，再在 Kuikly 要求的线程上调用 setTimeout 等 callNative，避免 assertContextQueue 断言失败。
 */
expect fun runOnKuiklyContext(block: () -> Unit)
