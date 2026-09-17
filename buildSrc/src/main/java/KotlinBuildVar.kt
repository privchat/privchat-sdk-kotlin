object Version {

    private const val KUIKLY_VERSION = "2.28.0"
    private const val KOTLIN_VERSION = "2.1.21"
    private const val KOTLIN_OHOS_VERSION = "2.0.21-ohos"

    /**
     * 获取 Kuikly 版本号，版本号规则：${shortVersion}-${kotlinVersion}
     * 适用于 core、core-ksp、core-annotation、core-render-android
     */
    fun getKuiklyVersion(): String {
        return "$KUIKLY_VERSION-$KOTLIN_VERSION"
    }

    /**
     * 获取 Kuikly Ohos版本号
     */
    fun getKuiklyOhosVersion(): String {
        return "$KUIKLY_VERSION-$KOTLIN_OHOS_VERSION"
    }
}

object BuildPlugin {
    // Gradle 插件与运行时同版本、同 Kotlin 后缀发布（2.5.0 之后不再有独立的
    // 2.4.x 插件线；旧坐标 2.4.0-2.0.21 在镜像上已经拿不到）。
    val kuikly by lazy {
        "com.tencent.kuikly-open:core-gradle-plugin:${Version.getKuiklyVersion()}"
    }
}
