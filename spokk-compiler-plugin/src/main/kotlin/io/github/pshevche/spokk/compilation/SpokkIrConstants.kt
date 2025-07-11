package io.github.pshevche.spokk.compilation

internal object SpokkIrConstants {
    val SPOKK_BLOCKS_FQN = setOf(
        "io.github.pshevche.spokk.lang.given",
        "io.github.pshevche.spokk.lang.expect",
        "io.github.pshevche.spokk.lang.`when`",
        "io.github.pshevche.spokk.lang.then",
    )

    val SPEC_METADATA_FQN = "io.github.pshevche.spokk.lang.internal.SpecMetadata"
    val FEATURE_METADATA_FQN = "io.github.pshevche.spokk.lang.internal.FeatureMetadata"
}
