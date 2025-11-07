package io.github.pshevche.spockk.fixtures.runtime.samples

import io.github.pshevche.spockk.lang.expect

open class InheritedOpenParentSpec {

    fun `successful parent feature`() {
        expect
        assert(true)
    }

    fun `failing parent feature`() {
        expect
        assert(false)
    }
}
