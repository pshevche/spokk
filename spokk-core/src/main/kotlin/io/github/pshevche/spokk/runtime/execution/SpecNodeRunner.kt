package io.github.pshevche.spokk.runtime.execution

import io.github.pshevche.spokk.runtime.node.SpecInfo

/**
 * Executes a `SpecNode` in the test hierarchy.
 */
internal class SpecNodeRunner {

    private var currentSpecInstance: Any? = null

    fun createSpecInstance(spec: SpecInfo, errorInfoCollector: ErrorInfoCollector) {
        try {
            currentSpecInstance = spec.reflection.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            errorInfoCollector.addErrorInfo(e)
        }
    }

    fun runFeatureMethod(
        currentFeature: io.github.pshevche.spokk.runtime.node.FeatureInfo,
        errorInfoCollector: ErrorInfoCollector,
    ) {
        try {
            currentFeature.reflection.invoke(currentSpecInstance!!)
        } catch (e: Exception) {
            errorInfoCollector.addErrorInfo(e)
        }
    }
}
