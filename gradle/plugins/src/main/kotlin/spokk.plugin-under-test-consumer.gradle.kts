plugins {
    id("spokk.kotlin-library")
}

val pluginUnderTest by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "plugin-under-test"))
    }
}

val gradlePluginProject = project(":spokk-gradle-plugin")
dependencies {
    pluginUnderTest(gradlePluginProject)
}

tasks.withType<Test>().configureEach {
    dependsOn(gradlePluginProject.tasks.named("publishAllPublicationsToRepoForTestRepository"))
    inputs.files(pluginUnderTest)
    systemProperty("spokk.pluginRepo", gradlePluginProject.ext.get("repoForTest").toString())
}
