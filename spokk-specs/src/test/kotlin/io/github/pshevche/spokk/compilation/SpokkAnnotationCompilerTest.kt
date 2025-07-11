package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.fixtures.compilation.Samples
import io.github.pshevche.spokk.lang.expect
import io.github.pshevche.spokk.lang.given
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class SpokkAnnotationCompilerTest {

    fun `keeps classes without spokk labels untransformed`() {
        given
        val result = transform(Samples.NON_SPEC)

        expect
        assert(result.isSuccess())
        assert(result.actualDump == result.expectedDump)
    }

    fun `annotates classes with feature methods with @SpecMetadata`() {
        given
        val result = transform(Samples.SPEC_WITH_SINGLE_FEATURE)

        expect
        assert(result.isSuccess())
        assert(result.actualDump == result.expectedDump)
    }

    fun `annotates features with spokk labels with @FeatureMetadata`() {

    }

    fun `does not annotate abstract spec classes`() {

    }

    fun `annotates child classes with @SpecMetadata if parent contains features`() {

    }

}
