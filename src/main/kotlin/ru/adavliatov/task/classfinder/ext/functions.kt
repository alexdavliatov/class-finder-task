package ru.adavliatov.task.classfinder.ext

infix fun <V, T, R> Function1<T, R>.compose(before: (V) -> T): (V) -> R = { v: V -> this(before(v)) }
