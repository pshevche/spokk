package io.github.pshevche.spokk.runtime.engine.node

import java.lang.reflect.Method

/**
 * Contains additional data about the feature method (e.g., original reflection method or declaring spec)
 */
class FeatureInfo(val spec: SpecInfo, val reflection: Method) : NodeInfo {

    override fun getName(): String = reflection.name
}
