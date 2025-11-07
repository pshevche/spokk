package io.github.pshevche.spockk.samples

import io.github.pshevche.spockk.lang.expect

class <caret>SimpleSpec {

    fun `successful feature`() {
        expect
        assert(true)
    }

    fun `failing feature`() {
        expect
        assert(false)
    }
}
