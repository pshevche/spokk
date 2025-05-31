package io.github.pshevche.spokk.runtime

import org.opentest4j.MultipleFailuresError

class SpokkMultipleFailuresError(heading: String, failures: List<Throwable>) :
    MultipleFailuresError(heading, failures) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
