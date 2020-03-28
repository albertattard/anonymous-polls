package com.albertattard.polls.resource

import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PollCount
import com.albertattard.polls.service.PollService
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension.getMock
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

@MicronautTest
class CountPollControllerTest(
    private val service: PollService,
    @Client("/poll/count") private val client: RxHttpClient
) : StringSpec({
    "should return the number of polls" {
        val mock = getMock(service)

        val count = PollCount(42)
        every { mock.count() } returns count

        val response = client.toBlocking().retrieve(HttpRequest.GET<Any>("/"), PollCount::class.java)
        response shouldBe count

        verify(exactly = 1) { mock.count() }

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
