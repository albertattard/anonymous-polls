package com.albertattard.polls.helpers

import com.albertattard.polls.repository.PollsTable
import com.albertattard.polls.repository.QuestionsTable
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
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

    fun withPoll(database: Database, pollId: UUID, block: (Map<String, Any>) -> Unit) {
        block.invoke(readPoll(database, pollId))
    }

    fun withQuestion(database: Database, pollId: UUID, index: Int, block: (Map<String, Any>) -> Unit) {
        block.invoke(readQuestion(database, pollId, index))
    }
}
