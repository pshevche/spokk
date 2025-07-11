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

package io.github.pshevche.spokk.runtime.node

import io.github.pshevche.spokk.runtime.SpokkExecutionContext
import io.github.pshevche.spokk.runtime.execution.SpecNodeRunner
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.ClassSource

/**
 * Represents the specification class in the test descriptor hierarchy
 */
internal class SpecNode(
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
