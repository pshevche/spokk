package io.github.pshevche.spokk.intellij

import com.intellij.icons.AllIcons
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import java.nio.file.Paths

class RunMarkerTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/RunMarkerTest/").toAbsolutePath()
        return path.toString()
    }

    fun testRunMarkerOnSpec() {
        // given
        myFixture.configureByFile("/testRunMarkerOnSpec/SimpleSpec.kt")

        // when
        val gutters = myFixture.findAllGutters()

        // then
        assertThat(gutters, hasSize(3))
        assertThat(gutters[0].icon, equalTo(AllIcons.RunConfigurations.TestState.Run_run))
        assertThat(gutters[1].icon, equalTo(AllIcons.RunConfigurations.TestState.Run))
        assertThat(gutters[2].icon, equalTo(AllIcons.RunConfigurations.TestState.Run))
    }

    fun testNoRunMarkerOnNonSpec() {
        // given
        myFixture.configureByFile("/testNoRunMarkerOnNonSpec/NotASpec.kt")

        // when
        val gutters = myFixture.findAllGutters()

        // then
        assertThat(gutters, empty())
    }

    fun testRunMarkersOnlyForFeatureMethods() {
        // given
        myFixture.configureByFile("/testRunMarkersOnlyForFeatureMethods/SpecWithHelperMethods.kt")

        // when
        val gutters = myFixture.findAllGutters()

        // then
        assertThat(gutters, hasSize(2))
        assertThat(gutters[0].icon, equalTo(AllIcons.RunConfigurations.TestState.Run_run))
        assertThat(gutters[1].icon, equalTo(AllIcons.RunConfigurations.TestState.Run))
    }

    fun testNoRunMarkerOnNonKotlin() {
        // given
        myFixture.configureByFile("/testNoRunMarkerOnNonKotlin/LooksLikeJavaSpec.java")

        // when
        val gutters = myFixture.findAllGutters()

        // then
        assertThat(gutters, empty())
    }
}
