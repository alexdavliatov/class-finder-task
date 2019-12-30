package ru.adavliatov.task.classfinder.usecase

import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.adavliatov.task.classfinder.Configs
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

class ClassFinderTest {
    private val finder = ClassFinder(Configs.default)

    @Test
    fun `should correctly handle starts with`() {
        assertThat(
            finder.find(
                "FB",
                sequenceOf("a.b.c.d.FooBarBaz", "c.d.FooBar")
            ).count(),
            IsEqual(2)
        )
        assertThat(
            finder.find(
                "FBar",
                sequenceOf("a.b.c.d.FooBarBaz", "c.d.FooBar")
            ).count(),
            IsEqual(2)
        )

        assertThat(
            finder.find(
                "FoBa",
                sequenceOf("a.b.c.d.FooBarBaz", "c.d.FooBar")
            ).count(),
            IsEqual(2)
        )
    }

    @Test
    fun `should correctly handle invalid order`() {
        assertThat(
            finder.find(
                "BF",
                sequenceOf("c.d.FooBar")
            ).count(),
            IsEqual(0)
        )
    }

    @Test
    fun `should correctly handle case insensitive order`() {
        assertThat(
            finder.find(
                "fbb",
                sequenceOf("c.d.FooBarBaz")
            ).first(),
            IsEqual("c.d.FooBarBaz")
        )
        assertThat(
            finder.find(
                "fBb",
                sequenceOf("c.d.FooBarBaz")
            ).count(),
            IsEqual(0)
        )
    }

    @Test
    fun `should correctly handle ends with`() {
        assertThat(
            finder.find(
                "FBar ",
                sequenceOf("c.d.FooBarBaz")
            ).count(),
            IsEqual(0)
        )
        assertThat(
            finder.find(
                "FBar ",
                sequenceOf("c.d.FooBar")
            ).count(),
            IsEqual(1)
        )
    }

    @Test
    fun `should correctly handle wildcards`() {
        assertThat(
            finder.find(
                "B*rBaz",
                sequenceOf("c.d.FooBarBaz")
            ).count(),
            IsEqual(1)
        )
        assertThat(
            finder.find(
                "FBar ",
                sequenceOf("c.d.FooBrBaz")
            ).count(),
            IsEqual(0)
        )
    }

    @Test
    fun `should correctly handle tricky cases`() {
        //tricky
        assertThat(finder.find("FB", "a.b.c.d.FooBarBaz").first(), IsEqual("a.b.c.d.FooBarBaz"))
        assertThat(
            finder.find("FoB*ar*rd**Baz", "a.b.c.d.FuxFooBxarCprqDrdyETBaz").first(),
            IsEqual("a.b.c.d.FuxFooBxarCprqDrdyETBaz")
        )

        assertTrue(finder.find("FoB*ar*rd**Baz", "a.b.c.d.FuxFooBxarCprqDrdyTBaz").toList().isEmpty())
    }

    private fun ClassFinder.find(input: Input, item: Item) = find(input, sequenceOf(item))
}