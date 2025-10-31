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

import com.intellij.execution.JavaRunConfigurationExtensionManager
import com.intellij.execution.RunManager
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.openapi.module.Module
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import io.github.pshevche.spokk.intellij.extensions.enclosingFeature
import io.github.pshevche.spokk.intellij.extensions.enclosingSpec
import io.github.pshevche.spokk.intellij.extensions.requiredFqn
import kotlinx.io.files.SystemPathSeparator
import org.jetbrains.kotlin.idea.base.projectStructure.externalProjectPath
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.plugins.gradle.execution.GradleRunConfigurationProducer
import org.jetbrains.plugins.gradle.service.execution.GradleRunConfiguration
import org.jetbrains.plugins.gradle.service.project.GradleTasksIndices

class SpokkRunConfigurationProducer : GradleRunConfigurationProducer() {
    override fun setupConfigurationFromContext(
        configuration: GradleRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement?>
    ): Boolean {
        val module = context.module ?: return false
        val element = sourceElement.get() ?: return false
        val location = context.location ?: return false

        val spec = element.enclosingSpec() ?: return false
        val feature = element.enclosingFeature()

        configuration.apply {
            name = configurationName(spec, feature)
            isDebugServerProcess = false
            isRunAsTest = true
            settings.scriptParameters = ""
            settings.taskNames = taskNames(module, spec, feature)
        }

        RunManager.getInstance(context.project).setUniqueNameIfNeeded(configuration)
        JavaRunConfigurationExtensionManager.instance.extendCreatedConfiguration(configuration, location)

        return true
    }

    private fun configurationName(spec: KtClass, feature: KtFunction?): String = buildString {
        append(spec.name)
        feature?.let {
            append(" ")
            append(it.name)
        }
    }

    @Suppress("UnstableApiUsage")
    private fun taskNames(
        module: Module,
        spec: KtClass,
        feature: KtFunction?
    ): List<String> {
        val modulePath = requireNotNull(module.externalProjectPath) {
            "Cannot determine the module path to run Spokk tests"
        }
        val moduleName = modulePath.substring(modulePath.lastIndexOf(SystemPathSeparator) + 1)

        // TODO pshevche: support not only the default test task
        val defaultTestTask = GradleTasksIndices.getInstance(module.project)
            .findTasks(modulePath, ":${moduleName}:test")
            .singleOrNull() ?: return emptyList()

        val testFilter = feature?.requiredFqn() ?: spec.requiredFqn()
        return listOf(defaultTestTask.getFqnTaskName(), "--tests", "'${testFilter}'")
    }

    override fun isConfigurationFromContext(
        configuration: GradleRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val module = context.module ?: return false
        val element = context.psiLocation ?: return false

        val spec = element.enclosingSpec() ?: return false
        val feature = element.enclosingFeature()

        return configuration.settings.taskNames == taskNames(module, spec, feature)
    }
}