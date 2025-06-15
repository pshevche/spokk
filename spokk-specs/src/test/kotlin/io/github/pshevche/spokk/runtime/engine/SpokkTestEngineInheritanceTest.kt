package io.github.pshevche.spokk.runtime.engine

import io.github.pshevche.spokk.fixtures.runtime.samples.InheritedAbstractParentSpec
import io.github.pshevche.spokk.fixtures.runtime.samples.InheritedFromAbstractChildSpec
import io.github.pshevche.spokk.fixtures.runtime.samples.InheritedOpenChildSpec
import io.github.pshevche.spokk.fixtures.runtime.samples.InheritedOpenParentSpec
import io.github.pshevche.spokk.lang.internal.FeatureMetadata
import io.github.pshevche.spokk.lang.internal.SpecMetadata
import io.github.pshevche.spokk.runtime.engine.EngineTestKitUtils.execute
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod

@SpecMetadata
class SpokkTestEngineInheritanceTest {

    @FeatureMetadata
    fun `discovers feature methods defined both in parent and child`() {
        val events = execute(selectClass(InheritedOpenChildSpec::class.java))
        events.assertStatistics {
            it.started(4).succeeded(2).failed(2)
        }
    }

    @FeatureMetadata
    fun `discovers class with features declared only in the parent class`() {
        val events = execute(selectClass(InheritedFromAbstractChildSpec::class.java))
        events.assertStatistics {
            it.started(2).succeeded(1).failed(1)
        }
    }

    @FeatureMetadata
    fun `discovers feature methods by name from parent class`() {
        val events = execute(selectMethod(InheritedOpenChildSpec::class.java, "successful parent feature"))
        events.assertStatistics {
            it.started(1).succeeded(1)
        }
    }

    @FeatureMetadata
    fun `does not discover tests in open classes`() {
        val events = execute(selectClass(InheritedOpenParentSpec::class.java))
        events.assertStatistics { it.started(0) }
    }

    @FeatureMetadata
    fun `does not discover tests in abstract classes`() {
        val events = execute(selectClass(InheritedAbstractParentSpec::class.java))
        events.assertStatistics { it.started(0) }
    }

}
