package com.albertattard.polls.repository

import org.jetbrains.exposed.dao.UUIDTable

object PollsTable : UUIDTable("polls") {
    val caption = varchar("caption", 255)
}
