package ru.adavliatov.task.classfinder.domain

class SearchStrategyNotFoundError(input: Input) :
    IllegalArgumentException("Search strategy not found for [$input] input")