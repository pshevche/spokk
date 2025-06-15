package io.github.pshevche.spokk.runtime.util

import io.github.pshevche.spokk.lang.internal.SpecMetadata
import java.lang.reflect.Modifier

internal object SpecUtil {
    fun isRunnableSpec(clazz: Class<*>): Boolean {
        return clazz.isAnnotationPresent(SpecMetadata::class.java) && !Modifier.isAbstract(clazz.modifiers)
    }
}
