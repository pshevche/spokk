package io.github.pshevche.spokk.intellij

import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import java.nio.file.Paths

class SpokkUnusedBlockInspectionSuppressorTest : LightJavaCodeInsightFixtureTestCase() {

    private lateinit var suppressor: SpokkUnusedBlockInspectionSuppressor

    override fun setUp() {
        super.setUp()
        suppressor = SpokkUnusedBlockInspectionSuppressor()
    }

    override fun getTestDataPath(): String {
        val path = Paths.get("./src/test/resources/SpokkUnusedLabelInspectionSuppressorTest/").toAbsolutePath()
        return path.toString()
    }

    fun testSuppressUnusedWarningsForSpokkBlockObjectReferences() {
        // given
        myFixture.configureByFile("/testSuppressUnusedWarningsForSpokkBlockObjectReferences/SimpleSpec.kt")

        // expect
        assertTrue(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedExpression"))
    }

    fun testWarnsAboutSpokkObjectReferencesForOtherInspections() {
        // given
        myFixture.configureByFile("/testWarnsAboutSpokkObjectReferencesForOtherInspections/SimpleSpec.kt")

        // expect
        assertFalse(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedDeclaration"))
    }

    fun testWarnsAboutUnusedNonSpokkObjectReferences() {
        // given
        myFixture.configureByFile("/testWarnsAboutUnusedNonSpokkObjectReferences/UnusedObjectReference.kt")

        // expect
        assertFalse(suppressor.isSuppressedFor(myFixture.findElementByText("expect", PsiElement::class.java), "UnusedExpression"))
    }

}
