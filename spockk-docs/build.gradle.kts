import org.asciidoctor.gradle.jvm.AsciidoctorTask

repositories {
    mavenCentral()
}

plugins {
    alias(libs.plugins.asciidoctor)
}

val spockkVersion by project.properties
tasks.named("asciidoctor", AsciidoctorTask::class) {
    notCompatibleWithConfigurationCache("issue in org.asciidoctor.jvm.convert")

    baseDirFollowsSourceDir()
    sourceDir(layout.projectDirectory.dir("docs"))
    setOutputDir(layout.buildDirectory.dir("docs"))
    resources {
        from(layout.projectDirectory.files("docs/images")) {
            into("images")
        }
    }
    attributes(
        mapOf(
            "imagesdir" to "images",
            "revnumber" to spockkVersion,
            "junitPlatformVersion" to libs.versions.junit.platform.get()
        )
    )
}
