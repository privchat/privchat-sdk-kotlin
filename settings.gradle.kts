pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://mirrors.tencent.com/nexus/repository/maven-tencent/") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://mirrors.tencent.com/nexus/repository/maven-tencent/") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "privchat-sdk-kotlin"
include(":sdk")
include(":sample")
include(":sample-androidApp")
project(":sample-androidApp").projectDir = file("sample/androidApp")
