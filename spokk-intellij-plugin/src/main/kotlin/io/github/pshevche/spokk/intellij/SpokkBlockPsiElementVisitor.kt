package io.github.pshevche.spokk.intellij

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
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
        return getSpokkImportDirectivesFromCache(element.containingFile)
            .any { it.endsWith(element.text) }
    }

    private fun getSpokkImportDirectivesFromCache(file: PsiFile): List<String> {
        return CachedValuesManager.getManager(file.project).getCachedValue(file) {
            CachedValueProvider.Result.create(
                getSpokkImportDirectives(file),
                file
            )
        }
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