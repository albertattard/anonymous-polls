package com.albertattard.polls.repository

import com.albertattard.polls.helpers.DatabaseHelper
import com.albertattard.polls.model.PollDelete
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import java.util.UUID
import org.jetbrains.exposed.sql.Database

@MicronautTest
class DeletePollDatabaseServiceTest(
    private val database: Database
) : StringSpec({
    "should return not found when no poll with the given deleteId is found" {
        DatabaseHelper.emptyDatabase(database)
        val service = PollDatabaseService(database)

        val result = service.delete(UUID.randomUUID())
        result shouldBe PollDelete.NotFound
    }

    "should delete the poll with the given deleteId" {
        val poll = DatabaseHelper.createRandomPoll(database)
        val service = PollDatabaseService(database)

        /* Should not delete the poll by providing the readId */
        service.delete(poll.first.readId) shouldBe PollDelete.NotFound
        DatabaseHelper.countPoll(database, poll.first.readId) shouldBe 1

        /* Should not delete the poll by providing the editId */
        service.delete(poll.first.editId) shouldBe PollDelete.NotFound
        DatabaseHelper.countPoll(database, poll.first.readId) shouldBe 1

        /* Should delete the poll by providing the deleteId */
        service.delete(poll.first.deleteId) shouldBe PollDelete.Deleted
        DatabaseHelper.countPoll(database, poll.first.readId) shouldBe 0
    }
})
