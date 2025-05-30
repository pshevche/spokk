plugins {
    id("spokk.kotlin-library")
    id("spokk.compiler-plugin-config")
}

configurations.create("compilerPlugin") {
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "compiler-plugin"))
    }
}

artifacts {
    add("compilerPlugin", tasks.named("jar"))
}
