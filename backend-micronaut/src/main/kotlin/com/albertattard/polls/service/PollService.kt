package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import java.util.UUID

interface PollService {
    fun create(poll: CreatePoll): UUID
}
