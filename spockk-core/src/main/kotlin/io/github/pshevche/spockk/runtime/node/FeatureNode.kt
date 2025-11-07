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
import io.github.pshevche.spockk.runtime.execution.ErrorInfoCollector
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
    SpockkNode<FeatureInfo>(uniqueId, displayName, source, nodeInfo) {

    override fun getType() = TestDescriptor.Type.TEST

    override fun prepare(context: SpockkExecutionContext): SpockkExecutionContext {
        val errorInfoCollector = ErrorInfoCollector()
        context.getRunner().createSpecInstance(context.getSpec(), errorInfoCollector)
        errorInfoCollector.assertEmpty()
        return context.withCurrentFeature(nodeInfo)
    }

    override fun execute(
        context: SpockkExecutionContext,
        dynamicTestExecutor: Node.DynamicTestExecutor,
    ): SpockkExecutionContext {
        val errorInfoCollector = ErrorInfoCollector()
        context.getRunner().runFeatureMethod(context.getCurrentFeature(), errorInfoCollector)
        errorInfoCollector.assertEmpty()
        return context
    }
}
