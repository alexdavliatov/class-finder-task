package ru.adavliatov.task.classfinder.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

class WildcardSearchStrategyTest {
    private val strategy = WildcardSearchStrategy()

    @Test
    fun `should be applicable for valid input`() {
        assertTrue(strategy.applicableFor("*"))
        assertTrue(strategy.applicableFor("abd*cd"))
        assertTrue(strategy.applicableFor("aBc*cd"))
        assertTrue(strategy.applicableFor("aB*"))
    }

    @Test
    fun `should NOT be applicable for invalid input`() {
        assertFalse(strategy.applicableFor(""))
        assertFalse(strategy.applicableFor("    "))
        assertFalse(strategy.applicableFor("abc"))
    }

    @Test
    fun `should have valid satisfy`() {
        assertTrue(strategy.satisfies("FooBarBaz", "B*rBaz"))
        assertTrue(strategy.satisfies("Bxaxr", "B*a*r"))
        assertTrue(strategy.satisfies("BeaEr", "B*a*r"))

        assertFalse(strategy.satisfies("BrBaz", "B*r"))
        assertFalse(strategy.satisfies("BrrBaz", "B**r"))
        assertFalse(strategy.satisfies("BeaE", "B*a*E"))
    }

    private fun WildcardSearchStrategy.satisfies(input: Input, item: Item) =
        satisfies(input, strategy.contextFor(item)).first
}