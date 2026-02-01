# Privchat SDK Kotlin Sample（KuiklyUI）

基于 **KuiklyUI** 的 4 页面结构（与 Android/Swift 一致）：
1. 登录/注册页
2. 会话列表页
3. 好友列表页
4. 会话详情页

## 结构

```
sample/
├── src/                    # sample 库（KuiklyUI 页面）
│   ├── commonMain/
│   ├── androidMain/
│   └── iosMain/
├── androidApp/             # Android 启动器
│   └── src/main/...
└── iosApp/                 # iOS 启动器（Xcode + CocoaPods）
    ├── Podfile
    └── iosApp/...
```

## 运行

### Android
```bash
cd privchat-sdk-kotlin
./gradlew :sample-androidApp:installDebug
```
或用 Android Studio 打开项目，选择 `sample-androidApp` 运行。

### iOS
```bash
cd privchat-sdk-kotlin
./gradlew :sample:podGen
cd sample/iosApp
pod install
```
然后用 Xcode 打开 `iosApp.xcworkspace`，选择模拟器或真机运行。
