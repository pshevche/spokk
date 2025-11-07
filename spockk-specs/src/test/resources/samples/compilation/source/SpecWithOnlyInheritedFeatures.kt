abstract class BaseSpec {
    fun `inherited feature`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
class Spec : BaseSpec()
