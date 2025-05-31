package io.github.pshevche.spokk.runtime.engine.node

import io.github.pshevche.spokk.runtime.engine.SpokkExecutionContext
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.EngineDescriptor
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Root test descriptor.
 */
class SpokkEngineDescriptor(uniqueId: UniqueId) : EngineDescriptor(uniqueId, "Spokk"), Node<SpokkExecutionContext>
