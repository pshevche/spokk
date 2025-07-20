package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.compile
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.compilation.TestDataFactory.specWithSingleFeature
import io.github.pshevche.spokk.compilation.TransformationSample.Companion.sampleFromResource
import io.github.pshevche.spokk.lang.expect
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class SpokkAnnotationCompilerTest {

    private fun assertTransformation(sample: TransformationSample) {
        val actual = transform(sample.source)
        val expected = compile(sample.expected)
        assert(actual.isSuccess() && expected.isSuccess())
        assert(actual.irDump == actual.irDump)
    }

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
    }

    fun `does not annotate abstract spec classes`() {
        expect
        assertTransformation(sampleFromResource("AbstractBaseSpec"))
        assertTransformation(sampleFromResource("OpenBaseSpec"))
    }

    fun `annotates child classes with @SpecMetadata if parent contains features`() {
        expect
        assertTransformation(sampleFromResource("SpecWithInheritedFeatures"))
    }

}
