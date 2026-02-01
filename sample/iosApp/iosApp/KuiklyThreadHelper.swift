import Foundation
import sample

/// KuiklyUI 线程调度 - Privchat Sample 无需额外桥接
@objc public class KuiklyThreadHelper: NSObject {
    @objc public static func performOnContextQueue(_ block: @escaping () -> Void) {
        KuiklyRenderViewController.perform(onKuiklyContextQueue: block)
    }

    @objc public static func registerToKotlin() {
        // Privchat Sample 无需 HybridLiveStream 桥接
    }
}
