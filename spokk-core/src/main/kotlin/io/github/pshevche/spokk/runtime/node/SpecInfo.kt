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

package io.github.pshevche.spokk.runtime.node

import io.github.pshevche.spokk.lang.internal.FeatureMetadata

/**
 * Represents the specification class in the test hierarchy
 */
internal class SpecInfo(val reflection: Class<*>) : NodeInfo {
    val features = collectFeaturesInfo(reflection)

    override fun getName(): String = reflection.name

    private fun collectFeaturesInfo(spec: Class<*>): List<FeatureInfo> {
        return spec.methods
            .filter { it.isAnnotationPresent(FeatureMetadata::class.java) }
            .map { FeatureInfo(this, it) }
    }
}
