plugins {
    `kotlin-dsl`
}

fun Provider<PluginDependency>.asDependency(): Provider<String> =
    this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

dependencies {
    implementation(libs.plugins.kotlin.jvm.asDependency())
    implementation(libs.plugins.detekt.asDependency())
}
