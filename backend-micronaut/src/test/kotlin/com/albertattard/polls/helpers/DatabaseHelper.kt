package com.albertattard.polls.helpers

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPollIds
import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.PossibleAnswersTable
import com.albertattard.polls.repository.QuestionsTable
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseHelper {

    private fun readPoll(database: Database, readId: UUID): Map<String, Any> =
        transaction(database) {
            PollsTable.select { PollsTable.readId eq readId }
                .singleOrNull()
                ?.let {
                    mapOf(
                        "id" to it[PollsTable.id].value,
                        "readId" to it[PollsTable.readId],
                        "editId" to it[PollsTable.editId],
                        "deleteId" to it[PollsTable.deleteId],
                        "caption" to it[PollsTable.caption]
                    )
                } ?: emptyMap()
        }

    private fun readQuestion(database: Database, readId: UUID, index: Int): Map<String, Any> =
        transaction(database) {
            QuestionsTable.select { QuestionsTable.readId eq readId }
                .andWhere { QuestionsTable.index eq index }
                .singleOrNull()
                ?.let {
                    mapOf(
                        "id" to it[QuestionsTable.id].value,
                        "pollId" to it[QuestionsTable.pollId],
                        "readId" to it[QuestionsTable.readId],
                        "editId" to it[QuestionsTable.editId],
                        "deleteId" to it[QuestionsTable.deleteId],
                        "index" to it[QuestionsTable.index],
                        "question" to it[QuestionsTable.question]
                    )
                } ?: emptyMap()
        }

    private fun readPossibleAnswer(database: Database, questionId: Long, index: Int): Map<String, Any> =
        transaction(database) {
            PossibleAnswersTable.select { PossibleAnswersTable.questionId eq questionId }
                .andWhere { PossibleAnswersTable.index eq index }
                .singleOrNull()
                ?.let {
                    mapOf(
                        "id" to it[PossibleAnswersTable.id].value,
                        "pollId" to it[PossibleAnswersTable.pollId],
                        "readId" to it[PossibleAnswersTable.readId],
                        "editId" to it[PossibleAnswersTable.editId],
                        "deleteId" to it[PossibleAnswersTable.deleteId],
                        "questionId" to it[PossibleAnswersTable.questionId],
                        "index" to it[PossibleAnswersTable.index],
                        "possibleAnswer" to it[PossibleAnswersTable.possibleAnswer]
                    )
                } ?: emptyMap()
        }

    fun withPoll(database: Database, readId: UUID, block: (Map<String, Any>) -> Unit) =
        readPoll(database, readId).let {
            block(it)
            it["id"] as Long
        }

    fun withQuestion(database: Database, readId: UUID, index: Int, block: (Map<String, Any>) -> Unit) =
        readQuestion(database, readId, index).let {
            block(it)
            it["id"] as Long
        }

    fun withPossibleAnswer(database: Database, questionId: Long, index: Int, block: (Map<String, Any>) -> Unit) =
        readPossibleAnswer(database, questionId, index).let {
            block(it)
            it["id"] as Long
        }

    fun emptyDatabase(database: Database) {
        transaction(database) {
            PossibleAnswersTable.deleteAll()
            QuestionsTable.deleteAll()
            PollsTable.deleteAll()
        }
    }

    private fun createPoll(database: Database, poll: CreatePoll) =
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

    fun createRandomPoll(database: Database): Pair<CreatedPollIds, CreatePoll> {
        val poll = CreatePollHelper.random()
        val pollId = createPoll(database, poll)
        return pollId to poll
    }

    fun countPoll(database: Database, readId: UUID): Int =
        transaction(database) {
            val count = PollsTable.id.count()
            PollsTable.slice(count)
                .select { PollsTable.readId eq readId }
                .single()
                .let { it[count] }
        }
}
