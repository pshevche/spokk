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

package io.github.pshevche.spokk.runtime.discovery

import io.github.pshevche.spokk.runtime.node.FeatureNode
import io.github.pshevche.spokk.runtime.node.SpecNode
import org.junit.platform.engine.DiscoverySelector
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.engine.discovery.MethodSelector
import org.junit.platform.engine.discovery.UniqueIdSelector
import org.junit.platform.engine.support.discovery.SelectorResolver
import org.junit.platform.engine.support.discovery.SelectorResolver.Resolution

/**
 * Resolve method- and uniqueId-based selectors.
 */
internal class MethodSelectorResolver : SelectorResolver {

    /**
     * Method is a spokk feature if the declaring class is a spokk spec, the method is annotated with `FeatureMetadata`
     * and the feature name matches the requested method name.
     */
    override fun resolve(selector: MethodSelector, context: SelectorResolver.Context): Resolution {
        val methodName = selector.methodName
        val parentSelector: DiscoverySelector = DiscoverySelectors.selectClass(selector.getJavaClass())
        return resolveMethod(methodName, context, parentSelector)
    }

    /**
     * Requested unique ID corresponds to a spokk feature if the segment type is "feature",
     * parent descriptor is a spokk spec, the method is annotated w/ `FeatureMetadata`, and the unique ID
     * value is equal to the name of the one of the methods declared in the spec class.
     */
    override fun resolve(selector: UniqueIdSelector, context: SelectorResolver.Context): Resolution {
        val uniqueId = selector.uniqueId

        if (UniqueIdUtil.isFeature(uniqueId)) {
            val methodName = uniqueId.lastSegment.value
            val parentSelector = DiscoverySelectors.selectUniqueId(uniqueId.removeLastSegment())
            return resolveMethod(methodName, context, parentSelector)
        }

        return Resolution.unresolved()
    }

    private fun resolveMethod(
        methodName: String,
        context: SelectorResolver.Context,
        parentSelector: DiscoverySelector,
    ): Resolution {
        return context.resolve(parentSelector)
            .filter { it is SpecNode }
            .map { spec ->
                spec.apply {
                    this.children
                        .filter { !isMatchingFeature(it, methodName) }
                        .forEach { it.removeFromHierarchy() }
                }
            }
            .map { Resolution.match(SelectorResolver.Match.exact(it)) }
            .orElseGet { Resolution.unresolved() }
    }

    private fun isMatchingFeature(descriptor: TestDescriptor, methodName: String): Boolean {
        if (descriptor is FeatureNode) {
            return descriptor.nodeInfo.getName() == methodName
        }

        return false
    }
}
