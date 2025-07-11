plugins {
    id("spokk.kotlin-library")
}

val artifactUnderTest by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "artifact-under-test"))
    }
}

val gradlePluginProject = project(":spokk-gradle-plugin")
val compilerPluginProject = project(":spokk-compiler-plugin")
val coreProject = project(":spokk-core")
dependencies {
    artifactUnderTest(gradlePluginProject)
    artifactUnderTest(compilerPluginProject)
    artifactUnderTest(coreProject)
}

tasks.withType<Test>().configureEach {
    dependsOn(
        gradlePluginProject.tasks.named("publishAllPublicationsToRepoForTestRepository"),
        compilerPluginProject.tasks.named("publishAllPublicationsToRepoForTestRepository"),
        coreProject.tasks.named("publishAllPublicationsToRepoForTestRepository")
    )
    inputs.files(artifactUnderTest)
    systemProperty("spokk.gradlePluginRepo", gradlePluginProject.extra["repoForTest"].toString())
    systemProperty("spokk.compilerPluginRepo", compilerPluginProject.extra["repoForTest"].toString())
    systemProperty("spokk.coreRepo", coreProject.extra["repoForTest"].toString())
}
