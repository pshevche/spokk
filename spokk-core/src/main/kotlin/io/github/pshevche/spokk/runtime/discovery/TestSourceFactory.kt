package io.github.pshevche.spokk.runtime.discovery

import io.github.pshevche.spokk.runtime.node.FeatureInfo
import org.junit.platform.engine.support.descriptor.ClassSource
import org.junit.platform.engine.support.descriptor.MethodSource

internal object TestSourceFactory {
    fun forSpec(spec: Class<*>): ClassSource = ClassSource.from(spec)

    fun forFeature(feature: FeatureInfo): MethodSource = MethodSource.from(
        feature.spec.getName(),
        feature.getName()
    )
}
