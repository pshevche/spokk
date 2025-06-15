package io.github.pshevche.spokk.runtime.engine

import io.github.pshevche.spokk.runtime.engine.execution.ErrorInfoCollector
import io.github.pshevche.spokk.runtime.engine.execution.SpecNodeRunner
import io.github.pshevche.spokk.runtime.engine.node.FeatureInfo
import io.github.pshevche.spokk.runtime.engine.node.SpecInfo
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext

/**
 * Contains components required for executing various test nodes.
 */
data class SpokkExecutionContext(
    private val runner: SpecNodeRunner? = null,
    private val spec: SpecInfo? = null,
    private val errorInfoCollector: ErrorInfoCollector? = null,
    private val currentFeature: FeatureInfo? = null,
) : EngineExecutionContext {

    fun withRunner(runner: SpecNodeRunner) = copy(runner = runner)
    fun withSpec(spec: SpecInfo) = copy(spec = spec)
    fun withCurrentFeature(featureInfo: FeatureInfo) = copy(currentFeature = featureInfo)

    fun getRunner() = requireNotNull(runner) { "node runner must be initialized" }
    fun getSpec() = requireNotNull(spec) { "spec to execute must be initialized" }
    fun getCurrentFeature() = requireNotNull(currentFeature) { "feature to execute must be initialized" }
}
