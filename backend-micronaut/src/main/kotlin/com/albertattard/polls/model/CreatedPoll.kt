package com.albertattard.polls.model

import java.util.UUID

data class CreatedPoll private constructor(val link: String) {

    companion object {
        operator fun invoke(id: UUID) =
                CreatedPoll("/poll/$id")

        fun random() =
                invoke(UUID.randomUUID())
    }
}
