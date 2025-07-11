package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.expect

abstract class InheritedAbstractParentSpec {

    fun `successful parent feature`() {
        expect
        assert(true)
    }

    fun `failing parent feature`() {
        expect
        assert(false)
    }
}
