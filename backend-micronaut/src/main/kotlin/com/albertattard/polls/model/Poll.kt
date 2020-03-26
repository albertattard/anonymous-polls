package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected

sealed class Poll {

    object NotFound : Poll()

    @Introspected
    data class Found(
        val caption: String
    ) : Poll()
}
