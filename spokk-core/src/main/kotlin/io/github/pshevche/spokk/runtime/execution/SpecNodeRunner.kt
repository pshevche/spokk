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

package io.github.pshevche.spokk.runtime.execution

import io.github.pshevche.spokk.runtime.node.SpecInfo

/**
 * Executes a `SpecNode` in the test hierarchy.
 */
internal class SpecNodeRunner {

    private var currentSpecInstance: Any? = null

    fun createSpecInstance(spec: SpecInfo, errorInfoCollector: ErrorInfoCollector) {
        try {
            currentSpecInstance = spec.reflection.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            errorInfoCollector.addErrorInfo(e)
        }
    }

    fun runFeatureMethod(
        currentFeature: io.github.pshevche.spokk.runtime.node.FeatureInfo,
        errorInfoCollector: ErrorInfoCollector,
    ) {
        try {
            currentFeature.reflection.invoke(currentSpecInstance!!)
        } catch (e: Exception) {
            errorInfoCollector.addErrorInfo(e)
        }
    }
}
