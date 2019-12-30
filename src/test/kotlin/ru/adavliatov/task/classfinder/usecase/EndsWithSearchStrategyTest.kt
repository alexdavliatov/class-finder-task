package ru.adavliatov.task.classfinder.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

class EndsWithSearchStrategyTest {
    private val strategy = EndsWithSearchStrategy()

    @Test
    fun `should be applicable for valid input`() {
        assertTrue(strategy.applicableFor("abc   "))
        assertTrue(strategy.applicableFor("  abd  "))
    }

    @Test
    fun `should NOT be applicable for invalid input`() {
        assertFalse(strategy.applicableFor(""))
        assertFalse(strategy.applicableFor("    "))
        assertFalse(strategy.applicableFor("aBd"))
        assertFalse(strategy.applicableFor("abd"))
    }

    @Test
    fun `should have valid satisfy`() {
        assertTrue(strategy.satisfies("FooBar", "Bar "))

        assertFalse(strategy.satisfies("FooBarBaz", "Bar "))
    }

    private fun EndsWithSearchStrategy.satisfies(input: Input, item: Item) =
        satisfies(input, strategy.contextFor(item)).first
}