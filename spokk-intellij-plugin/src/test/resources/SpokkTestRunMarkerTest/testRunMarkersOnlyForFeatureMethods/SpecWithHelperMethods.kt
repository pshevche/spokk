import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`

class SpecWithHelperMethods {

    fun `successful feature`() {
        `when`
        helper()

        then
        assert(true)
    }

    fun helper() {
        println("Running SpecWithHelperMethods")
    }
}
