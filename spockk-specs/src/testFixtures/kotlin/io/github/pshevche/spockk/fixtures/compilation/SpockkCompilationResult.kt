package io.github.pshevche.spockk.fixtures.compilation

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
data class SpockkCompilationResult(val compilation: JvmCompilationResult, val irDump: String) {

    fun isSuccess() =
        (compilation.exitCode == KotlinCompilation.ExitCode.OK)

}
