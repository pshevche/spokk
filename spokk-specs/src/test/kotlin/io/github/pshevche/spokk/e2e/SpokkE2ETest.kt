package io.github.pshevche.spokk.e2e

import io.github.pshevche.spokk.fixtures.e2e.Workspace
import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import org.gradle.testkit.runner.TaskOutcome

class SpokkE2ETest {

    val workspace = Workspace()

    fun `can execute spokk tests as part of the Gradle build`() {
        given
        workspace.setup()
        workspace.addSuccessfulSpec()
        workspace.addFailingSpec()

        `when`
        val result = workspace.buildAndFail("test")

        then
        assert(result.task(":test")!!.outcome == TaskOutcome.FAILED)
        result.output.let {
            assert(it.contains("SuccessfulSpec > passing feature 1 PASSED"))
            assert(it.contains("SuccessfulSpec > passing feature 2 PASSED"))
            assert(it.contains("FailingSpec > failing feature 1 FAILED"))
            assert(it.contains("FailingSpec > failing feature 2 FAILED"))
        }
    }
}
