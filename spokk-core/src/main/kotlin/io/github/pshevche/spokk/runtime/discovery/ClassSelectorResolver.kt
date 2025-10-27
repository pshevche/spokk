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

import io.github.pshevche.spokk.runtime.SpokkException
import io.github.pshevche.spokk.runtime.node.FeatureInfo
import io.github.pshevche.spokk.runtime.node.FeatureNode
import io.github.pshevche.spokk.runtime.node.SpecInfo
import io.github.pshevche.spokk.runtime.node.SpecNode
import io.github.pshevche.spokk.runtime.util.SpecUtil.isRunnableSpec
import java.util.*
import org.junit.platform.commons.support.ReflectionSupport
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.discovery.ClassSelector
import org.junit.platform.engine.discovery.UniqueIdSelector
import org.junit.platform.engine.support.discovery.SelectorResolver
import org.junit.platform.engine.support.discovery.SelectorResolver.Match
import org.junit.platform.engine.support.discovery.SelectorResolver.Resolution

/**
 * Resolve class- and uniqueId-based selectors.
 */
internal class ClassSelectorResolver(private val classNameFilter: (String) -> Boolean) : SelectorResolver {

    /**
     * The requested class is recognized by the "spokk" engine if it has the `SpecMetadata` annotation.
     */
    override fun resolve(selector: ClassSelector, context: SelectorResolver.Context): Resolution {
        return resolveClass(selector.getJavaClass(), context)
    }

    /**
     * The requested uniqueId is a "spokk" spec class if its type is "spec" and the referenced class
     * has the `SpecMetadata` annotation.
     */
    override fun resolve(selector: UniqueIdSelector, context: SelectorResolver.Context): Resolution {
        val uniqueId = selector.uniqueId
        if (UniqueIdUtil.isSpec(uniqueId)) {
            val specClass = ReflectionSupport.tryToLoadClass(uniqueId.lastSegment.value)
                .getOrThrow { SpokkException("Failed to load class from UniqueIDSelector $uniqueId") }
            return resolveClass(specClass, context)
        }

        return Resolution.unresolved()
    }

    private fun resolveClass(specClass: Class<*>, context: SelectorResolver.Context): Resolution {
        if (isRunnableSpec(specClass) && classNameFilter(specClass.getName())) {
            return context
                .addToParent { Optional.of(createSpecNode(it, specClass)) }
                .map { Resolution.match(Match.exact(it)) }
                .orElseGet { Resolution.unresolved() }
        }

        return Resolution.unresolved()
    }

    private fun createSpecNode(
        parent: TestDescriptor,
        specClass: Class<*>,
    ): SpecNode {
        val specInfo = SpecInfo(specClass)
        val specId = UniqueIdUtil.forSpec(parent.uniqueId, specClass)
        val specNode = SpecNode(
            specId,
            specClass.name,
            TestSourceFactory.forSpec(specClass),
            specInfo
        )

        specInfo.features
            .map { createFeatureNode(specId, it) }
            .forEach { specNode.addChild(it) }

        return specNode
    }

    private fun createFeatureNode(
        specId: UniqueId,
        info: FeatureInfo,
    ): FeatureNode = FeatureNode(
        UniqueIdUtil.forFeature(specId, info),
        info.getName(),
        TestSourceFactory.forFeature(info),
        info
    )
}
