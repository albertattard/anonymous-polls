package com.albertattard.polls.resource

import com.albertattard.polls.model.PollDelete
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
class DeletePollControllerTest(
    private val service: PollService,
    @Client("/poll") private val client: RxHttpClient
) : StringSpec({
    "should return 404 when deleting a poll that does not exists" {
        val adminId = UUID.randomUUID()
        val mock = getMock(service)

        every { mock.delete(adminId) } returns PollDelete.NotFound

        val exception = shouldThrow<HttpClientResponseException> {
            client.toBlocking().exchange(HttpRequest.DELETE<Any>("/$adminId"), String::class.java)
        }
        exception.status.code shouldBe 404

        verify(exactly = 1) { mock.delete(adminId) }

        /* TODO: check why this needs to be verified */
        verify(exactly = 2) { mock.hashCode() }
        verify(exactly = 1) { mock.toString() }
        confirmVerified(mock)
    }

    "should return the poll when the requested poll is found" {
        val adminId = UUID.randomUUID()
        val mock = getMock(service)

        every { mock.delete(adminId) } returns PollDelete.Deleted

        val response = client.toBlocking().exchange(HttpRequest.DELETE<Any>("/$adminId"), String::class.java)
        response.status.code shouldBe 204

        verify(exactly = 1) { mock.delete(adminId) }

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
