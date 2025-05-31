package io.github.pshevche.spokk.runtime.engine.node

import io.github.pshevche.spokk.runtime.engine.SpokkExecutionContext
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.MethodSource
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Represents a feature method in the test hierarchy
 */
class FeatureNode(
    uniqueId: UniqueId,
    displayName: String,
    source: MethodSource,
    nodeInfo: FeatureInfo,
) :
    SpokkNode<FeatureInfo>(uniqueId, displayName, source, nodeInfo) {

    override fun getType() = TestDescriptor.Type.TEST

    override fun prepare(context: SpokkExecutionContext): SpokkExecutionContext {
        return context.withCurrentFeature(nodeInfo)
    }

    override fun execute(
        context: SpokkExecutionContext,
        dynamicTestExecutor: Node.DynamicTestExecutor,
    ): SpokkExecutionContext {
        return super.execute(context, dynamicTestExecutor)
    }
}
