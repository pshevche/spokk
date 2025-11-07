plugins {
    checkstyle
    `java-library`
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

checkstyle {
    configFile = rootProject.layout.projectDirectory.file("gradle/config/checkstyle.xml").asFile
    maxWarnings = 3
}

detekt {
    config.from(rootProject.layout.projectDirectory.file("gradle/config/detekt.yml"))
}
