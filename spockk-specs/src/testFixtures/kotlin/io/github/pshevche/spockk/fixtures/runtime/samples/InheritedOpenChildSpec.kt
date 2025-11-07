package io.github.pshevche.spockk.fixtures.runtime.samples

import io.github.pshevche.spockk.lang.expect

class InheritedOpenChildSpec : InheritedOpenParentSpec() {

    fun `successful child feature`() {
        expect
        assert(true)
    }

    fun `failing child feature`() {
        expect
        assert(false)
    }
}
