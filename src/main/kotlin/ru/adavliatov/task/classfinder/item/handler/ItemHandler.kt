package ru.adavliatov.task.classfinder.item.handler

import ru.adavliatov.task.classfinder.item.Item

typealias ItemHandler = (Item?) -> Item?

class NonBlankToNullHandler : ItemHandler {
    override fun invoke(item: Item?): Item? = if (item.isNullOrBlank()) null else item
}

class PackageRemoverHandler : ItemHandler {
    override fun invoke(item: Item?): Item? = item?.substringAfterLast(".")
}