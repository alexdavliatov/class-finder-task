package ru.adavliatov.task.classfinder

import ru.adavliatov.task.classfinder.config.Config
import ru.adavliatov.task.classfinder.config.ConfigDSL.Companion.config
import ru.adavliatov.task.classfinder.item.Item
import ru.adavliatov.task.classfinder.item.ItemWithPrepared
import ru.adavliatov.task.classfinder.item.handler.ItemHandler
import ru.adavliatov.task.classfinder.item.handler.NonBlankToNullHandler
import ru.adavliatov.task.classfinder.item.handler.PackageRemoverHandler
import ru.adavliatov.task.classfinder.search.CaseInsensitiveSearchStrategy
import ru.adavliatov.task.classfinder.search.CommonSearchStrategy
import ru.adavliatov.task.classfinder.search.EndsWithSearchStrategy
import ru.adavliatov.task.classfinder.search.WildcardSearchStrategy

//notes:
//+package names ignored
//+order alphabetically
//
//source: visitor (could be file, filesystem, ...) - sequence
//
//strategies:
//validation: add filters
//0. common => Upper case in the right order + lower case
//1. lowercase only => case insensitive
//2. endsWith if end space present

class ClassFinder(private val config: Config) {

    fun find(input: String, items: Sequence<Item>): Sequence<Item> {
        val searchStrategy = config.strategyFor(input)
        return items
            .map {
                it.prepared { item -> item }
            }
            .filter { (_, preparedItem) -> searchStrategy.satisfies(preparedItem) }
            .sortedBy { it.preparedItem }
            .mapNotNull { it.item }
    }

    companion object {
        fun Item.prepared(itemHandler: ItemHandler): ItemWithPrepared = ItemWithPrepared(this, itemHandler(this))
    }
}

val config = config {
    searchStrategies {
        plus(EndsWithSearchStrategy())
        plus(CaseInsensitiveSearchStrategy())
        plus(WildcardSearchStrategy())
        plus(CommonSearchStrategy())
    }
    itemHandlers {
        plus(NonBlankToNullHandler())
        plus(PackageRemoverHandler())
    }
}

fun main() {
    val input = "abc"
    val items = sequenceOf("abc").mapNotNull { it.map(config.itemHandlers) }
    ClassFinder(config)
        .find(input, items)
        .joinToString("\n")
        .run { println(this) }
}

fun Item?.map(handlers: Iterable<ItemHandler>) = handlers.fold(this) { acc, handler -> handler(acc) }

