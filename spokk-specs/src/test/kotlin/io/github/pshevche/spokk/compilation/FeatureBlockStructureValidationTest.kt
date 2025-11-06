package io.github.pshevche.spokk.compilation

import io.github.pshevche.spokk.compilation.TestDataFactory.specWithFeatureBody
import io.github.pshevche.spokk.fixtures.compilation.CompilationUtils.transform
import io.github.pshevche.spokk.lang.then
import io.github.pshevche.spokk.lang.`when`
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class FeatureBlockStructureValidationTest {

    fun `accepts valid block sequences (single expectation)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.expect
            assert(true)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (multiple expectations)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.expect
            assert(true)
            
            io.github.pshevche.spokk.lang.and
            assert(true)
            
            io.github.pshevche.spokk.lang.and
            assert(true)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (single expectation with precondition)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.given
            val a = 1
            
            io.github.pshevche.spokk.lang.expect
            assert(a == 1)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (single expectation with multiple preconditions)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.given
            val a = 1
            
            io.github.pshevche.spokk.lang.and
            val b = 1
            
            io.github.pshevche.spokk.lang.expect
            assert(a + b == 2)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (single expectation with single action and precondition)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.given
            val a = 1
            val b = 1
            
            io.github.pshevche.spokk.lang.`when`
            val c = a + b
            
            io.github.pshevche.spokk.lang.then
            assert(c == 2)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (single expectation with multiple actions)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.`when`
            val a = 1
            
            io.github.pshevche.spokk.lang.and
            val b = 1
            
            io.github.pshevche.spokk.lang.and
            val c = a + b
            
            io.github.pshevche.spokk.lang.then
            assert(c == 2)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (multiple expectations with single action)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.`when`
            val a = 1
            val b = 1
            
            io.github.pshevche.spokk.lang.then
            assert(a == 1)
            
            io.github.pshevche.spokk.lang.and
            assert(b == 1)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `accepts valid block sequences (multiple expectations with multiple actions)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.`when`
            val a = 1
            
            io.github.pshevche.spokk.lang.then
            assert(a == 1)
            
            io.github.pshevche.spokk.lang.`when`
            val b = 1
            
            io.github.pshevche.spokk.lang.then
            assert(b == 1)
        """.trimIndent()
            )
        )

        then
        assert(result.isSuccess())
    }

    fun `discards invalid block sequences (precondition with missing expectation)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.given
            val a = 1
        """.trimIndent()
            )
        )

        then
        assert(!result.isSuccess())
        assert(result.compilation.messages.contains("Spec.kt:3:21"))
        assert(
            result.compilation.messages.contains(
                """
            Problem with `given`
            Details: Expected to find one of spokk blocks ['and', 'when', 'expect'], but reached the end of the feature method
        """.trimIndent()
            )
        )
    }

    fun `discards invalid block sequences (action with missing expectation)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.`when`
            val a = 1
        """.trimIndent()
            )
        )

        then
        assert(!result.isSuccess())
        assert(result.compilation.messages.contains("Spec.kt:3:21"))
        assert(
            result.compilation.messages.contains(
                """
            Problem with `when`
            Details: Expected to find one of spokk blocks ['and', 'then'], but reached the end of the feature method
        """.trimIndent()
            )
        )
    }

    fun `discards invalid block sequences (invalid block order)`() {
        `when`
        val result = transform(
            specWithFeatureBody(
                """
            io.github.pshevche.spokk.lang.`when`
            val a = 1
            
            io.github.pshevche.spokk.lang.expect
            assert(a == 1)
        """.trimIndent()
            )
        )

        then
        assert(!result.isSuccess())
        assert(result.compilation.messages.contains("Spec.kt:6:1"))
        assert(
            result.compilation.messages.contains(
                """
            Problem with `expect`
            Details: Expected to find one of spokk blocks ['and', 'then'], but encountered 'expect'
        """.trimIndent()
            )
        )
    }
}
