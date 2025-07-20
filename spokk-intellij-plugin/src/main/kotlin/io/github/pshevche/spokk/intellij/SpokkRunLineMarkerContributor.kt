package io.github.pshevche.spokk.intellij

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.util.Function
import io.github.pshevche.spokk.intellij.SpokkPsiElementExtensions.isClassOrFunctionIdentifier
import io.github.pshevche.spokk.intellij.SpokkPsiElementExtensions.isFeatureMethod
import io.github.pshevche.spokk.intellij.SpokkPsiElementExtensions.isSpec
import org.jetbrains.kotlin.idea.base.psi.kotlinFqName
import javax.swing.Icon

class SpokkRunLineMarkerContributor : RunLineMarkerContributor() {

    private val icon: Icon = AllIcons.RunConfigurations.TestState.Run

    private fun isTestFile(element: PsiElement): Boolean {
        return TestSourcesFilter.isTestSources(element.containingFile.virtualFile, element.project)
    }

    override fun getInfo(element: PsiElement): Info? {
        // line markers should only be added to leaf elements
        if (element !is LeafPsiElement) {
            return null
        }

        // not a test file -> ignore
        if (!ModuleUtil.hasTestSourceRoots(element.project) || !isTestFile(element)) {
            return null
        }

        // add markers only for class or function identifiers
        if (element.isClassOrFunctionIdentifier()) {
            // potentially a spec or a feature method
            val parent = element.parent
            if (parent.isSpec() || parent.isFeatureMethod()) {
                return Info(
                    icon,
                    ExecutorAction.Companion.getActions(1),
                    Function { "Run \"${element.parent.kotlinFqName?.shortName()?.asString()}\"" },
                )
            }
        }

        return null
    }
}
