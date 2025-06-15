package io.github.pshevche.spokk.runtime.node

import io.github.pshevche.spokk.runtime.SpokkExecutionContext
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.EngineDescriptor
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Root test descriptor.
 */
internal class SpokkEngineDescriptor(uniqueId: UniqueId) : EngineDescriptor(uniqueId, "Spokk"), Node<SpokkExecutionContext>
