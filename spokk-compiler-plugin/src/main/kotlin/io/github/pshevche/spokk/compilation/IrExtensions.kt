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

@file:OptIn(UnsafeDuringIrConstructionAPI::class)

package io.github.pshevche.spokk.compilation

import org.jetbrains.kotlin.backend.common.CompilationException
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

internal fun IrPluginContext.referenceClass(className: String): IrClassSymbol {
    return this.referenceClass(classId(className)) ?: throw CompilationException(
        "Cannot find class $className",
        null,
        null,
        null
    )
}

internal fun IrClass.isOpenOrAbstract() = this.modality == Modality.OPEN || this.modality == Modality.ABSTRACT

internal fun IrGetObjectValue.asIrSpokkBlock(file: IrFile?): FeatureBlockIrElement? = FeatureBlockIrElement.from(file, this)

internal fun IrGetObjectValue.requiredFqn() = symbol.owner.fqNameWhenAvailable!!.asString()

internal fun IrCall.asIrSpokkBlock(file: IrFile?): FeatureBlockIrElement? = FeatureBlockIrElement.from(file, this)

internal fun IrCall.requiredFqn() = symbol.owner.fqNameWhenAvailable!!.asString()

private fun classId(className: String): ClassId = ClassId.topLevel(FqName(className))
