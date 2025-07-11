package io.github.pshevche.spokk.fixtures.compilation

import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
data class SpokkTransformationResult(val actual: SpokkCompilationResult, val expected: SpokkCompilationResult) {

    fun isSuccess() =
        (actual.compilation.exitCode == KotlinCompilation.ExitCode.OK && expected.compilation.exitCode == KotlinCompilation.ExitCode.OK)

    val actualDump
        get() = actual.irDump

    val expectedDump
        get() = expected.irDump
}
