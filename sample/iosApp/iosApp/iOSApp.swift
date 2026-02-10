import SwiftUI
import UIKit
import sample

@main
struct iOSApp: App {
    init() {
        KuiklyThreadHelper.registerToKotlin()
        // 注册 Kuikly 页面
        PageRegistrar.shared.register()
        // 初始化 PrivchatSampleState 路径
        let dirs = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let docDir = dirs.first?.path ?? "/tmp"
        let dataDir = "\(docDir)/privchat_data"
        let assetsDir = "\(docDir)/privchat_assets"
        try? FileManager.default.createDirectory(atPath: dataDir, withIntermediateDirectories: true)
        try? FileManager.default.createDirectory(atPath: assetsDir, withIntermediateDirectories: true)
        let deviceId = UIDevice.current.identifierForVendor?.uuidString ?? UUID().uuidString
        PrivchatSampleState.shared.setPaths(dataDir: dataDir, assetsDir: assetsDir, deviceId: deviceId)
        print("✅ [iOSApp] Privchat Sample 已启动")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
