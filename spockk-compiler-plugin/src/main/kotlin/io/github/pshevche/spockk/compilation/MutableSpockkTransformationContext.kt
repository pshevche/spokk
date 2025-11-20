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
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.interpreter.getLastOverridden
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.parentClassOrNull
import org.jetbrains.kotlin.ir.util.superClass
import org.jetbrains.kotlin.utils.mapToIndex

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class MutableSpockkTransformationContext {

    private val specs: MutableMap<IrClass, MutableSpecContext> = mutableMapOf()

    fun addFeature(clazz: IrClass, feature: IrFunction) =
        specs.computeIfAbsent(clazz) { MutableSpecContext() }.addFeature(feature)

    fun addPotentialFeature(clazz: IrClass, function: IrFunction) =
        specs.computeIfAbsent(clazz) { MutableSpecContext() }.addPotentialFeature(function)

    fun finalized(): SpockkTransformationContext {
        return SpockkTransformationContext(buildMap {
            specs.forEach { (spec, ctx) ->
                val features = finalizeFeatures(spec, ctx)
                if (features.isNotEmpty()) {
                    put(spec, SpockkTransformationContext.SpecContext(features.toMap()))
                }
            }
        })
    }

    private fun finalizeFeatures(
        spec: IrClass,
        ctx: MutableSpecContext,
    ): MutableMap<IrFunction, SpockkTransformationContext.FeatureContext> {
        val features = mutableMapOf<IrFunction, SpockkTransformationContext.FeatureContext>()

        val inheritedFeatures = determineInheritedFeatures(ctx, getInheritanceDepth(spec))
        features.putAll(inheritedFeatures)

        val featureOrdinalOffset = inheritedFeatures.size
        ctx.features.forEach {
            features[it.key] =
                SpockkTransformationContext.FeatureContext(it.value.ordinal + featureOrdinalOffset)
        }
        return features
    }

    private fun getInheritanceDepth(spec: IrClass): Map<IrClassSymbol, Int> {
        val allParents = mutableListOf<IrClassSymbol>()
        var currentClass = spec.superClass

        while (currentClass != null) {
            allParents.add(currentClass.symbol)
            currentClass = currentClass.superClass
        }

        return allParents.reversed().mapToIndex()
    }

    private fun determineInheritedFeatures(
        ctx: MutableSpecContext,
        inheritanceDepth: Map<IrClassSymbol, Int>,
    ): Map<IrFunction, SpockkTransformationContext.FeatureContext> = ctx.potentialFeatures
        .filter { isInheritedFeature(it) }
        .sortedBy { inheritanceDepth[it.getLastOverridden().parentAsClass.symbol] }
        .mapToIndex()
        .mapValues { SpockkTransformationContext.FeatureContext(it.value) }

    private fun isInheritedFeature(function: IrFunction): Boolean {
        val overriddenFunc = function.getLastOverridden()
        return overriddenFunc.parentClassOrNull?.let {
            specs[it]?.features[overriddenFunc] != null
        } ?: false
    }

    internal class MutableSpecContext() {
        var featureOrdinal: Int = 0
        var features: MutableMap<IrFunction, MutableFeatureContext> = mutableMapOf()
        var potentialFeatures: MutableSet<IrFunction> = mutableSetOf()

        fun addFeature(feature: IrFunction) {
            features.putIfAbsent(feature, MutableFeatureContext(featureOrdinal))
            featureOrdinal += 1
        }

        fun addPotentialFeature(function: IrFunction) {
            potentialFeatures.add(function)
        }
    }

    internal class MutableFeatureContext(var ordinal: Int)
}
