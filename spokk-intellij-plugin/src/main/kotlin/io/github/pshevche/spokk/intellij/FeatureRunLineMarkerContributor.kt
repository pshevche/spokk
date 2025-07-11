package io.github.pshevche.spokk.intellij;

import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import javax.swing.Icon

class FeatureRunLineMarkerContributor : RunLineMarkerContributor() {

    // icons list https://jetbrains.design/intellij/resources/icons_list/
    private val icon: Icon = AllIcons.RunConfigurations.TestState.Run

    private fun isTestFile(element: LeafPsiElement): Boolean {
        return TestSourcesFilter.isTestSources(element.containingFile.virtualFile, element.project)
    }

    override fun getInfo(element: PsiElement): Info? {
        return when (element) {
            is LeafPsiElement -> {
                if (!ModuleUtil.hasTestSourceRoots(element.project)) return null
                if (!isTestFile(element)) return null
                return null
            }

            else -> null
        }
    }
}
