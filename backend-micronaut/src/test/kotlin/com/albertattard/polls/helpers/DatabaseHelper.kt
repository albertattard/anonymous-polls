package com.albertattard.polls.helpers

import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.PossibleAnswersTable
import com.albertattard.polls.repository.QuestionsTable
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseHelper {

    private fun readPoll(database: Database, pollId: UUID): Map<String, Any> =
        transaction(database) {
            PollsTable.select { PollsTable.id eq pollId }
                .singleOrNull()
                ?.let {
                    mapOf(
                        "id" to it[PollsTable.id].value,
                        "caption" to it[PollsTable.caption]
                    )
                } ?: emptyMap()
        }

    private fun readQuestion(database: Database, pollId: UUID, index: Int): Map<String, Any> =
        transaction(database) {
            QuestionsTable.select { QuestionsTable.pollId eq pollId }
                .andWhere { QuestionsTable.index eq index }
                .singleOrNull()
                ?.let {
                    mapOf(
                        "id" to it[QuestionsTable.id].value,
                        "pollId" to it[QuestionsTable.pollId],
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
                        "questionId" to it[PossibleAnswersTable.questionId],
                        "index" to it[PossibleAnswersTable.index],
                        "possibleAnswer" to it[PossibleAnswersTable.possibleAnswer]
                    )
                } ?: emptyMap()
        }

    fun withPoll(database: Database, pollId: UUID, block: (Map<String, Any>) -> Unit) =
        readPoll(database, pollId).let {
            block(it)
            it["id"] as UUID
        }

    fun withQuestion(database: Database, pollId: UUID, index: Int, block: (Map<String, Any>) -> Unit) =
        readQuestion(database, pollId, index).let {
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
}
