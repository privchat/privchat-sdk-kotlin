package io.privchat.sdk.kotlin.sample.base

import com.tencent.kuikly.core.pager.Pager
import com.tencent.kuikly.core.module.Module
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.reactive.handler.*

internal abstract class BasePager : Pager() {
    private var nightModel: Boolean? by observable(null)
    private var modulesInitialized = false

    override fun createExternalModules(): Map<String, Module>? {
        val externalModules = hashMapOf<String, Module>()
        externalModules[BridgeModule.MODULE_NAME] = BridgeModule()
        return externalModules
    }

    override fun didInit() {
        // 对于嵌套的 Pager（通过 addChild 添加的），需要手动初始化模块
        // 因为 onCreatePager 不会被调用，所以 initModule() 也不会被调用
        // 判断逻辑：
        // 1. 根 Pager：通过 onCreatePager 创建，pageName 会被设置（在 PagerManager.createPager 中）
        // 2. 嵌套 Pager：通过 addChild 添加，pageName 保持为空（默认值）
        // 注意：嵌套 Pager 的 pagerId 会被设置为父 Pager 的 pagerId，所以不能通过 pagerId.isEmpty() 判断
        if (!modulesInitialized && pageName.isEmpty() && pagerId.isNotEmpty()) {
            // 这是一个嵌套 Pager（pageName 为空但 pagerId 不为空），需要初始化模块 creator
            initModuleForNestedPager()
            modulesInitialized = true
        }
        super.didInit()
    }

    private fun initModuleForNestedPager() {
        // 调用 Pager 的私有方法 initModule() 的等价逻辑
        // 由于 initModule() 是私有的，我们需要手动调用其逻辑
        initCoreModulesForNestedPager()
        initExternalModulesForNestedPager()
    }

    private fun initCoreModulesForNestedPager() {
        // 注册核心模块的 creator（与 Pager.initCoreModules() 相同）
        registerModule(com.tencent.kuikly.core.module.ModuleConst.NOTIFY, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.NotifyModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.MEMORY, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.MemoryCacheModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.SHARED_PREFERENCES, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.SharedPreferencesModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.SNAPSHOT, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.SnapshotModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.ROUTER, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.RouterModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.NETWORK, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.NetworkModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.CODEC, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.CodecModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.CALENDAR, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.CalendarModule()
            }
        })
        // ReflectionModule 是 internal 的，无法从外部包访问，跳过注册
        // 如果需要使用，可以通过反射创建，但通常业务代码不需要直接使用
        registerModule(com.tencent.kuikly.core.module.ModuleConst.PERFORMANCE, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.PerformanceModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.TURBO_DISPLAY, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.TurboDisplayModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.FONT, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.FontModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.VSYNC, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.VsyncModule()
            }
        })
        registerModule(com.tencent.kuikly.core.module.ModuleConst.BACK_PRESS, object : com.tencent.kuikly.core.pager.IModuleCreator {
            override fun createModule(): com.tencent.kuikly.core.module.Module {
                return com.tencent.kuikly.core.module.BackPressModule()
            }
        })
    }

    private fun initExternalModulesForNestedPager() {
        // 外部模块（如 BridgeModule）通过 createExternalModules() 返回
        // 但由于 modulesMap 是私有的，我们无法直接添加
        // 实际上，外部模块会在第一次 acquireModule 时通过 createModuleIfNeed 创建
        // 但为了确保外部模块也能正常工作，我们需要通过反射或其他方式处理
        // 暂时先只初始化核心模块，外部模块会在需要时通过 createModuleIfNeed 创建
        // 注意：createExternalModules() 返回的模块需要直接添加到 modulesMap
        // 但由于 modulesMap 是私有的，我们无法直接访问
        // 所以外部模块的初始化需要在其他地方处理
    }

    override fun created() {
        super.created()
        // 不在 created() 中初始化 nightModel，避免在 pageData 未完全初始化时访问
        // isNightMode() 会在真正需要时延迟初始化
    }

    override fun themeDidChanged(data: JSONObject) {
        super.themeDidChanged(data)
        nightModel = data.optBoolean(IS_NIGHT_MODE_KEY)
    }

    // 是否为夜间模式
    override fun isNightMode(): Boolean {
        if (nightModel == null) {
            // 安全地访问 pageData.params，如果未初始化则使用默认值 false
            // 使用 try-catch 捕获任何异常（包括 UninitializedPropertyAccessException）
            nightModel = try {
                pageData.params.optBoolean(IS_NIGHT_MODE_KEY)
            } catch (e: Exception) {
                // pageData 尚未完全初始化，使用默认值
                false
            }
        }
        return nightModel!!
    }

    // 不开启调试UI模式
    override fun debugUIInspector(): Boolean {
        return false
    }

    companion object {
        const val IS_NIGHT_MODE_KEY = "isNightMode"
    }

}