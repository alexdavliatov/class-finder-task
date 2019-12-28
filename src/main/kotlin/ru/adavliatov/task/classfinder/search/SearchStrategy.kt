package ru.adavliatov.task.classfinder.search

import ru.adavliatov.task.classfinder.item.Item

interface SearchStrategy {
    fun satisfies(item: Item?): Boolean = false
}

class CommonSearchStrategy : SearchStrategy
class CaseInsensitiveSearchStrategy : SearchStrategy
class EndsWithSearchStrategy : SearchStrategy
class WildcardSearchStrategy : SearchStrategy