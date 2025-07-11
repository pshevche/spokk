plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.kapt)
    id("spokk.artifact-under-test-producer")
    id("spokk.kotlin-library")
}

dependencies {
    kapt(libs.google.autoservice)

    compileOnly(libs.google.autoservice.annotations)
    implementation(libs.junit.platform.engine)
}

group = "io.github.pshevche.spokk"
version = "0.1"

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "spokk-core"
            from(components["kotlin"])
        }
    }
}
