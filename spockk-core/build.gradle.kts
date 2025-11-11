plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.kapt)
    id("spockk.artifact-under-test-producer")
    id("spockk.kotlin-library")
}

dependencies {
    kapt(libs.google.autoservice)

    compileOnly(libs.google.autoservice.annotations)
    implementation(libs.junit.platform.engine)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "spockk-core"
            from(components["kotlin"])
        }
    }
}
