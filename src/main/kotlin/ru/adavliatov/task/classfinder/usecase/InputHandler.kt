package ru.adavliatov.task.classfinder.usecase

import ru.adavliatov.task.classfinder.domain.Item
import ru.adavliatov.task.classfinder.usecase.handler.Handler

interface InputHandler : Handler<Item>

class NonBlankToNullInputHandler : InputHandler {
    override fun invoke(input: Item?): Item? = if (input.isNullOrBlank()) null else input
}

class TrimLeftHandler : InputHandler {
    override fun invoke(input: Item?): Item? = input?.trimStart()
}

class MiddleWhitespacesToNullInputHandler : InputHandler {
    override fun invoke(input: Item?): Item? = input
        ?.trim()
        ?.let {
            if (it.split(" ").size <= 1)
                input
            else
                null
        }
}