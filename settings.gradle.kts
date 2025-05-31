plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "spokk"

includeBuild("gradle/plugins")

include("spokk-compiler-plugin")
include("spokk-core")
include("spokk-gradle-plugin")
include("spokk-specs")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
