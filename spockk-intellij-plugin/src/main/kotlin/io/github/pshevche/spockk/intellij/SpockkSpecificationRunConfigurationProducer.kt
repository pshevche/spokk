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

package io.github.pshevche.spockk.intellij

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.junit.InheritorChooser
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiClass
import io.github.pshevche.spockk.intellij.extensions.enclosingSpec
import io.github.pshevche.spockk.intellij.extensions.toKtClass
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.plugins.gradle.execution.test.runner.AbstractGradleTestRunConfigurationProducer
import org.jetbrains.plugins.gradle.util.createTestFilterFrom
import java.util.function.Consumer

/**
 * See https://github.com/JetBrains/intellij-community/blob/master/plugins/gradle/java/src/execution/test/runner/TestClassGradleConfigurationProducer.java
 * TODO: add tests https://github.com/pshevche/spockk/issues/81
 */
class SpockkSpecificationRunConfigurationProducer : AbstractGradleTestRunConfigurationProducer<KtClass, KtClass>() {
    override fun getElement(context: ConfigurationContext) = context.psiLocation?.enclosingSpec()

    override fun getLocationName(
        context: ConfigurationContext,
        element: KtClass,
    ) = element.name!!

    override fun suggestConfigurationName(
        context: ConfigurationContext,
        element: KtClass,
        chosenElements: List<KtClass>,
    ): String {
        val elements = chosenElements.ifEmpty { listOf(element) }
        return StringUtil.join(elements, { it.name }, "|")
    }

    override fun chooseSourceElements(
        context: ConfigurationContext,
        element: KtClass,
        onElementsChosen: Consumer<List<KtClass>>,
    ) {
        val onPsiClassElementsChosen = { list: List<PsiClass> ->
            onElementsChosen.accept(list.mapNotNull { it.toKtClass() })
        }
        InheritorChooser.chooseAbstractClassInheritors(context, element.toLightClass(), onPsiClassElementsChosen);
    }

    override fun getAllTestsTaskToRun(
        context: ConfigurationContext,
        element: KtClass,
        chosenElements: List<KtClass>,
    ): List<TestTasksToRun> {
        val project = context.project
        val source = element.containingFile.virtualFile
        val elements = chosenElements.ifEmpty { listOf(element) }
        return elements
            .mapNotNull { clazz -> clazz.toLightClass()?.let { createTestFilterFrom(it) } }
            .flatMap { testFilter ->
                findAllTestsTaskToRun(
                    source,
                    project
                ).map { TestTasksToRun(it, testFilter) }
            }
    }
}
