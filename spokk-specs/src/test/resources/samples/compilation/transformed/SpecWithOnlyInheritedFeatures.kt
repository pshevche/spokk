abstract class BaseSpec {
    @io.github.pshevche.spokk.lang.internal.FeatureMetadata(0)
    fun `inherited feature`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}
@io.github.pshevche.spokk.lang.internal.SpecMetadata
class Spec : BaseSpec()
