# KuiklyUI 组件集成指南

本文档说明如何在你的 app 中使用 KuiklyUI 框架实现类似微博的界面功能。

## 功能清单

✅ **1. 底部导航** - `MainTabPage.kt`  
✅ **2. 顶部左右滑动切换** - `HomePage.kt`  
✅ **3. 下拉刷新、上拉加载更多** - `FeedListPage.kt`  
✅ **4. 点击切换导航页面** - `MainTabPage.kt`  
✅ **5. 点击跳转到页面详情** - `FeedItemView.kt` + `DetailPage.kt`

## 核心组件说明

### 1. 底部导航 (MainTabPage)

**实现方式：**
- 使用 `PageList` 组件实现页面容器
- 自定义底部 TabBar（View + Image + Text）
- 通过 `scrollToPageIndex()` 方法切换页面

**关键代码：**
```kotlin
PageList {
    attr {
        flexDirectionRow() // 横向布局
        scrollEnable(false) // 禁用滑动，通过点击切换
        keepItemAlive(true) // 保持页面存活
    }
    event {
        pageIndexDidChanged {
            // 更新选中的 tab
        }
    }
}
```

**参考文档：**
- [PageList 组件](https://kuikly.tds.qq.com/API/components/override.html#pagelist分页列表)

### 2. 顶部 Tabs 和左右滑动 (HomePage)

**实现方式：**
- 使用 `Tabs` 组件实现顶部标签栏
- 使用 `PageList` 组件实现左右滑动
- 通过 `scrollParams` 实现 Tabs 与 PageList 的联动

**关键代码：**
```kotlin
Tabs {
    attr {
        scrollParams(it) // 与 PageList 联动
    }
    TabItem {
        event {
            click {
                pageListRef?.view?.scrollToPageIndex(i, true)
            }
        }
    }
}

PageList {
    event {
        scroll {
            scrollParams = it // 记录滚动参数
        }
    }
}
```

**参考文档：**
- [Tabs 组件](https://kuikly.tds.qq.com/API/components/override.html#tabs标签栏)
- [PageList 组件](https://kuikly.tds.qq.com/API/components/override.html#pagelist分页列表)

### 3. 下拉刷新和上拉加载 (FeedListPage)

**实现方式：**
- 使用 `Refresh` 组件实现下拉刷新
- 使用 `FooterRefresh` 组件实现上拉加载更多
- 监听 `refreshStateDidChange` 事件处理刷新状态

**关键代码：**
```kotlin
List {
    Refresh {
        event {
            refreshStateDidChange {
                when (it) {
                    RefreshViewState.REFRESHING -> {
                        // 请求数据
                        requestFeeds(0) {
                            refreshRef.view?.endRefresh()
                        }
                    }
                }
            }
        }
    }
    
    FooterRefresh {
        event {
            refreshStateDidChange {
                when (it) {
                    FooterRefreshState.REFRESHING -> {
                        // 加载更多数据
                        requestFeeds(page) {
                            footerRefreshRef.view?.endRefresh(state)
                        }
                    }
                }
            }
        }
    }
}
```

**参考文档：**
- [Refresh 组件](https://kuikly.tds.qq.com/API/components/override.html#refresh下拉刷新组件)
- [FooterRefresh 组件](https://kuikly.tds.qq.com/API/components/override.html#footerrefresh列表尾部刷新)

### 4. 页面跳转 (RouterModule)

**实现方式：**
- 使用 `RouterModule` 的 `openPage()` 方法跳转
- 通过 `pageData` 传递参数

**关键代码：**
```kotlin
// 跳转
val params = urlParams("id=${item.id}&title=${item.title}")
val pageData = JSONObject()
params.forEach {
    pageData.put(it.key, it.value)
}

acquireModule<RouterModule>(RouterModule.MODULE_NAME)
    .openPage("DetailPage", pageData)

// 返回
acquireModule<RouterModule>(RouterModule.MODULE_NAME)
    .closePage()
```

**参考文档：**
- [RouterModule](https://kuikly.tds.qq.com/API/modules/override.html#routermodule)

## 使用步骤

### 1. 启动主页面

在你的应用入口，设置 `MainTabPage` 为启动页面：

```kotlin
// 在 iOSApp.swift 或 Android 的 MainActivity 中
// 设置启动页面为 "MainTabPage"
```

### 2. 自定义图标和样式

修改 `MainTabPage.kt` 中的图标路径和颜色：

```kotlin
private val pageIcons = listOf(
    "ic_home.png",        // 替换为你的图标路径
    "ic_discover.png",
    "ic_message.png",
    "ic_profile.png"
)
```

### 3. 自定义数据源

修改 `FeedListPage.kt` 中的 `requestFeeds()` 方法，连接你的真实数据源：

```kotlin
private fun requestFeeds(page: Int, complete: () -> Unit) {
    // 替换为你的网络请求
    yourApiService.getFeeds(page) { feedList ->
        if (page == 0) {
            feeds.clear()
        }
        feeds.addAll(feedList)
        complete()
    }
}
```

### 4. 自定义详情页

根据你的业务需求，修改 `DetailPage.kt` 中的内容展示。

## 文件结构

```
pages/
├── MainTabPage.kt          # 主页面（底部导航）
├── DetailPage.kt           # 详情页面
├── home/
│   └── HomePage.kt         # 首页（顶部 Tabs）
└── feed/
    ├── FeedListPage.kt     # 列表页面（刷新/加载）
    └── FeedItemView.kt     # 列表项组件
```

## 注意事项

1. **页面注册**：确保所有页面都使用 `@Page` 注解注册
2. **资源文件**：图标文件需要放在 `commonMain/assets` 目录下
3. **状态管理**：使用 `observable` 和 `observableList` 管理状态
4. **生命周期**：在 `created()` 中初始化，在 `viewWillUnload()` 中清理

## 参考资源

- [KuiklyUI 官方文档](https://kuikly.tds.qq.com/)
- [组件 API 文档](https://kuikly.tds.qq.com/API/components/override.html)
- [Module API 文档](https://kuikly.tds.qq.com/API/modules/override.html)

## 示例代码位置

所有示例代码都在 `sample/src/commonMain/kotlin/io/privchat/sdk/kotlin/sample/pages/` 目录下，你可以直接参考和修改。

## 新 FFI API 调用示例（账号本地目录能力）

以下示例对应 Rust 新增的本地账号接口：
- `listLocalAccounts()`
- `wipeCurrentUserFull()`

建议放在 `SettingPage` 的按钮事件中调用。

```kotlin
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatMainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private fun debugListLocalAccounts() {
    val client = PrivchatClientHolder.client ?: return
    CoroutineScope(PrivchatMainDispatcher.main).launch {
        val result = client.listLocalAccounts()
        result.onSuccess { accounts ->
            accounts.forEach {
                println(
                    "[local-account] uid=${it.uid} active=${it.isActive} " +
                        "createdAt=${it.createdAt} lastLoginAt=${it.lastLoginAt}"
                )
            }
        }.onFailure { e ->
            println("[local-account] list failed: ${e.message}")
        }
    }
}
```

```kotlin
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatClientHolder
import om.netonstream.privchat.sdk.kotlin.sample.privchat.PrivchatMainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private fun debugWipeCurrentUserFull() {
    val client = PrivchatClientHolder.client ?: return
    CoroutineScope(PrivchatMainDispatcher.main).launch {
        val result = client.wipeCurrentUserFull()
        result.onSuccess {
            // 清理 sample 侧持有引用
            PrivchatClientHolder.clear()
            println("[local-account] wipe current user success")
        }.onFailure { e ->
            println("[local-account] wipe current user failed: ${e.message}")
        }
    }
}
```

如果你当前 `shared` 绑定代码里还没有这两个方法，先同步/刷新一次 rust->uniffi->kotlin 生成产物，再把上面的片段粘到页面按钮事件即可。
