package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

interface SearchStrategy {
    fun applicableFor(input: Input) = false
    fun satisfies(item: Item?): Boolean = false
}

class CommonSearchStrategy : SearchStrategy {
    override fun applicableFor(input: Input) = true
}

class CaseInsensitiveSearchStrategy : SearchStrategy {
    override fun applicableFor(input: Input): Boolean {
        val trimmed = input.trim()
        val lowered = trimmed.toLowerCase()

        return trimmed.isNotEmpty() && trimmed == lowered
    }
}

class EndsWithSearchStrategy : SearchStrategy {
    override fun applicableFor(input: Input): Boolean {
        val trimmedEnd = input.trimEnd()
        return trimmedEnd.isNotEmpty() && input != input.trimEnd()
    }
}

class WildcardSearchStrategy : SearchStrategy {
    override fun applicableFor(input: Input) = input.contains('*')
}