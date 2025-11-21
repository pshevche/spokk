package io.github.pshevche.spockk.compilation.collector

import io.github.pshevche.spockk.compilation.common.BaseSpockkIrElementTransformer
import io.github.pshevche.spockk.compilation.common.asIrSpockkBlock
import io.github.pshevche.spockk.compilation.common.MutableSpockkTransformationContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.isFakeOverride

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class SpockkTransformationContextCollector(private val context: MutableSpockkTransformationContext) :
    BaseSpockkIrElementTransformer() {

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        if (declaration.isFakeOverride) {
            context.addPotentialFeature(currentIrClass, declaration)
        }

        return super.visitFunctionNew(declaration)
    }

    override fun visitGetObjectValue(expression: IrGetObjectValue): IrExpression {
        expression.asIrSpockkBlock(currentFile)?.let {
            context.addFeature(currentIrClass, currentIrFunction)
        }

        return super.visitGetObjectValue(expression)
    }

    override fun visitCall(expression: IrCall): IrExpression {
        expression.asIrSpockkBlock(currentFile)?.let {
            context.addFeature(currentIrClass, currentIrFunction)
        }

        return super.visitCall(expression)
    }
}