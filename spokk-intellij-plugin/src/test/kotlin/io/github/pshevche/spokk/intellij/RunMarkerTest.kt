package io.github.pshevche.spokk.intellij

import com.intellij.icons.AllIcons
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.everyItem
import org.hamcrest.Matchers.hasSize
import java.nio.file.Paths

class RunMarkerTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/").toAbsolutePath()
        return path.toString()
    }

    fun testRunMarkerOnSpec() {
        // given
        myFixture.configureByFile("/SimpleSpec.kt")

        // when
        val gutters = myFixture.findAllGutters()

        // then
        assertThat(gutters, hasSize(3))
        assertThat(gutters.map { it.icon }, everyItem(equalTo(AllIcons.RunConfigurations.TestState.Run_run)))
    }

    fun testNoRunMarkerOnNonSpec() {

    }

    fun testRunMarkersOnlyForFeatureMethods() {

    }

}
