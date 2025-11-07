package io.github.pshevche.spockk.samples

import io.github.pshevche.spockk.lang.expect

class SimpleSpec {

    fun <caret>`successful feature`() {
        expect
        assert(true)
    }

    fun `failing feature`() {
        expect
        assert(false)
    }
}
