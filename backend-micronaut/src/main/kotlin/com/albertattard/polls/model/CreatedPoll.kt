package com.albertattard.polls.model

import io.micronaut.core.annotation.Introspected
import java.util.UUID

@Introspected
data class CreatedPoll(val link: String) {

    companion object {
        operator fun invoke(id: UUID) =
            CreatedPoll("/poll/$id")
    }
}
