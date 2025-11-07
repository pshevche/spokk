@io.github.pshevche.spockk.lang.internal.SpecMetadata
class MultiFeatureSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `feature 1`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }

    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(1)
    fun `feature 2`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }

    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(2)
    fun `feature 3`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
