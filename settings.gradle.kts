rootProject.name = "spokk"

includeBuild("gradle/plugins")

include("spokk-compiler-plugin")
include("spokk-core")
include("spokk-intellij-plugin")
include("spokk-gradle-plugin")
include("spokk-specs")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
