plugins {
    id("com.github.gmazzo.buildconfig")
}

group = "io.github.pshevche.spockk"
val compilerPluginArtifactId = "spockk-compiler-plugin"
version = "0.1"

buildConfig {
    packageName("io.github.pshevche.spockk.compilation")
    className("SpockkCompilerPluginConfig")

    buildConfigField("String", "SPOCKK_COMPILER_PLUGIN_ID", "\"${group}:${compilerPluginArtifactId}\"")
    buildConfigField("String", "SPOCKK_COMPILER_PLUGIN_GROUP", "\"${group}\"")
    buildConfigField("String", "SPOCKK_COMPILER_ARTIFACT_ID", "\"${compilerPluginArtifactId}\"")
    buildConfigField("String", "SPOCKK_COMPILER_VERSION", "\"${version}\"")
}
