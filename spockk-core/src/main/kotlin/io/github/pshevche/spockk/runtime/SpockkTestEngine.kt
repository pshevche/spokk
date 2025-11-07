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

package io.github.pshevche.spockk.runtime

import com.google.auto.service.AutoService
import io.github.pshevche.spockk.runtime.discovery.ClassSelectorResolver
import io.github.pshevche.spockk.runtime.discovery.MethodSelectorResolver
import io.github.pshevche.spockk.runtime.node.SpockkEngineDescriptor
import io.github.pshevche.spockk.runtime.util.SpecUtil.isRunnableSpec
import org.junit.platform.engine.EngineDiscoveryRequest
import org.junit.platform.engine.ExecutionRequest
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestEngine
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine

@Suppress("unused")
@AutoService(TestEngine::class)
class SpockkTestEngine : HierarchicalTestEngine<SpockkExecutionContext>() {
    override fun createExecutionContext(request: ExecutionRequest): SpockkExecutionContext = SpockkExecutionContext()

    override fun getId(): String = "spockk"

    override fun discover(
        discoveryRequest: EngineDiscoveryRequest,
        uniqueId: UniqueId,
    ): TestDescriptor {
        val engineDescriptor = SpockkEngineDescriptor(uniqueId)

        EngineDiscoveryRequestResolver.builder<TestDescriptor>()
            .addClassContainerSelectorResolver { isRunnableSpec(it) }
            .addSelectorResolver { ctx ->
                ClassSelectorResolver { ctx.classNameFilter.test(it) }
            }
            .addSelectorResolver(MethodSelectorResolver())
            .build()
            .resolve(discoveryRequest, engineDescriptor)

        return engineDescriptor
    }
}
