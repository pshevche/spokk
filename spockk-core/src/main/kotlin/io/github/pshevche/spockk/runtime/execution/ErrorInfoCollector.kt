/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pshevche.spockk.runtime.execution

import io.github.pshevche.spockk.runtime.SpockkMultipleFailuresError

/**
 * Collects test errors during test execution.
 */
internal class ErrorInfoCollector {
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
            throw SpockkMultipleFailuresError("", errorInfos)
        }
    }
}
