package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.compilation.TransformationSample.Companion.sampleFromResource
import io.github.pshevche.spokk.lang.expect

class FeatureOrderingCompilationTest : BaseCompilationTest() {

    fun `captures feature declaration order within a single spec`() {
        expect
        assertTransformation(sampleFromResource("MultiFeatureSpec"))
    }

    fun `inherited features have the lower declaration ordinal`() {
        expect
        assertTransformation(sampleFromResource("SpecWithOwnAndInheritedFeatures"))
    }

}
