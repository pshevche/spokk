plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.kapt)
    id("spokk.artifact-under-test-producer")
    id("spokk.compiler-plugin-producer")
}

dependencies {
    kapt(libs.google.autoservice)

    compileOnly(libs.google.autoservice.annotations)
    compileOnly(libs.kotlin.compiler.embeddable)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name
            from(components["kotlin"])
        }
    }
}
