package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Question(val question: String, val possibleAnswers: List<PossibleAnswer>)
