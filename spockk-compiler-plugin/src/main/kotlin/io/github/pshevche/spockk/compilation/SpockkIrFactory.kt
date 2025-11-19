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

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.fromSymbolOwner
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.util.constructors

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class SpockkIrFactory(private val pluginContext: IrPluginContext) {

    companion object {
        private const val SPEC_METADATA_FQN = "io.github.pshevche.spockk.lang.internal.SpecMetadata"
        private const val FEATURE_METADATA_FQN = "io.github.pshevche.spockk.lang.internal.FeatureMetadata"
    }

    fun specMetadataAnnotation() = createConstructorCall(pluginContext, SPEC_METADATA_FQN)

    fun featureMetadataAnnotation(startOffset: Int, ordinal: Int) =
        createConstructorCall(pluginContext, FEATURE_METADATA_FQN).apply {
            arguments[0] = intConst(startOffset, ordinal)
        }

    private fun intConst(startOffset: Int, value: Int) =
        IrConstImpl.int(startOffset, startOffset, pluginContext.irBuiltIns.intType, value)

    private fun createConstructorCall(pluginContext: IrPluginContext, className: String): IrConstructorCall {
        val classSymbol = pluginContext.referenceClass(className)
        val constructorSymbol = classSymbol.constructors.single()
        val classType = classSymbol.defaultType
        return IrConstructorCallImpl.fromSymbolOwner(classType, constructorSymbol)
    }

}
