package io.github.pshevche.spokk.compilation

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.compile
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.lang.given
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class SpokkAnnotationCompilerTest {

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
        assert(compilationResult.compilation.exitCode == KotlinCompilation.ExitCode.OK)
        assert(transformationResult.irDump == compilationResult.irDump)
    }

    fun `annotates classes with feature methods with @SpecMetadata`() {
        given
        val source = kotlin(
            "Spec.kt", """
class Spec {
    fun `some feature`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}   
        """.trimIndent()
        )
        val result = kotlin(
            "Spec.kt", """
@io.github.pshevche.spokk.lang.internal.SpecMetadata
class Spec {
    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
    fun `some feature`() {
        io.github.pshevche.spokk.lang.expect
        assert(true)
    }
}
        """.trimIndent()
        )

        `when`
        val transformationResult = transform(source)
        val compilationResult = compile(result)

        then
        assert(transformationResult.compilation.exitCode == KotlinCompilation.ExitCode.OK)
        assert(compilationResult.compilation.exitCode == KotlinCompilation.ExitCode.OK)
        assert(transformationResult.irDump == compilationResult.irDump)
    }

    fun `annotates features with spokk labels with @FeatureMetadata`() {

    }

    fun `does not annotate abstract spec classes`() {

    }

    fun `annotates child classes with @SpecMetadata if parent contains features`() {

    }

}
