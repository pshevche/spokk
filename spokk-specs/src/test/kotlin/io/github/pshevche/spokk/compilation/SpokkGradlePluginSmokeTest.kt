package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`

@SpecMetadata
class SpokkGradlePluginSmokeTest : BaseGradlePluginTest() {

    @FeatureMetadata
    fun `smoke test`() {
        given
        workspace.configurePluginRepositories()
        workspace.applySpokkPlugin()

        `when`
        run("tasks")

        then
        assert(true)
    }
}
