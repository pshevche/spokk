abstract class BaseSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `inherited feature 1`() {
        assert(true)
    }
}

abstract class IntermediateSpec : BaseSpec() {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(1)
    fun `inherited feature 2`() {
        assert(true)
    }
}

@io.github.pshevche.spockk.lang.internal.SpecMetadata
class Spec : IntermediateSpec() {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(2)
    fun `own feature`() {
        assert(true)
    }
}
