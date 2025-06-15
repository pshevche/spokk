package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.expect
import io.github.pshevche.spokk.lang.internal.FeatureMetadata

abstract class InheritedAbstractParentSpec {

    @FeatureMetadata
    fun `successful parent feature`() {
        expect
        assert(true)
    }

    @FeatureMetadata
    fun `failing parent feature`() {
        expect
        assert(false)
    }
}
