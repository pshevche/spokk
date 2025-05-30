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

extra["repoForTest"] = repoForTest.get().asFile.absolutePath

artifacts {
    add("pluginUnderTest", repoForTest)
}
