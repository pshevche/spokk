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

import org.jetbrains.kotlin.backend.common.CompilationException
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue
import org.jetbrains.kotlin.ir.interpreter.getLastOverridden
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.superClass

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class SpokkIrTransformer(pluginContext: IrPluginContext) : IrElementTransformerVoidWithContext() {

    private val context = SpokkIrTransformerContext()
    private val irFactory = SpokkIrFactory(pluginContext)

    override fun visitClassNew(declaration: IrClass): IrStatement {
        // visit parent classes to determine whether there are any inherited features
        declaration.superClass?.let { super.visitClassNew(it) }

        // replay transformations of inherited features
        if (context.isSpec(declaration.superClass)) {
            specFound(declaration)
            declaration.functions
                .filter { context.isInheritedFeature(it) }
                .forEach { featureFound(it, context.featureOrdinal(it.getLastOverridden())) }
        }

        // transform current class
        return super.visitClassNew(declaration)
    }

    override fun visitGetObjectValue(expression: IrGetObjectValue): IrExpression {
        if (expression.isSpokkLabel()) {
            featureFound(currentFunction(expression))
        }

        return super.visitGetObjectValue(expression)
    }

    override fun visitCall(expression: IrCall): IrExpression {
        if (expression.isSpokkLabel()) {
            featureFound(currentFunction(expression))
        }

        return super.visitCall(expression)
    }

    private fun specFound(clazz: IrClass) {
        if (context.addSpec(clazz) && !clazz.isOpenOrAbstract()) {
            clazz.annotations += irFactory.specMetadataAnnotation()
        }
    }

    private fun featureFound(feature: IrFunction, ordinalOverride: Int? = null) {
        specFound(feature.parentAsClass)
        if (context.addFeature(feature, ordinalOverride)) {
            feature.annotations += irFactory.featureMetadataAnnotation(
                feature.startOffset,
                context.featureOrdinal(feature)
            )
        }
    }

    private fun currentFunction(expression: IrGetObjectValue): IrFunction {
        val irFunction = currentFunction?.irElement as IrFunction?

        if (irFunction == null) {
            throw CompilationException(
                "Spokk label ${expression.symbol.owner.name.asString()} was used outside of a feature method",
                expression.symbol.owner.file,
                expression
            )
        }

        return irFunction
    }

    private fun currentFunction(expression: IrCall): IrFunction {
        val irFunction = currentFunction?.irElement as IrFunction?

        if (irFunction == null) {
            throw CompilationException(
                "Spokk label ${expression.symbol.owner.name.asString()} was used outside of a feature method",
                expression.symbol.owner.file,
                expression
            )
        }

        return irFunction
    }

}
