@io.github.pshevche.spokk.lang.internal.SpecMetadata
class MultiFeatureSpec {
    @io.github.pshevche.spokk.lang.internal.FeatureMetadata(0)
    fun `feature 1`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }

    @io.github.pshevche.spokk.lang.internal.FeatureMetadata(1)
    fun `feature 2`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }

    @io.github.pshevche.spokk.lang.internal.FeatureMetadata(2)
    fun `feature 3`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}
