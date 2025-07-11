package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.expect

class InheritedOpenChildSpec : InheritedOpenParentSpec() {

    fun `successful child one feature`() {
        expect
        assert(true)
    }

    fun `failing child one feature`() {
        expect
        assert(false)
    }
}
