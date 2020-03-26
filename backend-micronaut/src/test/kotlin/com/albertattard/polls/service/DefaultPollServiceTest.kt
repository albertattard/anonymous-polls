package com.albertattard.polls.service

import com.albertattard.polls.helpers.CreatePollHelper
import com.albertattard.polls.helpers.DatabaseHelper
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import org.jetbrains.exposed.sql.Database

@MicronautTest
class DefaultPollServiceTest(
    private val database: Database
) : StringSpec({
    "should create the poll and return the share link generated by the greeting service" {
        val create = CreatePollHelper.sample()
        val service = DefaultPollService(database)
        val pollId = service.create(create)

        /* Verify that the poll is in the database */
        DatabaseHelper.withPoll(database, pollId) {
            it["caption"] shouldBe create.caption
        }

        create.questions.forEachIndexed { index, question ->
            DatabaseHelper.withQuestion(database, pollId, index) {
                it["question"] shouldBe question.question
            }
        }
    }
})