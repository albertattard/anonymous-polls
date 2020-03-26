package com.albertattard.polls.repository

import com.albertattard.polls.helpers.DatabaseHelper
import com.albertattard.polls.model.Poll
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import java.util.UUID
import org.jetbrains.exposed.sql.Database

@MicronautTest
class ReadPollDatabaseServiceTest(
    private val database: Database
) : StringSpec({
    "should return not found when no poll with the given readId is found" {
        DatabaseHelper.emptyDatabase(database)
        val service = PollDatabaseService(database)

        val result = service.read(UUID.randomUUID())
        result shouldBe Poll.NotFound
    }

    "should return the poll with the given readId" {
        val poll = DatabaseHelper.createRandomPoll(database)
        val service = PollDatabaseService(database)

        val result = service.read(poll.first.readId)
        result.shouldBeInstanceOf<Poll.Found>()

        with(poll.second) {
            result as Poll.Found
            result.caption shouldBe caption
            result.questions shouldBe questions
        }
    }
})
