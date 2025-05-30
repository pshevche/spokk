package io.github.pshevche.spokk.plugin

import kotlin.test.Test

class SpokkGradlePluginSmokeTest : BaseGradlePluginTest() {

    @Test
    fun `smoke test`() {
        run("tasks")
    }
}
