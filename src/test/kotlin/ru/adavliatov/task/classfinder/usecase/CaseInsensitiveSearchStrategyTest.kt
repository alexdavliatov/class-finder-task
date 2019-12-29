package ru.adavliatov.task.classfinder.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

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
}