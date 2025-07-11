package io.github.pshevche.spokk.fixtures.e2e

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import kotlin.io.path.createTempDirectory

class Workspace {

    val projectDir = createTempDirectory("spokk-specs")
    private val settingsFile = projectDir.resolve("settings.gradle.kts").toFile()
    private val buildFile = projectDir.resolve("build.gradle.kts").toFile()

    fun setup() {
        configurePluginRepositories()
        applySpokkPlugin()
    }

    fun run(vararg args: String): BuildResult {
        return GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withArguments(*args)
            .forwardOutput()
            .build()
    }

    private fun configurePluginRepositories() {
        settingsFile.writeText(
            """
            pluginManagement {
                repositories {
                    gradlePluginPortal()
                    maven {
                        url = uri("${System.getProperty("spokk.pluginRepo")}")
                    }
                }
            }
            """.trimIndent()
        )
    }

    private fun applySpokkPlugin() {
        buildFile.writeText(
            """
            plugins {
                id("io.github.pshevche.spokk") version "latest.integration"
            }
            """.trimIndent()
        )
    }
}
