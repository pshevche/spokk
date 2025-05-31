package io.github.pshevche.spokk.runtime.engine.node

import io.github.pshevche.spokk.lang.internal.FeatureMetadata

/**
 * Represents the specification class in the test hierarchy
 */
class SpecInfo(private val spec: Class<*>) : NodeInfo {
    val features = collectFeaturesInfo(spec)

    override fun getName(): String = spec.name

    private fun collectFeaturesInfo(spec: Class<*>): List<FeatureInfo> {
        return spec.declaredMethods
            .filter { it.isAnnotationPresent(FeatureMetadata::class.java) }
            .map { FeatureInfo(this, it) }
    }
}
