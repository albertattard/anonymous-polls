package com.albertattard.polls.resource

import com.albertattard.polls.model.Poll
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
import java.util.UUID

@MicronautTest
class ReadPollControllerTest(
    private val service: PollService,
    @Client("/poll") private val client: RxHttpClient
) : StringSpec({
    "!should return 404 when the requested poll is not found" {
        val pollId = UUID.randomUUID()
        val mock = getMock(service)

        every { mock.read(pollId) } returns Poll.NotFound

        val response = client.toBlocking().exchange(HttpRequest.GET<Any>("/$pollId"), String::class.java)
        response.code() shouldBe 404

        verify(exactly = 1) { mock.read(pollId) }

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
