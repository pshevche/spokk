plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "spokk"

includeBuild("gradle/plugins")

include("spokk-core")
include("spokk-specs")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
