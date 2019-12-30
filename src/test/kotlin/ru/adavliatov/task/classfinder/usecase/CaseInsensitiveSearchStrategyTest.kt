package ru.adavliatov.task.classfinder.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

class CaseInsensitiveSearchStrategyTest {
    private val strategy = CaseInsensitiveSearchStrategy()

    @Test
    fun `should be applicable for valid input`() {
        assertTrue(strategy.applicableFor("abc"))
        assertTrue(strategy.applicableFor("  abd  "))
    }

    @Test
    fun `should NOT be applicable for invalid input`() {
        assertFalse(strategy.applicableFor(""))
        assertFalse(strategy.applicableFor("    "))
        assertFalse(strategy.applicableFor("aBd"))
    }

    @Test
    fun `should have valid satisfy`() {
        assertTrue(strategy.satisfies("ABC", "abc"))
        assertTrue(strategy.satisfies("AxbyCz", "abc"))

        assertFalse(strategy.satisfies("AxbyDz", "abc"))
    }

    private fun CaseInsensitiveSearchStrategy.satisfies(input: Input, item: Item) =
        satisfies(input, strategy.contextFor(item)).first
}