abstract class BaseSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `inherited feature 1`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}

abstract class IntermediateSpec : BaseSpec() {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(1)
    fun `inherited feature 2`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}

@io.github.pshevche.spockk.lang.internal.SpecMetadata
class Spec : IntermediateSpec() {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(2)
    fun `own feature`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
