package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.InputExtensions.nextItem
import ru.adavliatov.task.classfinder.domain.Item

@Suppress("UNCHECKED_CAST")
data class ItemWithStrategy<C : Context>(val context: C, val strategy: SearchStrategy<C>) {
    fun satisfies(item: Item, context: Context) = strategy.satisfies(item, context as C)
}

interface SearchStrategy<C : Context> {
    fun applicableFor(item: Item): Boolean
    fun nextItem(input: Item): Pair<Item, Item>
    fun contextFor(item: Item): C
    fun satisfies(item: Item, context: C): Pair<Boolean, C>
}

fun <C : Context> SearchStrategy<C>.toItemWithStrategy(item: Item) = ItemWithStrategy(contextFor(item), this)

class CaseInsensitiveSearchStrategy : SearchStrategy<SingleItemContext> {
    override fun applicableFor(item: Item): Boolean {
        val trimmed = item.trim()
        val lowered = trimmed.toLowerCase()

        return trimmed.isNotEmpty() && trimmed == lowered
    }

    override fun nextItem(input: Item): Pair<Item, Item> = input to ""
    override fun contextFor(item: Item) = SingleItemContext(item.toLowerCase())
    override fun satisfies(item: Item, context: SingleItemContext): Pair<Boolean, SingleItemContext> {
        val lowered = item.toLowerCase()
        val detailsItem = context.item

        var pointerItem = 0
        var pointerDetails = 0

        while (pointerItem < item.length && pointerDetails < detailsItem.length) {
            if (lowered[pointerItem] == detailsItem[pointerDetails]) {
                pointerDetails++
            }
            pointerItem++
        }

        return (pointerDetails == detailsItem.length) to SingleItemContext(detailsItem.substring(pointerDetails))
    }
}

class StartsWithSearchStrategy : SearchStrategy<SingleItemContext> {
    override fun nextItem(input: Item): Pair<Item, Item> = input.nextItem()
    override fun applicableFor(item: Item) = item.isValid()
    override fun contextFor(item: Item): SingleItemContext = SingleItemContext(item)
    override fun satisfies(item: Item, context: SingleItemContext) = item.startsWith(context.item) to context
}

class EndsWithSearchStrategy : SearchStrategy<SingleItemContext> {
    override fun applicableFor(item: Item): Boolean {
        val trimmedEnd = item.trimEnd()
        return trimmedEnd.isNotEmpty() && item != item.trimEnd()
    }

    override fun nextItem(input: Item): Pair<Item, Item> = input to ""

    override fun contextFor(item: Item): SingleItemContext = SingleItemContext(item.trim())
    override fun satisfies(item: Item, context: SingleItemContext) = item.endsWith(context.item) to context
}

class WildcardSearchStrategy : SearchStrategy<MultipleItemsContext> {
    override fun nextItem(input: Item): Pair<Item, Item> = input.nextItem()
    override fun applicableFor(item: Item) = item.contains('*')
    override fun contextFor(item: Item): MultipleItemsContext = MultipleItemsContext(item.split('*'))
    override fun satisfies(item: Item, context: MultipleItemsContext) = context.satisfies(item)
}


fun Char.isUpperCase() = this in 'A'..'Z'
fun Char.isValid() = this in 'A'..'Z' || this in 'a'..'z' || this in setOf('*', ' ')
fun Item.isValid() = toCharArray().all { it.isValid() }