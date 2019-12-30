package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.config.Config
import ru.adavliatov.task.classfinder.domain.Item
import ru.adavliatov.task.classfinder.domain.ItemWithPrepared
import ru.adavliatov.task.classfinder.usecase.StrategiesExtensions.satisfies
import ru.adavliatov.task.classfinder.usecase.StrategiesExtensions.toItemsWithStrategy
import ru.adavliatov.task.classfinder.usecase.handler.Handler
import ru.adavliatov.task.classfinder.usecase.handler.HandlerExtensions.invoke

class ClassFinder(private val config: Config) {

    fun find(input: Item, items: Sequence<Item>): Sequence<Item> = items
        .mapNotNull { it.prepared(config.itemHandlers()) }
        .filter { (_, preparedItem) -> input.toItemsWithStrategy(config.searchStrategies).satisfies(preparedItem) }
        .sortedBy { it.preparedItem }
        .mapNotNull { it.item }

    companion object {
        fun Item.prepared(itemHandler: Handler<Item>) =
            itemHandler(this)?.let { ItemWithPrepared(this, it) }
    }
}