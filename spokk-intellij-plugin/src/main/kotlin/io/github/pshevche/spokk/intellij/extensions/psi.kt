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

package io.github.pshevche.spokk.intellij.extensions

import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import io.github.pshevche.spokk.intellij.SpokkBlockPsiElementVisitor
import org.jetbrains.kotlin.idea.base.psi.kotlinFqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

fun PsiElement.isSpec() = this is KtClass && hasSpokkBlocks()

fun PsiElement.isFeatureMethod() = this is KtFunction && hasSpokkBlocks()

fun PsiElement.enclosingSpec(): KtClass? {
    val ktClass = this as? KtClass ?: this.getStrictParentOfType<KtClass>()
    return when {
        ktClass == null -> null
        ktClass.isSpec() -> ktClass
        else -> null
    }
}

fun PsiElement.enclosingFeature(): KtFunction? {
    val ktFunction = this as? KtFunction ?: this.getStrictParentOfType<KtFunction>()
    return when {
        ktFunction == null -> null
        ktFunction.isFeatureMethod() -> ktFunction
        else -> null
    }
}

private fun PsiElement.hasSpokkBlocks(): Boolean {
    return CachedValuesManager.getCachedValue(this) {
        val visitor = SpokkBlockPsiElementVisitor()
        accept(visitor)
        CachedValueProvider.Result(visitor.hasSpokkBlocks(), this)
    }
}

fun PsiElement.requiredFqn(): String = requireNotNull(this.kotlinFqName).asString()
