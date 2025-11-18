<h1>
  <picture width="200">
    <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/pshevche/spockk/refs/heads/main/spockk-docs/docs/images/name_with_icon_dark.svg">
    <source media="(prefers-color-scheme: light)" srcset="https://raw.githubusercontent.com/pshevche/spockk/refs/heads/main/spockk-docs/docs/images/name_with_icon_light.svg">
    <img alt="Spockk" src="https://raw.githubusercontent.com/pshevche/spockk/refs/heads/main/spockk-docs/docs/images/name_with_icon_light.svg" width="200">
  </picture>
</h1>

This repository is home to Spockk, a testing framework that brings [Spock](https://github.com/spockframework/spock)'s expressive BDD-style syntax for Groovy to Kotlin.

## Sneak peek

```kotlin
import io.github.pshevche.spockk.lang.and
import io.github.pshevche.spockk.lang.given
import io.github.pshevche.spockk.lang.`when`
import io.github.pshevche.spockk.lang.then

class MyFirstSpecification {
    fun `adding an element to a list`() {
        given
        val myList = mutableListOf<Int>()

        `when`
        myList.add(1)

        and
        myList.add(2)

        then
        assert(myList.size == 2)
    }
}
```

## Getting started

- [User guide](https://pshevche.github.io/spockk/)
- [Example project](https://github.com/pshevche/spockk-example)

## Development

### High-level design

The Spock framework for Groovy relies on AST transformations to transform its expressive specification syntax into runnable specification classes.
The Spockk framework achieves a similar behavior by implementing a Kotlin compiler plugin ([examples](https://kotlinlang.org/docs/all-open-plugin.html#0)).
The following diagram shows the simplified interaction between all the different components that Spockk is composed of.

```mermaid
graph TB
    gradle["spockk-gradle-plugin"]
    intellij["spockk-intellij-plugin"]

    subgraph compiler["Kotlin Compiler"]
        sources["Sources (.kt)"]
        frontend["Compiler frontend"]
        backend["JVM IR backend"]
        plugin["spockk-compiler-plugin"]
        bytecode["Bytecode"]

        sources --> frontend
        frontend -->|generate intermediate representation| backend
        backend --> plugin
        plugin -->|transform IR into executable tests| bytecode
    end

    gradle -->|applies spockk-compiler-plugin| compiler
    intellij -->|detects specifications and features| sources
```

### Modules

- [`spockk-compiler-plugin`](spockk-compiler-plugin/README.adoc): implements IR transformations that modify the simplified test syntax into executable tests.
- [`spockk-core`](spockk-core/README.adoc): declares the specification syntax and implements the JUnit Platform `TestEngine` capable of executing transformed tests.
- [`spockk-docs`](spockk-docs/README.adoc): module with user guide.
- [`spockk-gradle-plugin`](spockk-gradle-plugin/README.adoc): Gradle plugin that abstracts away the application of the `spockk-compiler-plugin` to Kotlin compiler invocations.
- [`spockk-intellij-plugin`](spockk-intellij-plugin/README.adoc): provides support for Spockk tests in IntelliJ.
- [`spockk-specs`](spockk-specs/README.adoc): specifications for the framework written with Spockk.
