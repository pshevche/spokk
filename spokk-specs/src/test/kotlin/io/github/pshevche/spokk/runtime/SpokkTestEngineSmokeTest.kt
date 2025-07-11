package io.github.pshevche.spokk.runtime

import io.github.pshevche.spokk.fixtures.runtime.samples.SimpleSpec
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import io.github.pshevche.spokk.fixtures.runtime.EngineTestKitUtils.execute
import java.util.stream.Collectors.toSet
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod
import org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage
import org.junit.platform.engine.discovery.DiscoverySelectors.selectUniqueId

class SpokkTestEngineSmokeTest {

    fun `discovers test class by class name`() {
        `when`
        val events = execute(selectClass(SimpleSpec::class.java))

        then
        events.assertStatistics {
            it.started(2).succeeded(1).failed(1)
        }
    }

    fun `discovers test class by unique id`() {
        `when`
        val events =
            execute(selectUniqueId(UniqueId.forEngine("spokk").append("spec", SimpleSpec::class.qualifiedName)))

        then
        events.assertStatistics {
            it.started(2).succeeded(1).failed(1)
        }
    }

    fun `discovers feature method by method name`() {
        `when`
        var events = execute(selectMethod(SimpleSpec::class.java, "successful feature"))

        then
        events.assertStatistics {
            it.started(1).succeeded(1)
        }

        `when`
        events = execute(selectMethod(SimpleSpec::class.java, "failing feature"))

        then
        events.assertStatistics {
            it.started(1).failed(1)
        }
    }

    fun `discovers feature method by unique id`() {
        `when`
        var events = execute(
            selectUniqueId(
                UniqueId.forEngine("spokk")
                    .append("spec", SimpleSpec::class.qualifiedName)
                    .append("feature", "successful feature")
            )
        )

        then
        events.assertStatistics {
            it.started(1).succeeded(1)
        }

        `when`
        events = execute(
            selectUniqueId(
                UniqueId.forEngine("spokk")
                    .append("spec", SimpleSpec::class.qualifiedName)
                    .append("feature", "failing feature")
            )
        )

        then
        events.assertStatistics {
            it.started(1).failed(1)
        }
    }

    fun `supports package selectors`() {
        `when`
        val events = execute(selectPackage(SimpleSpec::class.java.packageName))
        val specIds = events
            .map { it.testDescriptor.uniqueId.removeLastSegment() }
            .collect(toSet())

        then
        assert(specIds.count() == 4)
    }

}
