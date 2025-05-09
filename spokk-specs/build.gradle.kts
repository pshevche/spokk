plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(project(":spokk-core"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
