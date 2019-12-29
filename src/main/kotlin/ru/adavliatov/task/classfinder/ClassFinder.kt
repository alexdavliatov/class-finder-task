package ru.adavliatov.task.classfinder

import ru.adavliatov.task.classfinder.config.Config
import ru.adavliatov.task.classfinder.config.ConfigDSL.Companion.config
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.Item
import ru.adavliatov.task.classfinder.domain.ItemWithPrepared
import ru.adavliatov.task.classfinder.usecase.*
import ru.adavliatov.task.classfinder.usecase.handler.Handler
import ru.adavliatov.task.classfinder.usecase.handler.invoke

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

    fun find(input: Input, items: Sequence<Item>): Sequence<Item> {
        val searchStrategy = config.strategyFor(input)

        return items
            .map {
                it.prepared(config.itemHandlers())
            }
            .filter { (_, preparedItem) -> searchStrategy.satisfies(preparedItem) }
            .sortedBy { it.preparedItem }
            .mapNotNull { it.item }
    }

    companion object {
        fun Item.prepared(itemHandler: Handler<Item>) = ItemWithPrepared(this, itemHandler(this))
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
        plus(NonBlankToNullItemHandler())
        plus(PackageRemoverHandler())
    }
}

fun main() {
    val input: Input = "abc"
    val items = sequenceOf("abc").mapNotNull(config.itemHandlers())

    ClassFinder(config)
        .find(input, items)
        .joinToString("\n")
        .run { println(this) }
}
