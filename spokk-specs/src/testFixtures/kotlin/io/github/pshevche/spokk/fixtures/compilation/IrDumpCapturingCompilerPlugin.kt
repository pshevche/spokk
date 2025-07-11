package io.github.pshevche.spokk.fixtures.compilation

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions
import org.jetbrains.kotlin.ir.util.dump

@OptIn(ExperimentalCompilerApi::class)
class IrDumpCapturingCompilerPlugin(private val irDumpBuilder: StringBuilder) : CompilerPluginRegistrar() {
    override val supportsK2: Boolean
        get() = true

    override fun ExtensionStorage.registerExtensions(
        configuration: CompilerConfiguration
    ) {
        IrGenerationExtension.registerExtension(SpokkIrGenerationExtension(irDumpBuilder))
    }

    internal class SpokkIrGenerationExtension(private val irDumpBuilder: StringBuilder) : IrGenerationExtension {
        override fun generate(
            moduleFragment: IrModuleFragment,
            pluginContext: IrPluginContext
        ) {
            val options = DumpIrTreeOptions(printFilePath = false)
            irDumpBuilder.append(moduleFragment.dump(options))
        }
    }

}
