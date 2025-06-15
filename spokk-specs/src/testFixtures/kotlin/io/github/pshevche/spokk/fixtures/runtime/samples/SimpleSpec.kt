package io.github.pshevche.spokk.fixtures.runtime.samples

import io.github.pshevche.spokk.lang.expect
import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata

@SpecMetadata
class SimpleSpec {

    @FeatureMetadata
    fun `successful feature`() {
        expect
        assert(true)
    }

    @FeatureMetadata
    fun `failing feature`() {
        expect
        assert(false)
    }
}
