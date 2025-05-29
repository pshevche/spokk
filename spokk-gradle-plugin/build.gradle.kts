plugins {
    `java-gradle-plugin`
    alias(libs.plugins.plugin.publish)
    id("spokk.compiler-plugin-component")
    id("spokk.kotlin-library")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    val spokk by plugins.creating {
        id = "io.github.pshevche.spokk"
        implementationClass = "io.github.pshevche.spokk.plugin.SpokkGradlePlugin"
    }
}
