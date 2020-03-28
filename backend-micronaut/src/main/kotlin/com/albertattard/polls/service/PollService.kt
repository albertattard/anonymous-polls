package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPollIds
import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PollCount
import com.albertattard.polls.model.PollDelete
import java.util.UUID

interface PollService {

    fun create(poll: CreatePoll): CreatedPollIds

    fun read(readId: UUID): Poll

    fun delete(deleteId: UUID): PollDelete

    fun count() : PollCount
}
