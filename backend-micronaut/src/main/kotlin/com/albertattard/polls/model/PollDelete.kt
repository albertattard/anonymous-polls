package com.albertattard.polls.model

sealed class PollDelete {

    object NotFound : PollDelete()

    object Deleted : PollDelete()
}
