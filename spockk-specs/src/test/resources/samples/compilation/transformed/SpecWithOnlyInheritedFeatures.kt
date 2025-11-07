abstract class BaseSpec {
    @io.github.pshevche.spockk.lang.internal.FeatureMetadata(0)
    fun `inherited feature`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
@io.github.pshevche.spockk.lang.internal.SpecMetadata
class Spec : BaseSpec()
