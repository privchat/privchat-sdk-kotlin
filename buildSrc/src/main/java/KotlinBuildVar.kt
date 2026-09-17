import java.io.File

/**
 * KuiklyUI 版本**不在本仓库声明**，一律从同级检出的 gearui-kit 读
 * （`../gearui-kit/gradle.properties` 的 KUIKLY_VERSION / KUIKLY_KOTLIN / KUIKLY_KOTLIN_OHOS），
 * 与 privchat-app 的规则一致：谁消费 gearui-kit，谁就跟它的 KuiklyUI 版本走。
 *
 * 曾经这里自己钉了一份（2.23.2），gearui-kit 早已到 2.27.0，两边渐行渐远却没人察觉——
 * 版本只要写两处，就一定会漂。找不到 gearui-kit 或缺键直接失败，不给任何默认值。
 */
object Version {

    private fun gearUiProperties(rootDir: File): String {
        val props = File(rootDir, "../gearui-kit/gradle.properties")
        require(props.exists()) {
            "gearui-kit not found at ${props.path}. It is a required sibling checkout: the KuiklyUI version is owned there."
        }
        return props.readText()
    }

    private fun read(text: String, key: String, path: String): String =
        Regex("^$key=(.+)$", RegexOption.MULTILINE).find(text)?.groupValues?.get(1)?.trim()
            ?: error("$key missing from $path")

    /**
     * Kuikly 版本号，规则：${shortVersion}-${kotlinVersion}
     * 适用于 core、core-ksp、core-annotations、core-render-android、core-gradle-plugin
     */
    fun getKuiklyVersion(rootDir: File): String {
        val text = gearUiProperties(rootDir)
        return "${read(text, "KUIKLY_VERSION", "gearui-kit/gradle.properties")}-${read(text, "KUIKLY_KOTLIN", "gearui-kit/gradle.properties")}"
    }

    /** Kuikly ohos 版本号 */
    fun getKuiklyOhosVersion(rootDir: File): String {
        val text = gearUiProperties(rootDir)
        return "${read(text, "KUIKLY_VERSION", "gearui-kit/gradle.properties")}-${read(text, "KUIKLY_KOTLIN_OHOS", "gearui-kit/gradle.properties")}"
    }
}

object BuildPlugin {
    /** Gradle 插件与运行时同版本、同 Kotlin 后缀发布（旧的独立 2.4.x 插件线在镜像上已拿不到）。 */
    fun kuikly(rootDir: File): String =
        "com.tencent.kuikly-open:core-gradle-plugin:${Version.getKuiklyVersion(rootDir)}"
}
