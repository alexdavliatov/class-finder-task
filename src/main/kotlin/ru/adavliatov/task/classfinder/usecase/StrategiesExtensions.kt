package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Item
import ru.adavliatov.task.classfinder.domain.SearchStrategyNotFoundError

object StrategiesExtensions {
    fun List<ItemWithStrategy<*>>.satisfies(item: Item): Boolean {
        var pointer = 0
        var currentInput = item

        while (pointer < size && currentInput.isNotBlank()) {
            var terminated = false
            val itemWithStrategy = this[pointer]
            var details = itemWithStrategy.context

            while (!terminated && currentInput.isNotBlank()) {
                val (nextItem, nextInput) = itemWithStrategy.strategy.nextItem(currentInput)
                val result = itemWithStrategy.satisfies(nextItem, details)

                terminated = result.first
                details = result.second
                currentInput = nextInput

                if (terminated) pointer++
            }
        }

        return pointer == size
    }

    fun Item.toItemsWithStrategy(predefinedStrategies: List<SearchStrategy<*>>): List<ItemWithStrategy<*>> {
        val strategies = mutableListOf<ItemWithStrategy<*>>()
        var word = StringBuilder()
        for (sym in asSequence()) {
            if (sym.isUpperCase()) {
                if (word.isNotEmpty()) {
                    strategies += word.toStrategy(predefinedStrategies)
                    word = StringBuilder()
                }
            }
            word.append(sym)
        }

        strategies += word.toStrategy(predefinedStrategies)
        return strategies
    }

    private fun StringBuilder.toStrategy(predefined: List<SearchStrategy<*>>): ItemWithStrategy<*> {
        val item = toString()
        val strategy = predefined.firstOrNull { it.applicableFor(item) } ?: throw SearchStrategyNotFoundError(item)
        return strategy.toItemWithStrategy(item)
    }
}