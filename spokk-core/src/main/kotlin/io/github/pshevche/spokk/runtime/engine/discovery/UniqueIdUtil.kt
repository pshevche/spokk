package io.github.pshevche.spokk.runtime.engine.discovery

import io.github.pshevche.spokk.runtime.engine.node.FeatureInfo
import org.junit.platform.engine.UniqueId

internal object UniqueIdUtil {

    private const val SPEC_SEGMENT = "spec"
    private const val FEATURE_SEGMENT = "feature"

    fun forSpec(parentId: UniqueId, specClass: Class<*>): UniqueId = parentId.append(SPEC_SEGMENT, specClass.name)

    fun forFeature(parentId: UniqueId, featureInfo: FeatureInfo): UniqueId =
        parentId.append(FEATURE_SEGMENT, featureInfo.getName())

    fun isSpec(id: UniqueId): Boolean = id.lastSegment.type == SPEC_SEGMENT

    fun isFeature(id: UniqueId): Boolean = id.lastSegment.type == FEATURE_SEGMENT
}
