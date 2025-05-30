import gradle.kotlin.dsl.accessors._cff12a2f85a9cfdf5a2f9101bbe0d2e0.ext

plugins {
    `maven-publish`
}

configurations.create("pluginUnderTest") {
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "plugin-under-test"))
    }
}

val repoForTest = layout.buildDirectory.dir("repoForTest")
publishing {
    repositories {
        maven {
            name = "repoForTest"
            url = uri(repoForTest)
        }
    }
}

ext.set("repoForTest", repoForTest.get().asFile.absolutePath)

artifacts {
    add("pluginUnderTest", repoForTest)
}
