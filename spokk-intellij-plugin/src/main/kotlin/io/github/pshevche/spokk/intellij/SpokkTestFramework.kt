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

import com.intellij.icons.AllIcons
import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.lang.Language
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.testIntegration.TestFramework
import io.github.pshevche.spokk.intellij.extensions.hasSpokkPackages
import io.github.pshevche.spokk.intellij.extensions.isFeatureMethod
import io.github.pshevche.spokk.intellij.extensions.isSpec
import javax.swing.Icon
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.testIntegration.framework.KotlinPsiBasedTestFramework.Companion.asKtClassOrObject
import org.jetbrains.kotlin.idea.testIntegration.framework.KotlinPsiBasedTestFramework.Companion.asKtNamedFunction

class SpokkTestFramework : TestFramework {

    override fun getName(): String = "Spokk"

    override fun getLanguage(): Language = KotlinLanguage.INSTANCE

    // TODO pshevche: use a dedicated icon
    override fun getIcon(): Icon = AllIcons.RunConfigurations.Junit

    override fun isLibraryAttached(module: Module): Boolean = module.hasSpokkPackages()

    override fun getLibraryPath(): String? = null

    // spokk does not require tests to extend any base classes
    override fun getDefaultSuperClass(): String? = null

    override fun isTestClass(element: PsiElement): Boolean = element.asKtClassOrObject()?.isSpec() ?: false

    override fun isPotentialTestClass(element: PsiElement): Boolean {
        if (element is PsiClass) {
            val psiFile = element.containingFile
            val vFile = psiFile.virtualFile ?: return false
            return ProjectRootManager.getInstance(element.project).fileIndex.isInTestSourceContent(vFile)
        }

        return false
    }

    // spokk does not support setup methods yet
    override fun findSetUpMethod(element: PsiElement): PsiElement? = null

    // spokk does not support tear down methods yet
    override fun findTearDownMethod(element: PsiElement): PsiElement? = null

    // spokk does not support setup methods yet
    override fun findOrCreateSetUpMethod(element: PsiElement): PsiElement? = null

    // spokk does not support setup methods yet
    override fun getSetUpMethodFileTemplateDescriptor(): FileTemplateDescriptor? = null

    // spokk does not support tear down methods yet
    override fun getTearDownMethodFileTemplateDescriptor(): FileTemplateDescriptor? = null

    // TODO pshevche: implement
    override fun getTestMethodFileTemplateDescriptor(): FileTemplateDescriptor = FileTemplateDescriptor("unimplemented")

    // spokk does not support ignoring tests yet
    override fun isIgnoredMethod(element: PsiElement?): Boolean = false

    override fun isTestMethod(element: PsiElement): Boolean = element.asKtNamedFunction()?.isFeatureMethod() ?: false

}