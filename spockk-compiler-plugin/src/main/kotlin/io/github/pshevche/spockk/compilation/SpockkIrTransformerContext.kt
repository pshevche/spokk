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

package io.github.pshevche.spockk.compilation

import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.interpreter.getLastOverridden
import org.jetbrains.kotlin.ir.util.parentAsClass

internal class SpockkIrTransformerContext(
    var currentFile: IrFile? = null,
    private val specs: MutableMap<IrClass, SpecContext> = mutableMapOf(),
    private val blockValidators: MutableMap<IrFunction, FeatureBlockIrElementValidator> = mutableMapOf(),
) {
    fun addSpec(clazz: IrClass): Boolean = specs.putIfAbsent(clazz, SpecContext()) == null
    fun isSpec(clazz: IrClass?) = specs.containsKey(clazz)

    fun addFeature(feature: IrFunction, ordinalOverride: Int? = null) =
        specs[feature.parentAsClass]!!.addFeature(feature, ordinalOverride)

    fun featureOrdinal(feature: IrFunction) = specs[feature.parentAsClass]!!.features[feature]!!.ordinal
    fun isFeature(feature: IrFunction) = specs[feature.parentAsClass]?.features[feature] != null
    fun isInheritedFeature(feature: IrFunction) = isFeature(feature.getLastOverridden())

    fun initializeBlockValidator(function: IrFunction) = blockValidators.computeIfAbsent(function) { FeatureBlockIrElementValidator() }
    fun blockValidator(function: IrFunction) = blockValidators[function]!!
    fun discardBlockValidator(function: IrFunction) = blockValidators.remove(function)

    internal class SpecContext {
        private var featureOrdinal: Int = -1
        var features: MutableMap<IrFunction, FeatureContext> = mutableMapOf()

        fun addFeature(feature: IrFunction, ordinalOverride: Int?): Boolean {
            featureOrdinal = ordinalOverride ?: (featureOrdinal + 1)
            return features.putIfAbsent(feature, FeatureContext(featureOrdinal)) == null
        }
    }

    internal class FeatureContext(val ordinal: Int)
}
