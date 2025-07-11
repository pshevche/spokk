package io.github.pshevche.spokk

import io.github.pshevche.spokk.fixtures.e2e.Workspace
import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`

class SpokkGradlePluginSmokeTest {

    val workspace = Workspace()

    fun `smoke test`() {
        given
        workspace.setup()

        `when`
        workspace.run("tasks")

        then
        assert(true)
    }
}