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

import io.github.pshevche.spockk.compilation.FeatureBlock
import io.github.pshevche.spockk.compilation.FeatureBlockIrElement
import org.jetbrains.kotlin.backend.common.CompilationException

internal class FeatureBlockIrElementValidator {

    enum class State {
        INIT {
            override val validBlocks: List<FeatureBlock>
                get() = listOf(FeatureBlock.GIVEN, FeatureBlock.EXPECT, FeatureBlock.WHEN)

            override fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State {
                return when (block) {
                    is FeatureBlockIrElement.Given -> PRECONDITION
                    is FeatureBlockIrElement.Expect -> EXPECTATION_EXPECT
                    is FeatureBlockIrElement.When -> ACTION
                    else -> failOnInvalidBlock(block)
                }
            }
        },
        PRECONDITION {
            override val validBlocks: List<FeatureBlock>
                get() = listOf(FeatureBlock.AND, FeatureBlock.WHEN, FeatureBlock.EXPECT)

            override fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State {
                return when (block) {
                    is FeatureBlockIrElement.And -> this
                    is FeatureBlockIrElement.When -> ACTION
                    is FeatureBlockIrElement.Expect -> EXPECTATION_EXPECT
                    else -> failOnInvalidBlock(block)
                }
            }
        },
        ACTION {
            override val validBlocks: List<FeatureBlock>
                get() = listOf(FeatureBlock.AND, FeatureBlock.THEN)

            override fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State {
                return when (block) {
                    is FeatureBlockIrElement.And -> this
                    is FeatureBlockIrElement.Then -> EXPECTATION_THEN
                    else -> failOnInvalidBlock(block)
                }
            }
        },
        EXPECTATION_EXPECT {
            override val validBlocks: List<FeatureBlock>
                get() = listOf(FeatureBlock.AND)

            override fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State {
                return when (block) {
                    is FeatureBlockIrElement.And -> this
                    else -> failOnInvalidBlock(block)
                }
            }
        },
        EXPECTATION_THEN {
            override val validBlocks: List<FeatureBlock>
                get() = listOf(FeatureBlock.AND, FeatureBlock.WHEN)

            override fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State {
                return when (block) {
                    is FeatureBlockIrElement.And -> this
                    is FeatureBlockIrElement.When -> ACTION
                    else -> failOnInvalidBlock(block)
                }
            }
        }
        ;

        abstract val validBlocks: List<FeatureBlock>

        abstract fun nextOrFailIfInvalid(block: FeatureBlockIrElement): State

        fun failOnInvalidBlock(currentBlock: FeatureBlockIrElement): Nothing {
            val displayNames = validBlocks.map { "'${it.displayName}'" }
            throw CompilationException(
                "Expected to find one of spockk blocks ${displayNames}, but encountered '${currentBlock.block.displayName}'",
                currentBlock.file,
                currentBlock.element
            )
        }

        fun isExpectation() = this == EXPECTATION_EXPECT || this == EXPECTATION_THEN
    }

    private var currentState: State = State.INIT
    private var lastBlock: FeatureBlockIrElement? = null

    fun validate(block: FeatureBlockIrElement) {
        currentState = currentState.nextOrFailIfInvalid(block)
        lastBlock = block
    }

    fun assertBlockStructureIsComplete() {
        if (currentState != State.INIT && !currentState.isExpectation()) {
            val displayNames = currentState.validBlocks.map { "'${it.displayName}'" }
            throw CompilationException(
                "Expected to find one of spockk blocks ${displayNames}, but reached the end of the feature method",
                lastBlock?.file,
                lastBlock?.element
            )
        }
    }
}