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
        assertTransformation(specWithSingleFeature("given"))
        assertTransformation(specWithSingleFeature("given(\"description\")"))
        assertTransformation(specWithSingleFeature("expect"))
        assertTransformation(specWithSingleFeature("expect(\"description\")"))
        assertTransformation(specWithSingleFeature("`when`"))
        assertTransformation(specWithSingleFeature("`when`(\"description\")"))
        assertTransformation(specWithSingleFeature("then"))
        assertTransformation(specWithSingleFeature("then(\"description\")"))
        assertTransformation(specWithSingleFeature("and"))
        assertTransformation(specWithSingleFeature("and(\"description\")"))
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
