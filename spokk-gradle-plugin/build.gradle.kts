plugins {
    `java-gradle-plugin`
    alias(libs.plugins.plugin.publish)
    id("spokk.artifact-under-test-producer")
    id("spokk.compiler-plugin-config")
    id("spokk.kotlin-library")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    val spokk by plugins.creating {
        id = "io.github.pshevche.spokk"
        implementationClass = "io.github.pshevche.spokk.compilation.SpokkGradlePlugin"
    }
}
