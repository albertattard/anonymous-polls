package com.albertattard.polls.resource

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import com.albertattard.polls.model.Poll
import com.albertattard.polls.service.PollService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import java.util.UUID

@Controller("/poll")
class PollController internal constructor(
    private var service: PollService
) {
    @Get("/{pollId}", produces = [MediaType.APPLICATION_JSON])
    fun read(pollId: UUID): HttpResponse<Poll.Found> =
        when (val poll = service.read(pollId)) {
            is Poll.Found -> HttpResponse.ok(poll)
            else -> HttpResponse.notFound()
        }

    @Post("/create", produces = [MediaType.APPLICATION_JSON])
    fun create(@Body poll: CreatePoll): CreatedPoll =
        CreatedPoll(service.create(poll))
}
