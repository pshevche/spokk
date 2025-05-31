package io.github.pshevche.spokk.runtime.engine

import com.google.auto.service.AutoService
import io.github.pshevche.spokk.runtime.engine.discovery.ClassSelectorResolver
import io.github.pshevche.spokk.runtime.engine.discovery.MethodSelectorResolver
import io.github.pshevche.spokk.runtime.engine.node.SpokkEngineDescriptor
import io.github.pshevche.spokk.runtime.engine.util.SpecUtil.isRunnableSpec
import org.junit.platform.engine.EngineDiscoveryRequest
import org.junit.platform.engine.ExecutionRequest
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestEngine
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine

@Suppress("unused")
@AutoService(TestEngine::class)
class SpokkTestEngine : HierarchicalTestEngine<SpokkExecutionContext>() {
    override fun createExecutionContext(request: ExecutionRequest): SpokkExecutionContext = SpokkExecutionContext()

    override fun getId(): String = "spokk"

    override fun discover(
        discoveryRequest: EngineDiscoveryRequest,
        uniqueId: UniqueId,
    ): TestDescriptor {
        val engineDescriptor = SpokkEngineDescriptor(uniqueId)

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
