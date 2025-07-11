package io.github.pshevche.spokk.compilation

import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction

internal data class SpokkIrTransformerContext(
    val specs: MutableSet<IrClass> = mutableSetOf(),
    val features: MutableSet<IrFunction> = mutableSetOf()
)
