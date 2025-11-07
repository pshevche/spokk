package io.github.pshevche.spockk.compilation

import io.github.pshevche.spockk.compilation.TransformationSample.Companion.sampleFromResource
import io.github.pshevche.spockk.lang.expect

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
