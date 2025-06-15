package io.github.pshevche.spokk.runtime.node

import io.github.pshevche.spokk.runtime.SpokkExecutionContext
import io.github.pshevche.spokk.runtime.execution.ErrorInfoCollector
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.MethodSource
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Represents a feature method in the test hierarchy
 */
internal class FeatureNode(
    uniqueId: UniqueId,
    displayName: String,
    source: MethodSource,
    nodeInfo: FeatureInfo,
) :
    SpokkNode<FeatureInfo>(uniqueId, displayName, source, nodeInfo) {

    override fun getType() = TestDescriptor.Type.TEST

    override fun prepare(context: SpokkExecutionContext): SpokkExecutionContext {
        val errorInfoCollector = ErrorInfoCollector()
        context.getRunner().createSpecInstance(context.getSpec(), errorInfoCollector)
        errorInfoCollector.assertEmpty()
        return context.withCurrentFeature(nodeInfo)
    }

    override fun execute(
        context: SpokkExecutionContext,
        dynamicTestExecutor: Node.DynamicTestExecutor,
    ): SpokkExecutionContext {
        val errorInfoCollector = ErrorInfoCollector()
        context.getRunner().runFeatureMethod(context.getCurrentFeature(), errorInfoCollector)
        errorInfoCollector.assertEmpty()
        return context
    }
}
