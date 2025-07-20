package io.github.pshevche.spokk.intellij

import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.kotlin.lexer.KtKeywordToken
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction

internal object SpokkPsiElementExtensions {

    fun PsiElement.isClassOrFunctionIdentifier(): Boolean {
        return elementType is KtKeywordToken && (text == "class" || text == "fun")
    }

    fun PsiElement.isSpec() = this is KtClass && hasSpokkBlocks()

    fun PsiElement.isFeatureMethod() = this is KtFunction && hasSpokkBlocks()

    private fun PsiElement.hasSpokkBlocks(): Boolean {
        val visitor = SpokkBlockPsiElementVisitor()
        accept(visitor)
        return visitor.hasSpokkBlocks()
    }
}
