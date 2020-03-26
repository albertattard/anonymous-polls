package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.QuestionsTable
import javax.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class DefaultPollService internal constructor(
    private var database: Database
) : PollService {

    override fun create(poll: CreatePoll) =
        transaction(database) {
            val pollId = PollsTable.insertAndGetId {
                it[caption] = poll.caption
            }.value

            poll.questions.forEachIndexed { index, question ->
                QuestionsTable.insert {
                    it[QuestionsTable.pollId] = pollId
                    it[QuestionsTable.index] = index
                    it[QuestionsTable.question] = question.question
                }
            }

            pollId
        }
}
