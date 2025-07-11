package io.github.pshevche.spokk.fixtures.e2e

import kotlin.io.path.createTempDirectory

class Workspace {

    val projectDir = createTempDirectory("spokk-specs")
    private val settingsFile = projectDir.resolve("settings.gradle.kts").toFile()
    private val buildFile = projectDir.resolve("build.gradle.kts").toFile()

    fun configurePluginRepositories() {
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

    fun applySpokkPlugin() {
        buildFile.writeText(
            """
            plugins {
                id("io.github.pshevche.spokk") version "0.1"
            }
            """.trimIndent()
        )
    }
}
