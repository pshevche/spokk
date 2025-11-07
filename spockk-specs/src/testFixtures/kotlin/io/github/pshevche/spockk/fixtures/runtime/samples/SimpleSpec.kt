package io.github.pshevche.spockk.fixtures.runtime.samples

import io.github.pshevche.spockk.lang.expect

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
