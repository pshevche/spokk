plugins {
    `java-test-fixtures`
    id("spokk.compiler-plugin-consumer")
    id("spokk.plugin-under-test-consumer")
}

dependencies {
    testImplementation(gradleTestKit())
    // TODO: dogfood spokk instead
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
