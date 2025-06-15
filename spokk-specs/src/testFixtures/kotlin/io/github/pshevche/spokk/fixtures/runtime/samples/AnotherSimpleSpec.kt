package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata

@SpecMetadata
class AnotherSimpleSpec {

    @FeatureMetadata
    fun `successful feature`() {
        assert(true)
    }

    @FeatureMetadata
    fun `failing feature`() {
        assert(false)
    }
}
