package io.github.pshevche.spokk.runtime.engine.util

import io.github.pshevche.spokk.lang.internal.SpecMetadata
import java.lang.reflect.Modifier

object SpecUtil {
    fun isRunnableSpec(clazz: Class<*>): Boolean {
        return clazz.isAnnotationPresent(SpecMetadata::class.java) && !Modifier.isAbstract(clazz.modifiers)
    }
}
