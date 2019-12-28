package ru.adavliatov.task.classfinder.config

import ru.adavliatov.task.classfinder.item.handler.ItemHandler
import ru.adavliatov.task.classfinder.search.SearchStrategy

data class Config(
    val searchStrategies: List<SearchStrategy>,
    val itemHandlers: List<ItemHandler>
) {
    fun strategyFor(@Suppress("UNUSED_PARAMETER") input: String): SearchStrategy =
        TODO("strategyFor not implemented yet")
}

class ConfigDSL {
    var searchStrategies: List<SearchStrategy> = listOf()
    var itemHandlers: List<ItemHandler> = listOf()

    fun searchStrategies(body: SearchStrategies.() -> Unit) {
        searchStrategies = SearchStrategies().apply(body)
    }

    fun itemHandlers(body: ItemHandlers.() -> Unit) {
        itemHandlers = ItemHandlers().apply(body)
    }

    fun build() = Config(searchStrategies, itemHandlers)

    companion object {
        fun config(default: ConfigDSL = ConfigDSL(), body: ConfigDSL.() -> Unit) = default.apply(body).build()
    }
}

class SearchStrategies : ArrayList<SearchStrategy>()
class ItemHandlers : ArrayList<ItemHandler>()