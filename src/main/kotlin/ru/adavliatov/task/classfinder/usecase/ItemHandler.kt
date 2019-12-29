package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Item
import ru.adavliatov.task.classfinder.usecase.handler.Handler

interface ItemHandler : Handler<Item>

class NonBlankToNullItemHandler : ItemHandler {
    override fun invoke(item: Item?): Item? = if (item.isNullOrBlank()) null else item
}

class PackageRemoverHandler : ItemHandler {
    override fun invoke(item: Item?): Item? = item?.substringAfterLast(".")
}