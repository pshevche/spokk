@io.github.pshevche.spockk.lang.internal.SpecMetadata
class MultiFeatureSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `feature 1`() {
        assert(true)
    }

    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(1)
    fun `feature 2`() {
        assert(true)
    }

    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(2)
    fun `feature 3`() {
        assert(true)
    }
}
