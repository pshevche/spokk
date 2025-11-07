/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pshevche.spockk.runtime.node

import io.github.pshevche.spockk.runtime.SpockkExecutionContext
import org.junit.platform.engine.TestSource
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor
import org.junit.platform.engine.support.hierarchical.Node

/**
 * Base test descriptor for spockk test nodes.
 */
internal abstract class SpockkNode<I : NodeInfo>(
    uniqueId: UniqueId,
    displayName: String,
    source: TestSource,
    val nodeInfo: I,
) :
    AbstractTestDescriptor(uniqueId, displayName, source), Node<SpockkExecutionContext>
