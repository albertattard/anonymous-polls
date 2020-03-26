package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.LongIdTable

object QuestionsTable : LongIdTable("questions") {
    val pollId = uuid("poll_id").references(PollsTable.id)
    val index = integer("index")
    val question = varchar("question", 255)
}
