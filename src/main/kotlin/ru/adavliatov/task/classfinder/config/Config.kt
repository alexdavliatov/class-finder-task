package ru.adavliatov.task.classfinder.config

import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.SearchStrategyNotFoundError
import ru.adavliatov.task.classfinder.usecase.ItemHandler
import ru.adavliatov.task.classfinder.usecase.SearchStrategy

data class Config(
    val searchStrategies: List<SearchStrategy>,
    val itemHandlers: List<ItemHandler>
) {
    fun strategyFor(input: Input): SearchStrategy = searchStrategies.firstOrNull { it.applicableFor(input) }
        ?: throw SearchStrategyNotFoundError(input)
}

class ConfigDSL {
    private var searchStrategies: List<SearchStrategy> = listOf()
    private var itemHandlers: List<ItemHandler> = listOf()

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