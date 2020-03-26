package com.albertattard.polls.helpers

import com.albertattard.polls.model.CreatedPoll
import java.util.UUID

object CreatedPollHelper {

    fun random() =
        CreatedPoll.invoke(UUID.randomUUID())
}
