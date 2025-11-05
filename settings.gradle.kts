pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.develocity") version ("4.2.2")
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
        termsOfUseAgree = "yes"
        publishing {
            onlyIf {
                it.buildResult.failures.isNotEmpty()
            }
        }
    }
}

rootProject.name = "spokk"

includeBuild("gradle/plugins")

include("spokk-compiler-plugin")
include("spokk-core")
include("spokk-intellij-plugin")
include("spokk-gradle-plugin")
include("spokk-specs")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
