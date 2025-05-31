@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    `java-test-fixtures`
    alias(libs.plugins.kotlin.power.assert)
    id("spokk.compiler-plugin-consumer")
    id("spokk.plugin-under-test-consumer")
}

dependencies {
    testImplementation(gradleTestKit())
    testImplementation(projects.spokkCore)
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
