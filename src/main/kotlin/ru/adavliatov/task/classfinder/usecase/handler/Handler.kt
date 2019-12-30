package ru.adavliatov.task.classfinder.usecase.handler

import ru.adavliatov.task.classfinder.ext.compose

typealias Handler<Model> = (Model?) -> Model?

object HandlerExtensions {
    operator fun <Model> Iterable<Handler<Model?>>.invoke(): Handler<Model?> =
        reduce { acc, handler -> acc.compose(handler) }
}