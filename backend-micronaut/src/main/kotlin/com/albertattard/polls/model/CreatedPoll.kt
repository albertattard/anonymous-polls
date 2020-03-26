package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class CreatedPoll(
    val readLink: String,
    val editLink: String,
    val deleteLink: String
)
