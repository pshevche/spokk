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

package io.github.pshevche.spockk.compilation.common

import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI

internal sealed class FeatureBlockLabelIrElement(
    val label: FeatureBlockLabel,
    open val file: IrFile,
    open val element: IrElement,
) {

    companion object {
        fun from(file: IrFile, element: IrGetObjectValue) = fromElement(file, element, element.requiredFqn())

        fun from(file: IrFile, element: IrCall) = fromElement(file, element, element.requiredFqn())

        private fun fromElement(file: IrFile, element: IrElement, fqn: String): FeatureBlockLabelIrElement? {
            return when (FeatureBlockLabel.from(fqn)) {
                FeatureBlockLabel.GIVEN -> Given(file, element)
                FeatureBlockLabel.WHEN -> When(file, element)
                FeatureBlockLabel.THEN -> Then(file, element)
                FeatureBlockLabel.EXPECT -> Expect(file, element)
                FeatureBlockLabel.AND -> And(file, element)
                else -> null
            }
        }
    }

    data class Given(override val file: IrFile, override val element: IrElement) :
        FeatureBlockLabelIrElement(FeatureBlockLabel.GIVEN, file, element)

    data class When(override val file: IrFile, override val element: IrElement) :
        FeatureBlockLabelIrElement(FeatureBlockLabel.WHEN, file, element)

    data class Then(override val file: IrFile, override val element: IrElement) :
        FeatureBlockLabelIrElement(FeatureBlockLabel.THEN, file, element)

    data class Expect(override val file: IrFile, override val element: IrElement) :
        FeatureBlockLabelIrElement(FeatureBlockLabel.EXPECT, file, element)

    data class And(override val file: IrFile, override val element: IrElement) :
        FeatureBlockLabelIrElement(FeatureBlockLabel.AND, file, element)

}
