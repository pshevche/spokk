package io.github.pshevche.spokk.e2e

import io.github.pshevche.spokk.fixtures.e2e.Workspace
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner

open class BaseGradlePluginTest {

    val workspace = Workspace()

    fun run(vararg args: String): BuildResult {
        return GradleRunner.create()
            .withProjectDir(workspace.projectDir.toFile())
            .withArguments(*args)
            .forwardOutput()
            .build()
    }
}
