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

package io.github.pshevche.spokk.intellij

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid

internal class SpokkBlockPsiElementVisitor : KtTreeVisitorVoid() {

    companion object {
        private val SPOKK_BLOCKS_FQN = setOf(
            "io.github.pshevche.spokk.lang.given",
            "io.github.pshevche.spokk.lang.expect",
            "io.github.pshevche.spokk.lang.when",
            "io.github.pshevche.spokk.lang.then",
        )
    }

    private var hasSpokkBlocks = false

    override fun visitElement(element: PsiElement) {
        hasSpokkBlocks = hasSpokkBlocks || (element is LeafPsiElement && isSpokkBlock(element))
        super.visitElement(element)
    }

    fun hasSpokkBlocks() = hasSpokkBlocks

    private fun isSpokkBlock(element: LeafPsiElement): Boolean {
        return getSpokkImportDirectives(element.containingFile)
            .any { it.endsWith(element.text) }
    }

    private fun getSpokkImportDirectives(file: PsiFile): List<String> {
        if (file is KtFile) {
            return file.importDirectives
                .mapNotNull { it.importedReference?.text }
                .filter { SPOKK_BLOCKS_FQN.contains(it) }
        }

        return listOf()
    }
}