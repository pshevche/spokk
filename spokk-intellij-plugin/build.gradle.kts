import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.extensions.excludeCoroutines

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
        intellijIdeaCommunity("2025.2.4")

        bundledPlugin("org.jetbrains.kotlin")
        bundledPlugin("org.jetbrains.plugins.gradle")

        testFramework(TestFrameworkType.Platform)
        testFramework(TestFrameworkType.Plugin.Java)
    }

    testImplementation(libs.hamcrest)
    testImplementation(libs.junit4)
    testImplementation(libs.mockk) {
        // see https://github.com/JetBrains/intellij-platform-gradle-plugin/issues/2029#issuecomment-3385764519
        excludeCoroutines()
    }
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
