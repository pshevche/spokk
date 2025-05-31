package io.github.pshevche.spokk.runtime.engine.node

import io.github.pshevche.spokk.runtime.engine.SpokkExecutionContext
import org.junit.platform.engine.TestSource
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Base test descriptor for spokk test nodes.
 */
abstract class SpokkNode<I : NodeInfo>(
    uniqueId: UniqueId,
    displayName: String,
    source: TestSource,
    val nodeInfo: I,
) :
    AbstractTestDescriptor(uniqueId, displayName, source), Node<SpokkExecutionContext> {
}
