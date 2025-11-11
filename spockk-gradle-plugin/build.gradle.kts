plugins {
    `java-gradle-plugin`
    alias(libs.plugins.plugin.publish)
    id("spockk.artifact-under-test-producer")
    id("spockk.compiler-plugin-config")
    id("spockk.kotlin-library")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    val spockk by plugins.creating {
        id = "io.github.pshevche.spockk"
        implementationClass = "io.github.pshevche.spockk.compilation.SpockkGradlePlugin"
        description = "Applies the Spockk Kotlin compiler plugin to transform concise specification syntax into runnable tests"
    }
}
