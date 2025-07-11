package io.github.pshevche.spokk.fixtures.e2e

import java.nio.file.Files
import java.nio.file.Paths
import org.gradle.testkit.runner.GradleRunner
import org.intellij.lang.annotations.Language

class Workspace {

    val projectDir = Files.createDirectories(
        Paths.get(
            System.getProperty("spokk.workspaceDir"),
            "workspace-${System.currentTimeMillis()}"
        )
    )
    private val settingsFile = projectDir.resolve("settings.gradle.kts").toFile()
    private val buildFile = projectDir.resolve("build.gradle.kts").toFile()
    private val sourcesDir = projectDir.resolve("src/test/kotlin").toFile()

    fun setup() {
        configureRepositories()
        applyPlugins()
        configureTestTasks()
    }

    fun build(vararg args: String) = runner(args.toList()).build()

    fun buildAndFail(vararg args: String) = runner(args.toList()).buildAndFail()

    private fun runner(args: List<String>): GradleRunner {
        return GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withArguments(args)
            .forwardOutput()
    }

    private fun configureRepositories() {
        settingsFile.writeText(
            """
            pluginManagement {
                repositories {
                    gradlePluginPortal()
                    ${repoUnderTest("gradlePluginRepo")}
                }
            }
            """.trimIndent()
        )
        buildFile.writeText(
            """
            repositories {
                mavenCentral()
                ${repoUnderTest("compilerPluginRepo")}
                ${repoUnderTest("coreRepo")}
            }
            """.trimIndent()
        )
    }

    private fun repoUnderTest(property: String) = """
        maven {
            url = uri("${System.getProperty("spokk.${property}")}")
        }
    """.trimIndent()

    private fun applyPlugins() {
        val kotlinVersion = System.getProperty("spokk.kotlinVersion")
        buildFile.appendText(
            """
                
            plugins {
                kotlin("jvm") version "$kotlinVersion"
                kotlin("plugin.power-assert") version "$kotlinVersion"
                id("io.github.pshevche.spokk") version "latest.integration"
            }
            """.trimIndent()
        )
    }

    private fun configureTestTasks() {
        buildFile.appendText(
            """
                
            dependencies {
                testImplementation("io.github.pshevche.spokk:spokk-core:latest.integration")
                testRuntimeOnly("org.junit.platform:junit-platform-launcher:${System.getProperty("spokk.junitPlatformVersion")}")
            }
            tasks.test {
                useJUnitPlatform {
                    includeEngines.add("spokk")
                }
                testLogging {
                    events.addAll(listOf(
                        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, 
                        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED, 
                        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
                    ))
                    displayGranularity = 0
                }
            }
            """.trimIndent()
        )
    }

    fun addSuccessfulSpec(name: String = "SuccessfulSpec") {
        writeSpec(
            name, """
            class $name {
                fun `passing feature 1`() {
                    io.github.pshevche.spokk.lang.expect
                    assert(true)
                }

                fun `passing feature 2`() {
                    io.github.pshevche.spokk.lang.expect
                    assert(true)
                }

            }
        """.trimIndent()
        )
    }

    fun addFailingSpec(name: String = "FailingSpec") {
        writeSpec(
            name, """
            class $name {
                fun `failing feature 1`() {
                    io.github.pshevche.spokk.lang.expect
                    assert(false)
                }

                fun `failing feature 2`() {
                    io.github.pshevche.spokk.lang.expect
                    assert(false)
                }

            }
        """.trimIndent()
        )
    }

    private fun writeSpec(name: String, @Language("kotlin") content: String) {
        Files.createDirectories(sourcesDir.toPath())
        sourcesDir.resolve("${name}.kt").writeText(content)
    }
}
