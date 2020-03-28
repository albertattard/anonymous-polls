package com.albertattard.polls.resource

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PollCount
import com.albertattard.polls.model.PollDelete
import com.albertattard.polls.service.PollService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import java.util.UUID

@Controller("/poll")
class PollController internal constructor(
    private var service: PollService
) {
    @Get("/count", produces = [MediaType.APPLICATION_JSON])
    fun count(): HttpResponse<PollCount> =
        HttpResponse.ok(service.count())

    @Get("/{pollId}", produces = [MediaType.APPLICATION_JSON])
    fun read(pollId: UUID): HttpResponse<Poll.Found> =
        when (val read = service.read(pollId)) {
            is Poll.Found -> HttpResponse.ok(read)
            else -> HttpResponse.notFound()
        }

    @Delete("/{adminId}", produces = [MediaType.APPLICATION_JSON])
    fun delete(adminId: UUID): HttpResponse<PollDelete> =
        when (service.delete(adminId)) {
            is PollDelete.Deleted -> HttpResponse.noContent()
            else -> HttpResponse.notFound()
        }

    @Post("/create", produces = [MediaType.APPLICATION_JSON])
    fun create(@Body poll: CreatePoll): CreatedPoll =
        service.create(poll)
            .let {
                CreatedPoll(
                    readLink = "/poll/${it.readId}",
                    editLink = "/poll/${it.editId}/edit",
                    deleteLink = "/poll/${it.deleteId}"
                )
            }
}
