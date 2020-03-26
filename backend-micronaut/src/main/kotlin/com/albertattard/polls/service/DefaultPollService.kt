package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.Poll
import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.PossibleAnswersTable
import com.albertattard.polls.repository.QuestionsTable
import java.util.UUID
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

            poll.questions.forEachIndexed { questionIndex, question ->
                val questionId = QuestionsTable.insertAndGetId {
                    it[QuestionsTable.pollId] = pollId
                    it[QuestionsTable.index] = questionIndex
                    it[QuestionsTable.question] = question.text
                }.value

                question.possibleAnswers.forEachIndexed { answerIndex, possibleAnswer ->
                    PossibleAnswersTable.insert {
                        it[PossibleAnswersTable.questionId] = questionId
                        it[PossibleAnswersTable.index] = answerIndex
                        it[PossibleAnswersTable.possibleAnswer] = possibleAnswer.text
                    }
                }
            }

            pollId
        }

    override fun read(pollId: UUID): Poll {
        // TODO("Remember to write a test first!!")
        return Poll.NotFound
    }
}
