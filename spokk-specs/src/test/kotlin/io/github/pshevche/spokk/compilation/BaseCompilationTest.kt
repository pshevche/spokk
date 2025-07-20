package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.compile
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
abstract class BaseCompilationTest {

    protected fun assertTransformation(sample: TransformationSample) {
        val actual = transform(sample.source)
        val expected = compile(sample.expected)
        val aDump = actual.irDump
        val eDump = expected.irDump

        assert(actual.isSuccess() && expected.isSuccess())
        assert(aDump == eDump)
    }
}
