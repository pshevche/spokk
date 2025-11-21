abstract class BaseSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `inherited feature`() {
        assert(true)
    }
}

@io.github.pshevche.spockk.lang.internal.SpecMetadata
class Spec : BaseSpec()
