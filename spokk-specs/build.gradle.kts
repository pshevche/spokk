plugins {
    id("spokk.compiler-plugin-consumer")
}

dependencies {
    testImplementation(gradleTestKit())
    // TODO: dogfood spokk instead
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
