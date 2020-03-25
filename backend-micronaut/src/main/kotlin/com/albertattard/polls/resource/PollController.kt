package com.albertattard.polls.resource

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.CreatedPoll
import com.albertattard.polls.service.PollService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/poll")
class PollController internal constructor(
    private var service: PollService
) {

    @Post("/create", produces = [MediaType.APPLICATION_JSON])
    fun create(@Body poll: CreatePoll): CreatedPoll {
        return service.create(poll)
    }
}
