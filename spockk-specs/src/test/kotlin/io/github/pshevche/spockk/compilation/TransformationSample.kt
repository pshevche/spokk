package io.github.pshevche.spockk.compilation

import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin

data class TransformationSample(val source: SourceFile, val expected: SourceFile) {
    companion object {
        fun sampleFromResource(fileName: String): TransformationSample {
            return TransformationSample(
                resource("samples/compilation/source/${fileName}.kt"),
                resource("samples/compilation/transformed/${fileName}.kt"),
            )
        }

        private fun resource(resourcePath: String): SourceFile = kotlin(
            resourcePath.substring(resourcePath.lastIndexOf("/") + 1),
            this::class.java.classLoader.getResourceAsStream(resourcePath)!!.bufferedReader().readText()
        )
    }
}
