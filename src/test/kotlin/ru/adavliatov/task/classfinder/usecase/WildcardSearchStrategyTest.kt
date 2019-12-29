package ru.adavliatov.task.classfinder.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

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

}