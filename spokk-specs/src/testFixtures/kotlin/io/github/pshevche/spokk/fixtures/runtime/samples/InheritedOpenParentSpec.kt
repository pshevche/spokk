package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.internal.FeatureMetadata

open class InheritedOpenParentSpec {

    @FeatureMetadata
    fun `successful parent feature`() {
        assert(true)
    }

    @FeatureMetadata
    fun `failing parent feature`() {
        assert(false)
    }
}