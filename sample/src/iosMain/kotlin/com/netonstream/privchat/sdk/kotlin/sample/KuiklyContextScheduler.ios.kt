package om.netonstream.privchat.sdk.kotlin.sample

/**
 * 由 Swift 通过 registerKuiklyContextScheduler 注入的 Kuikly context 队列调度器。
 * runOnKuiklyContext 使用此调度器，供 LoginPage 等在非 context 线程执行完逻辑后安全调用 setTimeout。
 */
object KuiklyContextScheduler {
    var scheduler: ((() -> Unit) -> Unit)? = null
}

/**
 * 供 Swift 在 registerToKotlin() 中调用，注入 Kuikly context 队列调度器。
 * 例如：SampleKt.registerKuiklyContextScheduler { block in KuiklyThreadHelper.performOnContextQueue(block) }
 */
fun registerKuiklyContextScheduler(scheduler: (() -> Unit) -> Unit) {
    KuiklyContextScheduler.scheduler = scheduler
}
