package io.github.pshevche.spokk.runtime.node

import io.github.pshevche.spokk.lang.internal.FeatureMetadata

/**
 * Represents the specification class in the test hierarchy
 */
internal class SpecInfo(val reflection: Class<*>) : NodeInfo {
    val features = collectFeaturesInfo(reflection)

    override fun getName(): String = reflection.name

    private fun collectFeaturesInfo(spec: Class<*>): List<FeatureInfo> {
        return spec.methods
            .filter { it.isAnnotationPresent(FeatureMetadata::class.java) }
            .map { FeatureInfo(this, it) }
    }
}
