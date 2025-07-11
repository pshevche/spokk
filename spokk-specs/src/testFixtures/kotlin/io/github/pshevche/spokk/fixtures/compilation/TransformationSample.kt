package io.github.pshevche.spokk.fixtures.compilation

import com.tschuchort.compiletesting.SourceFile

data class TransformationSample(val source: SourceFile, val expected: SourceFile = source)