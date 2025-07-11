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