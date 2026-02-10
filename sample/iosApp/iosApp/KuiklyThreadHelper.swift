import Foundation
import sample

/// KuiklyUI 线程调度
@objc public class KuiklyThreadHelper: NSObject {
    @objc public static func performOnContextQueue(_ block: @escaping () -> Void) {
        KuiklyRenderViewController.perform(onKuiklyContextQueue: block)
    }

    @objc public static func registerToKotlin() {
        // 注入 Kuikly context 队列调度器（Swift 中类名为 HybridLiveStreamPageBridge_iosKt），使 runOnKuiklyContext / setTimeout 从后台线程调用时不会触发 assertContextQueue
        HybridLiveStreamPageBridge_iosKt.setIOSScheduler { action in
            KuiklyThreadHelper.performOnContextQueue { _ = action() }
        }
    }
}
