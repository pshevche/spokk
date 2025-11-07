abstract class BaseSpec {
    fun `inherited feature`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
class Spec : BaseSpec() {
    fun `own feature`() {
        io.github.pshevche.spockk.lang.expect
        assert(true)
    }
}
