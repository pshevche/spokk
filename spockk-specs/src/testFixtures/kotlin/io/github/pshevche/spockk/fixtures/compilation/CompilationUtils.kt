package io.github.pshevche.spockk.fixtures.compilation

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.github.pshevche.spockk.compilation.SpockkCompilerPlugin
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
object CompilationUtils {
    fun transform(source: SourceFile) = compile(source, SpockkCompilerPlugin())

    fun compile(
        sourceFile: SourceFile,
        vararg additionalPlugins: CompilerPluginRegistrar,
    ): SpockkCompilationResult {
        val irDumpBuilder = StringBuilder()
        val result = KotlinCompilation().apply {
            sources = listOf(sourceFile)
            compilerPluginRegistrars = additionalPlugins.toList() + listOf(IrDumpCapturingCompilerPlugin(irDumpBuilder))
            inheritClassPath = true
        }.compile()
        return SpockkCompilationResult(result, irDumpBuilder.toString())
    }
}
