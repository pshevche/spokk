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

package io.github.pshevche.spokk.compilation

internal object SpokkIrConstants {
    val SPOKK_BLOCKS_FQN = setOf(
        "io.github.pshevche.spokk.lang.given",
        "io.github.pshevche.spokk.lang.expect",
        "io.github.pshevche.spokk.lang.when",
        "io.github.pshevche.spokk.lang.then",
    )

    const val SPEC_METADATA_FQN = "io.github.pshevche.spokk.lang.internal.SpecMetadata"
    const val FEATURE_METADATA_FQN = "io.github.pshevche.spokk.lang.internal.FeatureMetadata"
}
