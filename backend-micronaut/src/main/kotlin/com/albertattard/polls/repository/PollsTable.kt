package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.LongIdTable

object PollsTable : LongIdTable("polls") {
    val readId = uuid("read_id").index()
    val editId = uuid("edit_id").index()
    val deleteId = uuid("delete_id").index()
    val caption = varchar("caption", 255)
}
