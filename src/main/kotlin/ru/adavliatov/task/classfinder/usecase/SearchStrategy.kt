package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item

interface SearchStrategy {
    fun applicableFor(input: Input) = false
    fun satisfies(item: Item?): Boolean = false
}

class CommonSearchStrategy : SearchStrategy
class CaseInsensitiveSearchStrategy : SearchStrategy
class EndsWithSearchStrategy : SearchStrategy
class WildcardSearchStrategy : SearchStrategy