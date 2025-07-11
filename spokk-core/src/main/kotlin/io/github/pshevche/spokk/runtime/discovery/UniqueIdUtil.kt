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

package io.github.pshevche.spokk.runtime.discovery

import io.github.pshevche.spokk.runtime.node.FeatureInfo
import org.junit.platform.engine.UniqueId

internal object UniqueIdUtil {

    private const val SPEC_SEGMENT = "spec"
    private const val FEATURE_SEGMENT = "feature"

    fun forSpec(parentId: UniqueId, specClass: Class<*>): UniqueId = parentId.append(SPEC_SEGMENT, specClass.name)

    fun forFeature(parentId: UniqueId, featureInfo: FeatureInfo): UniqueId =
        parentId.append(FEATURE_SEGMENT, featureInfo.getName())

    fun isSpec(id: UniqueId): Boolean = id.lastSegment.type == SPEC_SEGMENT

    fun isFeature(id: UniqueId): Boolean = id.lastSegment.type == FEATURE_SEGMENT
}
