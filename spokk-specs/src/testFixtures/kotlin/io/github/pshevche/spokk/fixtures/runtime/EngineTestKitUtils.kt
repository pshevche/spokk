package io.github.pshevche.spokk.fixtures.runtime

import org.junit.platform.engine.DiscoverySelector
import org.junit.platform.testkit.engine.EngineTestKit
import org.junit.platform.testkit.engine.Events

object EngineTestKitUtils {
    fun execute(selector: DiscoverySelector): Events {
        return EngineTestKit.engine("spokk")
            .selectors(selector)
            .execute()
            .testEvents()
            .debug()
    }
}
