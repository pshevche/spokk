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
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.superClass

@OptIn(UnsafeDuringIrConstructionAPI::class)
internal class SpokkIrTransformer(pluginContext: IrPluginContext) : IrElementTransformerVoidWithContext() {

    private val context = SpokkIrTransformerContext()
    private val irFactory = SpokkIrFactory(pluginContext)

    override fun visitClassNew(declaration: IrClass): IrStatement {
        val result = super.visitClassNew(declaration)

        if (context.specs.contains(declaration.superClass)) {
            specFound(declaration)
        }

        return result
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
        context.specs.add(clazz)
        if (!clazz.hasSpecMetadata() && !clazz.isAbstract()) {
            clazz.annotations += irFactory.specMetadataAnnotation()
        }
    }

    private fun featureFound(feature: IrFunction) {
        context.features.add(feature)
        if (!feature.hasFeatureMetadata()) {
            feature.annotations += irFactory.featureMetadataAnnotation()
        }

        specFound(feature.parentAsClass)
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
