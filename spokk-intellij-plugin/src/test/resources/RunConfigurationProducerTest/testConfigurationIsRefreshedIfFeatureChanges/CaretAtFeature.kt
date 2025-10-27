package io.github.pshevche.spokk.samples

import io.github.pshevche.spokk.lang.expect

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
