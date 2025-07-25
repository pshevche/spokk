package io.github.pshevche.spokk.intellij

import com.intellij.icons.AllIcons
import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.lang.Language
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.testIntegration.TestFramework
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

    override fun isLibraryAttached(module: Module): Boolean =
        JavaPsiFacade.getInstance(module.project).findPackage("io.github.pshevche.spokk.lang") != null

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