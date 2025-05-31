package io.github.pshevche.spokk.runtime.engine.node

import io.github.pshevche.spokk.runtime.engine.SpokkExecutionContext
import io.github.pshevche.spokk.runtime.engine.execution.SpecNodeRunner
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.ClassSource

/**
 * Represents the specification class in the test descriptor hierarchy
 */
class SpecNode(
    uniqueId: UniqueId,
    displayName: String,
    source: ClassSource,
    specInfo: SpecInfo,
) :
    SpokkNode<SpecInfo>(uniqueId, displayName, source, specInfo) {

    override fun getType() = TestDescriptor.Type.CONTAINER

    override fun prepare(context: SpokkExecutionContext): SpokkExecutionContext {
        return context
            .withRunner(SpecNodeRunner())
            .withSpec(nodeInfo)
    }
}
