package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Question(
    val text: String,
    val possibleAnswers: List<PossibleAnswer> = emptyList()
)
