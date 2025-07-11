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

import io.github.pshevche.spokk.compilation.SpokkIrConstants.FEATURE_METADATA_FQN
import io.github.pshevche.spokk.compilation.SpokkIrConstants.SPEC_METADATA_FQN
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.fromSymbolOwner
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.util.constructors

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class SpokkIrFactory(private val pluginContext: IrPluginContext) {

    fun specMetadataAnnotation() = createConstructorCall(pluginContext, SPEC_METADATA_FQN)

    fun featureMetadataAnnotation() = createConstructorCall(pluginContext, FEATURE_METADATA_FQN)

    private fun createConstructorCall(pluginContext: IrPluginContext, className: String): IrConstructorCall {
        val classSymbol = pluginContext.referenceClass(className)
        val constructorSymbol = classSymbol.constructors.single()
        val classType = classSymbol.defaultType
        return IrConstructorCallImpl.fromSymbolOwner(classType, constructorSymbol)
    }

}
