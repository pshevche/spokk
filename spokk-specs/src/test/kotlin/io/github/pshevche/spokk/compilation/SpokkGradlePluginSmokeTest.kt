package io.github.pshevche.spokk.compilation

import kotlin.test.Test

class SpokkGradlePluginSmokeTest : BaseGradlePluginTest() {

    @Test
    fun `smoke test`() {
        run("tasks")
    }
}
