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

import io.github.pshevche.spockk.runtime.execution.ErrorInfoCollector
import io.github.pshevche.spockk.runtime.execution.SpecNodeRunner
import io.github.pshevche.spockk.runtime.node.FeatureInfo
import io.github.pshevche.spockk.runtime.node.SpecInfo
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext

/**
 * Contains components required for executing various test nodes.
 */
class SpockkExecutionContext internal constructor(
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
    ) = SpockkExecutionContext(
        runner = runner,
        spec = spec,
        errorInfoCollector = errorInfoCollector,
        currentFeature = currentFeature
    )
}
