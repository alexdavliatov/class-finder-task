package ru.adavliatov.task.classfinder.config

import ru.adavliatov.task.classfinder.usecase.InputHandler
import ru.adavliatov.task.classfinder.usecase.ItemHandler
import ru.adavliatov.task.classfinder.usecase.SearchStrategy

data class Config(
    val searchStrategies: List<SearchStrategy<*>>,
    val inputHandlers: List<InputHandler>,
    val itemHandlers: List<ItemHandler>
)

class ConfigDSL {
    private var searchStrategies: List<SearchStrategy<*>> = listOf()
    private var inputHandlers: List<InputHandler> = listOf()
    private var itemHandlers: List<ItemHandler> = listOf()

    fun searchStrategies(body: SearchStrategies.() -> Unit) {
        searchStrategies = SearchStrategies().apply(body)
    }

    fun inputHandlers(body: InputHandlers.() -> Unit) {
        inputHandlers = InputHandlers().apply(body)
    }

    fun itemHandlers(body: ItemHandlers.() -> Unit) {
        itemHandlers = ItemHandlers().apply(body)
    }

    fun build() = Config(searchStrategies, inputHandlers, itemHandlers)

    companion object {
        fun config(default: ConfigDSL = ConfigDSL(), body: ConfigDSL.() -> Unit) = default.apply(body).build()
    }
}

class SearchStrategies : ArrayList<SearchStrategy<*>>()
class ItemHandlers : ArrayList<ItemHandler>()
class InputHandlers : ArrayList<InputHandler>()