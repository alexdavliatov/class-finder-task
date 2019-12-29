package ru.adavliatov.task.classfinder.usecase

import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Test

class MiddleWhitespacesToNullInputHandlerTest {
    private val handler = MiddleWhitespacesToNullInputHandler()

    @Test
    fun `should replace input with middle whitespaces to null`() {
        assertThat(handler.invoke("12345 6"), nullValue())
    }

    @Test
    fun `should ignore whitespaces at the beginning or end`() {
        assertThat(handler.invoke(" 123456 "), IsEqual(" 123456 "))
    }
}