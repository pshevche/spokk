plugins {
    id("com.github.gmazzo.buildconfig")
}

group = "io.github.pshevche.spokk"
val compilerPluginArtifactId = "spokk-compiler-plugin"
version = "0.1"

buildConfig {
    packageName("io.github.pshevche.spokk.plugin")
    className("SpokkCompilerPluginConfig")

    buildConfigField("String", "SPOKK_COMPILER_PLUGIN_ID", "\"${group}:${compilerPluginArtifactId}\"")
    buildConfigField("String", "SPOKK_COMPILER_PLUGIN_GROUP", "\"${group}\"")
    buildConfigField("String", "SPOKK_COMPILER_ARTIFACT_ID", "\"${compilerPluginArtifactId}\"")
    buildConfigField("String", "SPOKK_COMPILER_VERSION", "\"${version}\"")
}
