import io.github.pshevche.spockk.lang.then
import io.github.pshevche.spockk.lang.`when`

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
