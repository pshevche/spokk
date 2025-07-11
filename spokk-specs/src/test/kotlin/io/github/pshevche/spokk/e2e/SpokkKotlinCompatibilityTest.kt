package io.github.pshevche.spokk.e2e

import io.github.pshevche.spokk.fixtures.e2e.Workspace
import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import org.gradle.testkit.runner.TaskOutcome

class SpokkKotlinCompatibilityTest {

    val workspace = Workspace()

    fun `supports Kotlin 2_1_21`() {
        given
        workspace.setup("2.1.21")
        workspace.addSuccessfulSpec()

        `when`
        val result = workspace.build("test")

        then
        assert(result.task(":test")!!.outcome == TaskOutcome.SUCCESS)
        result.output.let {
            assert(it.contains("SuccessfulSpec > passing feature 1 PASSED"))
            assert(it.contains("SuccessfulSpec > passing feature 2 PASSED"))
        }
    }

    fun `supports Kotlin 2_0_21`() {
        given
        workspace.setup("2.0.21")
        workspace.addSuccessfulSpec()

        `when`
        val result = workspace.build("test")

        then
        assert(result.task(":test")!!.outcome == TaskOutcome.SUCCESS)
        result.output.let {
            assert(it.contains("SuccessfulSpec > passing feature 1 PASSED"))
            assert(it.contains("SuccessfulSpec > passing feature 2 PASSED"))
        }
    }

    fun `supports Kotlin 1_9_25`() {
        given
        workspace.setup("1.9.25")
        workspace.addSuccessfulSpec()

        `when`
        val result = workspace.build("test")

        then
        assert(result.task(":test")!!.outcome == TaskOutcome.SUCCESS)
        result.output.let {
            assert(it.contains("SuccessfulSpec > passing feature 1 PASSED"))
            assert(it.contains("SuccessfulSpec > passing feature 2 PASSED"))
        }
    }

    fun `supports Kotlin 1_8_22`() {
        given
        workspace.setup("1.8.22")
        workspace.addSuccessfulSpec()

        `when`
        val result = workspace.build("test")

        then
        assert(result.task(":test")!!.outcome == TaskOutcome.SUCCESS)
        result.output.let {
            assert(it.contains("SuccessfulSpec > passing feature 1 PASSED"))
            assert(it.contains("SuccessfulSpec > passing feature 2 PASSED"))
        }
    }

}
