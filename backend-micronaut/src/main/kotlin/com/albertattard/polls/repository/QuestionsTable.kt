package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.LongIdTable

object QuestionsTable : LongIdTable("questions") {
    val pollId = long("poll_id").references(PollsTable.id).index()
    val readId = uuid("read_id").index()
    val editId = uuid("edit_id").index()
    val deleteId = uuid("delete_id").index()
    val index = integer("index")
    val question = varchar("question", 255)
}
