package com.albertattard.polls.model

sealed class Poll {

    object NotFound : Poll()
}
