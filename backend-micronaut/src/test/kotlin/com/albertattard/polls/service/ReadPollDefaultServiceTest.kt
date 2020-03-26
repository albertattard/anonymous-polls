package com.albertattard.polls.service

import com.albertattard.polls.helpers.DatabaseHelper
import com.albertattard.polls.model.Poll
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
})
