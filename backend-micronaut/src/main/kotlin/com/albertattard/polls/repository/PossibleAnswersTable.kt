package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.LongIdTable

object PossibleAnswersTable : LongIdTable("possible_answers") {
    val pollId = long("poll_id").references(PollsTable.id).index()
    val readId = uuid("read_id").index()
    val editId = uuid("edit_id").index()
    val deleteId = uuid("delete_id").index()
    val questionId = long("question_id").references(QuestionsTable.id)
    val index = integer("index")
    val possibleAnswer = varchar("possible_answer", 255)
}
