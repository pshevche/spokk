plugins {
    `java-gradle-plugin`
    alias(libs.plugins.plugin.publish)
    id("spokk.compiler-plugin-config")
    id("spokk.kotlin-library")
    id("spokk.plugin-under-test-producer")
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
