package io.github.pshevche.spockk.fixtures.runtime.samples

import io.github.pshevche.spockk.lang.expect

class AnotherSimpleSpec {

    fun `successful feature`() {
        expect
        assert(true)
    }

    fun `failing feature`() {
        expect
        assert(false)
    }
}
