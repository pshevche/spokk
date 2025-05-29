package io.github.pshevche.spokk.plugin

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@Suppress("unused")
class SpokkGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { listOf() }
    }

    override fun getCompilerPluginId(): String = SpokkCompilerPluginConfig.SPOKK_COMPILER_PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        SpokkCompilerPluginConfig.SPOKK_COMPILER_PLUGIN_GROUP,
        SpokkCompilerPluginConfig.SPOKK_COMPILER_ARTIFACT_ID,
        SpokkCompilerPluginConfig.SPOKK_COMPILER_VERSION
    )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}
