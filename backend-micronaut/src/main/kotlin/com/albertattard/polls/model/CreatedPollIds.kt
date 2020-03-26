package com.albertattard.polls.model

import java.util.UUID

data class CreatedPollIds(
    val readId: UUID,
    val editId: UUID,
    val deleteId: UUID
)
