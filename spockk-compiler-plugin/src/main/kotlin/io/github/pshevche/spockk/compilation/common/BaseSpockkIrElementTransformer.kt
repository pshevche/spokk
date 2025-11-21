package io.github.pshevche.spockk.compilation.common

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction

internal open class BaseSpockkIrElementTransformer : IrElementTransformerVoidWithContext() {
    protected val currentIrClass: IrClass get() = currentClass!!.irElement as IrClass
    protected val currentIrFunction: IrFunction get() = currentFunction!!.irElement as IrFunction
}
