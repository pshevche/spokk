package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata

@SpecMetadata
class InheritedOpenChildSpec : InheritedOpenParentSpec() {

    @FeatureMetadata
    fun `successful child one feature`() {
        assert(true)
    }

    @FeatureMetadata
    fun `failing child one feature`() {
        assert(false)
    }
}