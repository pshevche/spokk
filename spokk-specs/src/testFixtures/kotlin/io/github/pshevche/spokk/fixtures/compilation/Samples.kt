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

    val ABSTRACT_CLASS_WITH_FEATURES = TransformationSample(
        kotlin(
            "BaseSpec.kt", """
                abstract class BaseSpec {
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }   
            """
        ),
        kotlin(
            "BaseSpec.kt", """
                abstract class BaseSpec {
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }
            """
        )
    )

    val OPEN_CLASS_WITH_FEATURES = TransformationSample(
        kotlin(
            "BaseSpec.kt", """
                open class BaseSpec {
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }   
            """
        ),
        kotlin(
            "BaseSpec.kt", """
                open class BaseSpec {
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }
            """
        )
    )

    val SPEC_WITH_INHERITED_FEATURES = TransformationSample(
        kotlin(
            "SpecWithInheritedFeatures.kt", """
                abstract class BaseSpec {
                    fun `inherited feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }   
                class Spec : BaseSpec()
            """
        ),
        kotlin(
            "SpecWithInheritedFeatures.kt", """
                abstract class BaseSpec {
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
                    fun `inherited feature`() {
                        io.github.pshevche.spokk.lang.expect
                        assert(true)
                    }
                }   
                @io.github.pshevche.spokk.lang.internal.SpecMetadata
                class Spec : BaseSpec()
            """
        )
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
                    @io.github.pshevche.spokk.lang.internal.FeatureMetadata
                    fun `some feature`() {
                        io.github.pshevche.spokk.lang.${label}
                        assert(true)
                    }
                }
            """
        )
    )

}