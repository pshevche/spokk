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

package io.github.pshevche.spockk.compilation.rewriter

import io.github.pshevche.spockk.compilation.SpockkIrFactory
import io.github.pshevche.spockk.compilation.SpockkTransformationContext.FeatureContext
import io.github.pshevche.spockk.compilation.asIrSpockkBlock
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.util.statements

internal class FeatureRewriter(private val irFactory: SpockkIrFactory) {

    fun rewrite(feature: IrFunction, file: IrFile, context: FeatureContext) {
        validateBlocks(feature, file)
        annotateFeature(feature, context)
    }

    private fun validateBlocks(
        feature: IrFunction,
        file: IrFile,
    ) {
        val blockValidator = FeatureBlockIrElementValidator()
        feature.body?.statements?.forEach { stat ->
            stat.asIrSpockkBlock(file)?.let {
                blockValidator.validate(it)
            }
        }
        blockValidator.assertBlockStructureIsComplete()
    }

    private fun annotateFeature(feature: IrFunction, context: FeatureContext) {
        feature.annotations += irFactory.featureMetadataAnnotation(
            feature.startOffset,
            context.ordinal
        )
    }
}
