package io.github.pshevche.spokk.compilation

import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin

object TestDataFactory {

    fun specWithFeatureBody(featureBody: String): SourceFile = kotlin(
        "Spec.kt", """
            class Spec {
                fun `some feature`() {
                    ${featureBody}
                }
            } 
        """.trimIndent()
    )

    fun specWithSingleFeature(label: String) = TransformationSample(
        kotlin(
            "Spec.kt", """
                class Spec {
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.${label}
                        assert(true)
                    }
                }   
            """
        ),
        kotlin(
            "Spec.kt", """
                @io.github.pshevche.spokk.lang.internal.SpecMetadata
                class Spec {
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata(0)
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.${label}
                        assert(true)
                    }
                }
            """
        )
    )

}
