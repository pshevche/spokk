package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.compilation.TestDataFactory.specWithSingleFeature
import io.github.pshevche.spokk.compilation.TransformationSample.Companion.sampleFromResource
import io.github.pshevche.spokk.lang.expect

class SpokkAnnotationCompilationTest : BaseCompilationTest() {

    fun `keeps classes without spokk labels untransformed`() {
        expect
        assertTransformation(sampleFromResource("NonSpec"))
    }

    fun `annotates classes with feature methods with @SpecMetadata`() {
        expect
        assertTransformation(sampleFromResource("SingleFeatureSpec"))
    }

    fun `annotates features with spokk labels with @FeatureMetadata`() {
        expect
        assertTransformation(specWithSingleFeature("expect"))
    }

    fun `does not annotate abstract spec classes`() {
        expect
        assertTransformation(sampleFromResource("AbstractBaseSpec"))
        assertTransformation(sampleFromResource("OpenBaseSpec"))
    }

    fun `annotates child classes with @SpecMetadata if parent contains features`() {
        expect
        assertTransformation(sampleFromResource("SpecWithOnlyInheritedFeatures"))
    }

}
