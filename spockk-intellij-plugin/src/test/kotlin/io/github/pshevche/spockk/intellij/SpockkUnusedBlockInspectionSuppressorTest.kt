package io.github.pshevche.spockk.intellij

import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import java.nio.file.Paths

class SpockkUnusedBlockInspectionSuppressorTest : LightJavaCodeInsightFixtureTestCase() {

    private lateinit var suppressor: SpockkUnusedBlockInspectionSuppressor

    override fun setUp() {
        super.setUp()
        suppressor = SpockkUnusedBlockInspectionSuppressor()
    }

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/SpockkUnusedLabelInspectionSuppressorTest/").toAbsolutePath()
        return path.toString()
    }

    fun testSuppressUnusedWarningsForSpockkBlockObjectReferences() {
        // given
        myFixture.configureByFile("/testSuppressUnusedWarningsForSpockkBlockObjectReferences/SimpleSpec.kt")

        // expect
        assertTrue(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedExpression"))
    }

    fun testWarnsAboutSpockkObjectReferencesForOtherInspections() {
        // given
        myFixture.configureByFile("/testWarnsAboutSpockkObjectReferencesForOtherInspections/SimpleSpec.kt")

        // expect
        assertFalse(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedDeclaration"))
    }

    fun testWarnsAboutUnusedNonSpockkObjectReferences() {
        // given
        myFixture.configureByFile("/testWarnsAboutUnusedNonSpockkObjectReferences/UnusedObjectReference.kt")

        // expect
        assertFalse(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedExpression"))
    }

}
