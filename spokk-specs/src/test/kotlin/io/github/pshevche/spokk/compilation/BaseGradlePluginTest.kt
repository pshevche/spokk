package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.fixtures.Workspace
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
