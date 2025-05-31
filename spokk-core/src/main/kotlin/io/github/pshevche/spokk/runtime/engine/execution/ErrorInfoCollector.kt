package io.github.pshevche.spokk.runtime.engine.execution

import io.github.pshevche.spokk.runtime.SpokkMultipleFailuresError

/**
 * Collects test errors during test execution.
 */
class ErrorInfoCollector {
    private val errorInfos: MutableList<Throwable> = mutableListOf()

    fun addErrorInfo(errorInfo: Throwable) {
        errorInfos.add(errorInfo)
    }

    fun isEmpty(): Boolean {
        return errorInfos.isEmpty()
    }

    fun hasErrors(): Boolean {
        return !errorInfos.isEmpty()
    }

    fun assertEmpty() {
        if (!errorInfos.isEmpty()) {
            if (errorInfos.size == 1) {
                throw errorInfos.first()
            }
            throw SpokkMultipleFailuresError("", errorInfos)
        }
    }
}
