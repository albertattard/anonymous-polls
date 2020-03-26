package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.Poll
import java.util.UUID

interface PollService {
    fun create(poll: CreatePoll): UUID

    fun read(pollId: UUID): Poll
}
