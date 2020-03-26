package com.albertattard.polls.repository

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPollIds
import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PollDelete
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question
import com.albertattard.polls.service.PollService
import java.util.UUID
import javax.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class PollDatabaseService internal constructor(
    private var database: Database
) : PollService {

    override fun create(poll: CreatePoll) =
        transaction(database) {
            var readId = UUID.randomUUID()
            var editId = UUID.randomUUID()
            var deleteId = UUID.randomUUID()

            val pollId = PollsTable.insertAndGetId {
                it[PollsTable.readId] = readId
                it[PollsTable.editId] = editId
                it[PollsTable.deleteId] = deleteId
                it[PollsTable.caption] = poll.caption
            }.value

            poll.questions.forEachIndexed { questionIndex, question ->
                val questionId = QuestionsTable.insertAndGetId {
                    it[QuestionsTable.pollId] = pollId
                    it[QuestionsTable.readId] = readId
                    it[QuestionsTable.editId] = editId
                    it[QuestionsTable.deleteId] = deleteId
                    it[QuestionsTable.index] = questionIndex
                    it[QuestionsTable.question] = question.text
                }.value

                question.possibleAnswers.forEachIndexed { answerIndex, possibleAnswer ->
                    PossibleAnswersTable.insert {
                        it[PossibleAnswersTable.pollId] = pollId
                        it[PossibleAnswersTable.readId] = readId
                        it[PossibleAnswersTable.editId] = editId
                        it[PossibleAnswersTable.deleteId] = deleteId
                        it[PossibleAnswersTable.questionId] = questionId
                        it[PossibleAnswersTable.index] = answerIndex
                        it[PossibleAnswersTable.possibleAnswer] = possibleAnswer.text
                    }
                }
            }

            CreatedPollIds(readId = readId, editId = editId, deleteId = deleteId)
        }

    override fun read(readId: UUID): Poll =
        transaction(database) {
            PollsTable.select { PollsTable.readId eq readId }
                .singleOrNull()
                ?.let { poll ->
                    /* Fetch all possible answers and then group them by question id */
                    val answersByQuestion = PossibleAnswersTable
                        .select { PossibleAnswersTable.readId eq readId }
                        .orderBy(PossibleAnswersTable.index)
                        .map {
                            it[PossibleAnswersTable.questionId] to PossibleAnswer(text = it[PossibleAnswersTable.possibleAnswer])
                        }.groupBy({ it.first }, { it.second })

                    val question = QuestionsTable
                        .select { QuestionsTable.readId eq readId }
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

    override fun delete(deleteId: UUID): PollDelete =
        transaction(database) {
            PossibleAnswersTable.deleteWhere { PossibleAnswersTable.deleteId eq deleteId }
            QuestionsTable.deleteWhere { QuestionsTable.deleteId eq deleteId }
            val deletedPolls = PollsTable.deleteWhere { PollsTable.deleteId eq deleteId }

            if (deletedPolls != 1) {
                rollback()
                PollDelete.NotFound
            } else {
                PollDelete.Deleted
            }
        }
}
