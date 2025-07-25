package io.github.pshevche.spokk.intellij.extensions

import com.intellij.psi.PsiElement
import io.github.pshevche.spokk.intellij.SpokkBlockPsiElementVisitor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction

fun PsiElement.isSpec() = this is KtClass && hasSpokkBlocks()

fun PsiElement.isFeatureMethod() = this is KtFunction && hasSpokkBlocks()

private fun PsiElement.hasSpokkBlocks(): Boolean {
    val visitor = SpokkBlockPsiElementVisitor()
    accept(visitor)
    return visitor.hasSpokkBlocks()
}
