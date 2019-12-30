package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Item

/**
 * SearchStrategies are stateless.
 * Context represent external state which passed between interactions with Strategy.
 */
interface Context

data class SingleItemContext(val item: Item) : Context
data class MultipleItemsContext(val items: List<Item>, val current: Int = 0) : Context {
    fun satisfies(item: Item): Pair<Boolean, MultipleItemsContext> {
        var currentItem = item
        var tempCurrent = current

        /**
         * Multiple occurrences in the same Item. Example:
         * search: A*bc*rd*
         * item  : Ax[bc]t[rd]y
         */
        while (tempCurrent < items.size
            && currentItem.isNotBlank()
            && currentItem.contains(items[tempCurrent])
        ) {
            //'*' in the end of this block
            currentItem = if (items[tempCurrent] == "")
                currentItem.substring(1)
            else {
                val after = currentItem.substringAfter(items[tempCurrent])
                after
                    .takeIf { after.isNotEmpty() }
                    ?.substring(1)
                    ?: after
            }

            tempCurrent++
        }

        val newDetails = if (tempCurrent > current)
            MultipleItemsContext(items, tempCurrent)
        else
            this

        return (newDetails.current == items.size) to newDetails
    }
}