package io.github.pshevche.spokk.fixtures.compilation

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin

object Samples {

    val NON_SPEC = TransformationSample(
        kotlin(
            "NonSpec.kt", """
                class NonSpec {
                    fun `some method`() {
                        println("some method")
                    }
                }   
            """
        ),
    )

    val SPEC_WITH_SINGLE_FEATURE = TransformationSample(
        kotlin(
            "Spec.kt", """
                class Spec {
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }   
            """
        ),
        kotlin(
            "Spec.kt", """
                @io.github.pshevche.spokk.lang.internal.SpecMetadata
                class Spec {
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }
            """
        )
    )

}