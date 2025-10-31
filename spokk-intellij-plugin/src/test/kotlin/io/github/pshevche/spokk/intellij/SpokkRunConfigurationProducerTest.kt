package io.github.pshevche.spokk.intellij

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.externalSystem.ExternalSystemModulePropertyManager
import com.intellij.openapi.externalSystem.model.DataNode
import com.intellij.openapi.externalSystem.model.Key
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.model.task.TaskData
import com.intellij.openapi.util.Ref
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import com.intellij.testFramework.replaceService
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.jetbrains.plugins.gradle.service.execution.GradleRunConfiguration
import org.jetbrains.plugins.gradle.service.project.GradleTasksIndices
import org.jetbrains.plugins.gradle.util.GradleTaskData
import java.nio.file.Paths

/**
 * These tests are very shallow, as we mock a lot of the Gradle configuration.
 * However, IntelliJ does not export fixtures for testing with proper Gradle projects (e.g., `KotlinGradleProjectTestCase`),
 * and I don't have the capacity at the moment to set up this infrastructure myself.
 */
class SpokkRunConfigurationProducerTest : LightJavaCodeInsightFixtureTestCase() {

    private lateinit var producer: SpokkRunConfigurationProducer

    override fun setUp() {
        super.setUp()
        producer = SpokkRunConfigurationProducer()
    }

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/SpokkRunConfigurationProducerTest/").toAbsolutePath()
        return path.toString()
    }

    private fun defaultContext() = ConfigurationContext.getFromContext({ key ->
        when (key) {
            CommonDataKeys.PSI_FILE.name -> file
            CommonDataKeys.EDITOR.name -> editor
            CommonDataKeys.PROJECT.name -> project
            LangDataKeys.MODULE.name -> module
            else -> null
        }
    }, "SpokkRunConfigurationProducerTest")

    @Suppress("UnstableApiUsage")
    private fun mockGradleConfiguration(moduleName: String): GradleRunConfiguration {
        val modulePath = "${project.basePath}/${moduleName}"

        // set file system path for the module
        ExternalSystemModulePropertyManager.getInstance(module).setLinkedProjectPath(modulePath)

        // mock Gradle services that are only available when Gradle project is imported
        val mockIndices = mockk<GradleTasksIndices>()
        every {
            mockIndices.findTasks(eq(modulePath), eq(":${moduleName}:test"))
        } returns listOf(
            GradleTaskData(
                DataNode(
                    Key("irrelevant", 0),
                    TaskData(ProjectSystemId("projectId"), "test", modulePath, null),
                    null
                ), ":${moduleName}"
            )
        )
        project.replaceService(GradleTasksIndices::class.java, mockIndices, mockk<Disposable>())

        // return default Gradle configuration to modify
        return GradleRunConfiguration(project, producer.configurationFactory, moduleName)
    }

    fun testRunConfigurationForSpec() {
        // given
        myFixture.configureByFile("/testRunConfigurationForSpec/CaretAtSpec.kt")
        val configuration = mockGradleConfiguration("testRunConfigurationForSpec")

        // when
        val configurationUpdated = producer.setupConfigurationFromContext(
            configuration,
            defaultContext(),
            Ref.create(myFixture.elementAtCaret)
        )

        // then
        assertTrue(configurationUpdated)
        assertThat(configuration.name, equalTo("SimpleSpec"))
        assertTrue(configuration.isRunAsTest)
        assertThat(
            configuration.settings.taskNames,
            equalTo(
                listOf(
                    ":testRunConfigurationForSpec:test",
                    "--tests",
                    "'io.github.pshevche.spokk.samples.SimpleSpec'"
                )
            )
        )
        assertTrue(producer.isConfigurationFromContext(configuration, defaultContext()))
    }

    fun testRunConfigurationForFeature() {
        // given
        myFixture.configureByFile("/testRunConfigurationForFeature/CaretAtFeature.kt")
        val configuration = mockGradleConfiguration("testRunConfigurationForFeature")

        // when
        val configurationUpdated = producer.setupConfigurationFromContext(
            configuration,
            defaultContext(),
            Ref.create(myFixture.elementAtCaret)
        )

        // then
        assertTrue(configurationUpdated)
        assertThat(configuration.name, equalTo("SimpleSpec successful feature"))
        assertTrue(configuration.isRunAsTest)
        assertThat(
            configuration.settings.taskNames,
            equalTo(
                listOf(
                    ":testRunConfigurationForFeature:test",
                    "--tests",
                    "'io.github.pshevche.spokk.samples.SimpleSpec.successful feature'"
                )
            )
        )
        assertTrue(producer.isConfigurationFromContext(configuration, defaultContext()))
    }

    fun testNoRunConfigurationForNonSpecs() {
        // given
        myFixture.configureByFile("/testNoRunConfigurationForNonSpecs/CaretAtNonSpec.kt")
        val configuration = mockGradleConfiguration("testNoRunConfigurationForNonSpecs")

        // when
        val configurationUpdated = producer.setupConfigurationFromContext(
            configuration,
            defaultContext(),
            Ref.create(myFixture.elementAtCaret)
        )

        // then
        assertFalse(configurationUpdated)
    }

    fun testNoRunConfigurationForNonFeatures() {
        // given
        myFixture.configureByFile("/testNoRunConfigurationForNonFeatures/CaretAtNonFeature.kt")
        val configuration = mockGradleConfiguration("testNoRunConfigurationForNonFeatures")

        // when
        val configurationUpdated = producer.setupConfigurationFromContext(
            configuration,
            defaultContext(),
            Ref.create(myFixture.elementAtCaret)
        )

        // then
        assertFalse(configurationUpdated)
    }

    fun testConfigurationIsRefreshedIfModuleChanges() {
        // given
        myFixture.configureByFile("/testConfigurationIsRefreshedIfModuleChanges/CaretAtSpec.kt")
        val previousConfiguration = mockGradleConfiguration("testConfigurationIsRefreshedIfModuleChanges").apply {
            settings.taskNames = listOf(":otherModule:test", "--tests", "'io.github.pshevche.spokk.samples.SimpleSpec'")
        }

        // expect
        assertFalse(producer.isConfigurationFromContext(previousConfiguration, defaultContext()))
    }

    fun testConfigurationIsRefreshedIfSpecChanges() {
        // given
        myFixture.configureByFile("/testConfigurationIsRefreshedIfSpecChanges/CaretAtSpec.kt")
        val previousConfiguration = mockGradleConfiguration("testConfigurationIsRefreshedIfSpecChanges").apply {
            settings.taskNames = listOf(
                ":testConfigurationIsRefreshedIfSpecChanges:test",
                "--tests",
                "'io.github.pshevche.spokk.samples.OtherSpec'"
            )
        }

        // expect
        assertFalse(producer.isConfigurationFromContext(previousConfiguration, defaultContext()))
    }

    fun testConfigurationIsRefreshedIfFeatureChanges() {
        // given
        myFixture.configureByFile("/testConfigurationIsRefreshedIfFeatureChanges/CaretAtFeature.kt")
        val previousConfiguration = mockGradleConfiguration("testConfigurationIsRefreshedIfFeatureChanges").apply {
            settings.taskNames = listOf(
                ":testConfigurationIsRefreshedIfFeatureChanges:test",
                "--tests",
                "'io.github.pshevche.spokk.samples.SimpleSpec.other feature'"
            )
        }

        // expect
        assertFalse(producer.isConfigurationFromContext(previousConfiguration, defaultContext()))
    }

}
