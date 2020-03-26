package com.albertattard.polls.helpers

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question

object CreatePollHelper {

    fun sample() =
        CreatePoll(
            caption = "Sample Poll",
            questions = listOf(
                Question(
                    question = "Sample Question 1", possibleAnswers = listOf(
                        PossibleAnswer("Sample answer 1"),
                        PossibleAnswer("Sample answer 2")
                    )
                )
            )
        )
}
