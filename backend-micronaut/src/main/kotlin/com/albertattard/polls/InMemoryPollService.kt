package com.albertattard.polls

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import javax.inject.Singleton

@Singleton
class InMemoryPollService : PollService {

    override fun create(poll: CreatePoll) =
        CreatedPoll.random()
}
