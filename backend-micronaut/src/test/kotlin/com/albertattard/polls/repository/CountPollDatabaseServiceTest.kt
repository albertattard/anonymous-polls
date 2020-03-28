package com.albertattard.polls.repository

import com.albertattard.polls.helpers.DatabaseHelper
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import org.jetbrains.exposed.sql.Database

@MicronautTest
class CountPollDatabaseServiceTest(
    private val database: Database
) : StringSpec({
    "should return 0 when no polls are in the database" {
        DatabaseHelper.emptyDatabase(database)

        val service = PollDatabaseService(database)
        val result = service.count()
        result.total shouldBe 0
    }

    "should return the number of polls present in the database" {
        val size = 42
        DatabaseHelper.emptyDatabase(database)
        repeat(size) {
            DatabaseHelper.createRandomPoll(database)
        }

        val service = PollDatabaseService(database)
        val result = service.count()
        result.total shouldBe size
    }
})
