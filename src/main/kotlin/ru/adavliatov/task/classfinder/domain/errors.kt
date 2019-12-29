package ru.adavliatov.task.classfinder.domain

class InvalidInputError(input: Input) :
    IllegalArgumentException("[$input] is not valid input")

class SearchStrategyNotFoundError(input: Input) :
    IllegalArgumentException("Search strategy not found for [$input] input")