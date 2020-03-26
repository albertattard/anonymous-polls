package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.LongIdTable

object PossibleAnswersTable : LongIdTable("possible_answers") {
    val pollId = uuid("poll_id").references(PollsTable.id)
    val questionId = long("question_id").references(QuestionsTable.id)
    val index = integer("index")
    val possibleAnswer = varchar("possible_answer", 255)
}
