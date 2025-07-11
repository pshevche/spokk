package io.github.pshevche.spokk.fixtures.compilation

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.github.pshevche.spokk.compilation.SpokkCompilerPlugin
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
object CompilationUtils {
    fun transform(sample: TransformationSample) =
        SpokkTransformationResult(
            compile(sample.source, SpokkCompilerPlugin()),
            compile(sample.expected)
        )

    fun compile(
        sourceFile: SourceFile,
        vararg additionalPlugins: CompilerPluginRegistrar
    ): SpokkCompilationResult {
        val irDumpBuilder = StringBuilder()
        val result = KotlinCompilation().apply {
            sources = listOf(sourceFile)
            compilerPluginRegistrars = additionalPlugins.toList() + listOf(IrDumpCapturingCompilerPlugin(irDumpBuilder))
            inheritClassPath = true
        }.compile()
        return SpokkCompilationResult(result, irDumpBuilder.toString())
    }
}
