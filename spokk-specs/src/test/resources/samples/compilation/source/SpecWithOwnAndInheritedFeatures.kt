abstract class BaseSpec {
    fun `inherited feature`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}
class Spec : BaseSpec() {
    fun `own feature`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}
