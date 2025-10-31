import io.github.pshevche.spokk.lang.expect

class SimpleSpec {

    fun <caret>`successful feature`() {
        expect
        assert(true)
    }
}
