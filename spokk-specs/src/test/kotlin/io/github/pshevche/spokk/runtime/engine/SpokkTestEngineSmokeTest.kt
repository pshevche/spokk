package io.github.pshevche.spokk.runtime.engine

import io.github.pshevche.spokk.fixtures.runtime.samples.SimpleSpec
import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata
import io.github.pshevche.spokk.runtime.engine.EngineTestKitUtils.execute
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod
import org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage
import org.junit.platform.engine.discovery.DiscoverySelectors.selectUniqueId
import java.util.stream.Collectors.toSet

@SpecMetadata
class SpokkTestEngineSmokeTest {

    @FeatureMetadata
    fun `discovers test class by class name`() {
        val events = execute(selectClass(SimpleSpec::class.java))
        events.assertStatistics {
            it.started(2).succeeded(1).failed(1)
        }
    }

    @FeatureMetadata
    fun `discovers test class by unique id`() {
        val events =
            execute(selectUniqueId(UniqueId.forEngine("spokk").append("spec", SimpleSpec::class.qualifiedName)))
        events.assertStatistics {
            it.started(2).succeeded(1).failed(1)
        }
    }

    @FeatureMetadata
    fun `discovers feature method by method name`() {
        var events = execute(selectMethod(SimpleSpec::class.java, "successful feature"))
        events.assertStatistics {
            it.started(1).succeeded(1)
        }

        events = execute(selectMethod(SimpleSpec::class.java, "failing feature"))
        events.assertStatistics {
            it.started(1).failed(1)
        }
    }

    @FeatureMetadata
    fun `discovers feature method by unique id`() {
        var events = execute(
            selectUniqueId(
                UniqueId.forEngine("spokk")
                    .append("spec", SimpleSpec::class.qualifiedName)
                    .append("feature", "successful feature")
            )
        )
        events.assertStatistics {
            it.started(1).succeeded(1)
        }

        events = execute(
            selectUniqueId(
                UniqueId.forEngine("spokk")
                    .append("spec", SimpleSpec::class.qualifiedName)
                    .append("feature", "failing feature")
            )
        )
        events.assertStatistics {
            it.started(1).failed(1)
        }
    }

    @FeatureMetadata
    fun `supports package selectors`() {
        val events = execute(selectPackage(SimpleSpec::class.java.packageName))
        val specIds = events
            .map { it.testDescriptor.uniqueId.removeLastSegment() }
            .collect(toSet())
        assert(specIds.count() == 4)
    }

}
