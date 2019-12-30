package ru.adavliatov.task.classfinder.domain

import ru.adavliatov.task.classfinder.usecase.isUpperCase

typealias Input = String

object InputExtensions {
    fun Input.nextItem(): Pair<Item, Input> {
        val word = StringBuilder()
        for (sym in asSequence()) {
            if (sym.isUpperCase()) {
                if (word.isNotEmpty()) {
                    val newInput = if (word.length >= this.length) "" else substring(word.length)
                    return word.toString() to newInput
                }
                word.append(sym)
            } else {
                word.append(sym)
            }
        }

        return this to ""
    }
}