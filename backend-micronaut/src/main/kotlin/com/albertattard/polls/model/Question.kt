package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Question(val value: String)
