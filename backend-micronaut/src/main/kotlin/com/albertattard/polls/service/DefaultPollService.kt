package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import com.albertattard.polls.repository.PollsTable
import javax.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class DefaultPollService internal constructor(
    private var database: Database
) : PollService {

    override fun create(poll: CreatePoll) =
        transaction(database) {
            val id = PollsTable.insertAndGetId {
            }
            CreatedPoll(id = id.value)
        }
}
