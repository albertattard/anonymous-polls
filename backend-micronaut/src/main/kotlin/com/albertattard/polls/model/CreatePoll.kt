package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class CreatePoll(val caption: String, val questions: List<Question>)
