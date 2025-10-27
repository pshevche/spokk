import io.github.pshevche.spokk.lang.expect

class SimpleSpec {

    fun `successful feature`() {
        expect
        assert(true)
    }

    fun `failing feature`() {
        expect
        assert(false)
    }
}
