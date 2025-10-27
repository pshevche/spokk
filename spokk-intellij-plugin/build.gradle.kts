import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    alias(libs.plugins.intellij.platform)
    id("spokk.kotlin-library")
}

group = "io.github.pshevche.spokk"
version = "0.1"

repositories {
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(TestFrameworkType.Platform)
        testFramework(TestFrameworkType.Plugin.Java)
        bundledPlugin("org.jetbrains.kotlin")
        bundledPlugin("org.jetbrains.plugins.gradle")
    }

    testImplementation(libs.hamcrest)
    testImplementation(libs.junit4)
}

val releaseNotesFile = layout.projectDirectory.dir("docs").file("release-notes.txt")
intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = releaseNotesFile.asFile.readText()
    }
}
