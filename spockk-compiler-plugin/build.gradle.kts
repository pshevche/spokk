plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.kapt)
    id("spockk.artifact-under-test-producer")
    id("spockk.compiler-plugin-producer")
}

dependencies {
    kapt(libs.google.autoservice)

    compileOnly(libs.google.autoservice.annotations)
    compileOnly(libs.kotlin.compiler.embeddable)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "spockk-compiler-plugin"
            from(components["kotlin"])
            description = "Kotlin compiler plugin that transforms Spockk’s concise specification syntax into runnable tests"
        }
    }
}
