package io.github.pshevche.spokk.plugin

import io.github.pshevche.spokk.fixtures.Workspace
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import kotlin.test.BeforeTest

open class BaseGradlePluginTest {

    val workspace = Workspace()

    @BeforeTest
    fun setup() {
        workspace.configurePluginRepositories()
        workspace.applySpokkPlugin()
    }

    fun run(vararg args: String): BuildResult {
        return GradleRunner.create()
            .withProjectDir(workspace.projectDir.toFile())
            .withArguments(*args)
            .forwardOutput()
            .build()
    }
}
