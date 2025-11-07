package io.github.pshevche.spockk.samples

import io.github.pshevche.spockk.lang.expect

class NotASpec {

    fun <caret>helper() {
        println("Hello, World!")
    }
}
