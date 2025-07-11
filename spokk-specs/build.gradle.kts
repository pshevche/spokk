@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    `java-test-fixtures`
    alias(libs.plugins.kotlin.power.assert)
    id("spokk.compiler-plugin-consumer")
    id("spokk.plugin-under-test-consumer")
}

dependencies {
    testImplementation(projects.spokkCore)
    testImplementation(gradleTestKit())
    testImplementation(libs.junit.platform.testkit)
    testImplementation(libs.kotlin.compile.testing)

    testRuntimeOnly(libs.junit.platform.launcher)

    testFixturesImplementation(projects.spokkCompilerPlugin)
    testFixturesImplementation(projects.spokkCore)
    testFixturesImplementation(gradleTestKit())
    testFixturesImplementation(libs.junit.platform.testkit)
    testFixturesImplementation(libs.kotlin.compile.testing)
}

tasks.test {
    useJUnitPlatform {
        includeEngines.add("spokk")
    }
}

powerAssert {
    functions = setOf("kotlin.assert")
    includedSourceSets = setOf("test")
}
