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
