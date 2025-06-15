package io.github.pshevche.spokk.runtime

import io.github.pshevche.spokk.runtime.execution.ErrorInfoCollector
import io.github.pshevche.spokk.runtime.execution.SpecNodeRunner
import io.github.pshevche.spokk.runtime.node.FeatureInfo
import io.github.pshevche.spokk.runtime.node.SpecInfo
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext

/**
 * Contains components required for executing various test nodes.
 */
class SpokkExecutionContext internal constructor(
    private val runner: SpecNodeRunner? = null,
    private val spec: SpecInfo? = null,
    private val errorInfoCollector: ErrorInfoCollector? = null,
    private val currentFeature: FeatureInfo? = null,
) : EngineExecutionContext {

    internal fun withRunner(runner: SpecNodeRunner) = copy(runner = runner)
    internal fun withSpec(spec: SpecInfo) = copy(spec = spec)
    internal fun withCurrentFeature(featureInfo: FeatureInfo) = copy(currentFeature = featureInfo)

    internal fun getRunner() = requireNotNull(runner) { "node runner must be initialized" }
    internal fun getSpec() = requireNotNull(spec) { "spec to execute must be initialized" }
    internal fun getCurrentFeature() = requireNotNull(currentFeature) { "feature to execute must be initialized" }

    private fun copy(
        runner: SpecNodeRunner? = this.runner,
        spec: SpecInfo? = this.spec,
        errorInfoCollector: ErrorInfoCollector? = this.errorInfoCollector,
        currentFeature: FeatureInfo? = this.currentFeature,
    ) = SpokkExecutionContext(
        runner = runner,
        spec = spec,
        errorInfoCollector = errorInfoCollector,
        currentFeature = currentFeature
    )
}
