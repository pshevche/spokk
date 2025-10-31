package io.github.pshevche.spokk.intellij

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import java.nio.file.Paths

class SpokkImplicitUsageProviderTest : LightJavaCodeInsightFixtureTestCase() {

    private lateinit var provider: SpokkImplicitUsageProvider

    override fun setUp() {
        super.setUp()
        provider = SpokkImplicitUsageProvider()
    }

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/SpokkImplicitUsageProviderTest/").toAbsolutePath()
        return path.toString()
    }

    fun testSuppressUnusedWarningsForSpecClasses() {
        // given
        myFixture.configureByFile("/testSuppressUnusedWarningsForSpecClasses/SimpleSpec.kt")

        // expect
        assertTrue(provider.isImplicitUsage(myFixture.elementAtCaret))
    }

    fun testSuppressUnusedWarningsForFeatureMethods() {
        // given
        myFixture.configureByFile("/testSuppressUnusedWarningsForFeatureMethods/SimpleSpec.kt")

        // expect
        assertTrue(provider.isImplicitUsage(myFixture.elementAtCaret))
    }

    fun testHighlightsImplicitUsageOutsideOfSpecsAndFeatures() {
        // given
        myFixture.configureByFile("/testHighlightsImplicitUsageOutsideOfSpecsAndFeatures/ClassAtCaret.kt")

        // expect
        assertFalse(provider.isImplicitUsage(myFixture.elementAtCaret))

        // given
        myFixture.configureByFile("/testHighlightsImplicitUsageOutsideOfSpecsAndFeatures/MethodAtCaret.kt")

        // expect
        assertFalse(provider.isImplicitUsage(myFixture.elementAtCaret))
    }

}
