package io.github.pshevche.spokk.runtime.engine.discovery

import io.github.pshevche.spokk.runtime.engine.node.FeatureNode
import io.github.pshevche.spokk.runtime.engine.node.SpecNode
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
class MethodSelectorResolver : SelectorResolver {

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
