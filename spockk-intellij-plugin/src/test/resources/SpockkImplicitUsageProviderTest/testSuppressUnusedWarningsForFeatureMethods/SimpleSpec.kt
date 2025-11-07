import io.github.pshevche.spockk.lang.expect

class SimpleSpec {

    fun <caret>`successful feature`() {
        expect
        assert(true)
    }
}
