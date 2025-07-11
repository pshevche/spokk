package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.fixtures.compilation.Samples
import io.github.pshevche.spokk.fixtures.compilation.TransformationSample
import io.github.pshevche.spokk.lang.expect
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class SpokkAnnotationCompilerTest {

    private fun assertTransformation(sample: TransformationSample) {
        val result = transform(sample)
        assert(result.isSuccess())
        assert(result.actualDump == result.expectedDump)
    }

    fun `keeps classes without spokk labels untransformed`() {
        expect
        assertTransformation(Samples.NON_SPEC)
    }

    fun `annotates classes with feature methods with @SpecMetadata`() {
        expect
        assertTransformation(Samples.SPEC_WITH_SINGLE_FEATURE)
    }

    fun `annotates features with spokk labels with @FeatureMetadata`() {
        expect
        assertTransformation(Samples.specWithSingleFeature("given"))
        assertTransformation(Samples.specWithSingleFeature("given(\"description\")"))
        assertTransformation(Samples.specWithSingleFeature("expect"))
        assertTransformation(Samples.specWithSingleFeature("expect(\"description\")"))
        assertTransformation(Samples.specWithSingleFeature("`when`"))
        assertTransformation(Samples.specWithSingleFeature("`when`(\"description\")"))
        assertTransformation(Samples.specWithSingleFeature("then"))
        assertTransformation(Samples.specWithSingleFeature("then(\"description\")"))
    }

    fun `does not annotate abstract spec classes`() {
        expect
        assertTransformation(Samples.ABSTRACT_CLASS_WITH_FEATURES)
        assertTransformation(Samples.OPEN_CLASS_WITH_FEATURES)
    }

    fun `annotates child classes with @SpecMetadata if parent contains features`() {
        expect
        assertTransformation(Samples.SPEC_WITH_INHERITED_FEATURES)
    }

}
