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

import io.github.pshevche.spokk.compilation.SpokkIrConstants.SPOKK_BLOCKS_FQN
import org.jetbrains.kotlin.backend.common.CompilationException
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

fun IrPluginContext.referenceClass(className: String): IrClassSymbol {
    val referenceIrClass = this.referenceClass(classId(className))

    if (referenceIrClass == null) {
        throw CompilationException("Cannot find class $className", null, null, null)
    }

    return referenceIrClass
}

fun IrClass.isOpenOrAbstract() = this.modality == Modality.OPEN || this.modality == Modality.ABSTRACT

fun IrGetObjectValue.isSpokkLabel() = SPOKK_BLOCKS_FQN.contains(symbol.owner.fqNameWhenAvailable?.asString())

fun IrCall.isSpokkLabel() = SPOKK_BLOCKS_FQN.contains(symbol.owner.fqNameWhenAvailable?.asString())

private fun classId(className: String): ClassId = ClassId.topLevel(FqName(className))
