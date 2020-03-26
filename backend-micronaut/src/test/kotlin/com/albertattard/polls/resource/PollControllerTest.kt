package com.albertattard.polls.resource

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question
import com.albertattard.polls.service.PollService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension.getMock
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

@MicronautTest
class PollControllerTest(
    private val service: PollService,
    @Client("/poll") private val client: RxHttpClient
) : StringSpec({
    "should create the poll and return the share link generated by the greeting service" {
        val mock = getMock(service)

        val create = CreatePoll(
            caption = "Test Poll",
            questions = listOf(
                Question(possibleAnswers = listOf(PossibleAnswer("Sample question 1")))
            )
        )
        val created = CreatedPoll.random()
        every { mock.create(create) } returns created

        val response = client.toBlocking().exchange(HttpRequest.POST("/create", create), CreatedPoll::class.java)
        response.body() shouldBe created

        verify(exactly = 1) { mock.create(create) }

        /* TODO: check why this needs to be verified */
        verify(exactly = 2) { mock.hashCode() }
        verify(exactly = 1) { mock.toString() }
        confirmVerified(mock)
    }
}) {
    @MockBean(PollService::class)
    fun pollService(): PollService {
        return mockk()
    }
}
