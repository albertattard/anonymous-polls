package com.albertattard.polls.service

import com.albertattard.polls.helpers.DatabaseHelper
import com.albertattard.polls.model.Poll
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import java.util.UUID
import org.jetbrains.exposed.sql.Database

@MicronautTest
class ReadPollDefaultServiceTest(
    private val database: Database
) : StringSpec({
    "should read the poll not found when no poll with the given poll id is found" {
        DatabaseHelper.emptyDatabase(database)
        val service = PollDefaultService(database)

        val result = service.read(UUID.randomUUID())
        result shouldBe Poll.NotFound
    }

    "should read the poll when the poll with the given poll id is found" {
        val poll = DatabaseHelper.createRandomPoll(database)
        val service = PollDefaultService(database)

        val result = service.read(poll.first)
        result.shouldBeInstanceOf<Poll.Found>()

        with(poll.second) {
            result as Poll.Found
            result.caption shouldBe caption
            result.questions shouldBe questions
        }
    }
})
