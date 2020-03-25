package com.albertattard.polls

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll

interface PollService {
    fun create(poll: CreatePoll): CreatedPoll
}
