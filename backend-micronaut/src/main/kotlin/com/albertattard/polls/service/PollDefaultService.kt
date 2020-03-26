package com.albertattard.polls.service

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question
import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.PossibleAnswersTable
import com.albertattard.polls.repository.QuestionsTable
import java.util.UUID
import javax.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class PollDefaultService internal constructor(
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
                        it[PossibleAnswersTable.pollId] = pollId
                        it[PossibleAnswersTable.questionId] = questionId
                        it[PossibleAnswersTable.index] = answerIndex
                        it[PossibleAnswersTable.possibleAnswer] = possibleAnswer.text
                    }
                }
            }

            pollId
        }

    override fun read(pollId: UUID): Poll =
        transaction(database) {
            PollsTable.select { PollsTable.id eq pollId }
                .singleOrNull()
                ?.let { poll ->
                    /* Fetch all possible answers and then group them by question id */
                    val answersByQuestion = PossibleAnswersTable
                        .select { PossibleAnswersTable.pollId eq pollId }
                        .orderBy(PossibleAnswersTable.index)
                        .map {
                            it[PossibleAnswersTable.questionId] to PossibleAnswer(text = it[PossibleAnswersTable.possibleAnswer])
                        }.groupBy({ it.first }, { it.second })

                    val question = QuestionsTable
                        .select { QuestionsTable.pollId eq pollId }
                        .orderBy(QuestionsTable.index)
                        .map {
                            Question(
                                text = it[QuestionsTable.question],
                                /* Retrieve the possible answers with this question id */
                                possibleAnswers = answersByQuestion[it[QuestionsTable.id].value] ?: emptyList()
                            )
                        }

                    Poll.Found(
                        caption = poll[PollsTable.caption],
                        questions = question
                    )
                } ?: Poll.NotFound
        }
}
