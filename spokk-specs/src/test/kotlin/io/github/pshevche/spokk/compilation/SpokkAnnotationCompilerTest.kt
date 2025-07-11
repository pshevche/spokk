package io.github.pshevche.spokk.compilation

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.compile
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
@SpecMetadata
class SpokkAnnotationCompilerTest {

    @FeatureMetadata
    fun `keeps classes without spokk labels untransformed`() {
        given
        val source = kotlin(
            "NonSpec.kt", """
class NonSpec {
    fun `some method`() {
        println("some method")
    }
}   
        """.trimIndent()
        )

        `when`
        val transformationResult = transform(source)
        val compilationResult = compile(source)

        then
        assert(transformationResult.compilation.exitCode == KotlinCompilation.ExitCode.OK)
        assert(transformationResult.irDump == compilationResult.irDump)
    }

    @FeatureMetadata
    fun `annotates classes with feature methods with @SpecMetadata`() {

    }

    @FeatureMetadata
    fun `annotates features with spokk labels with @FeatureMetadata`() {

    }

    @FeatureMetadata
    fun `does not annotate abstract spec classes`() {

    }

}
