package com.albertattard.polls.helpers

import com.albertattard.polls.model.CreatePoll
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question
import java.util.UUID

object CreatePollHelper {

    fun sample() =
        CreatePoll(
            caption = "Sample Poll",
            questions = listOf(
                Question(
                    text = "Sample Question 1", possibleAnswers = listOf(
                        PossibleAnswer("Sample answer 1-1"),
                        PossibleAnswer("Sample answer 1-2"),
                        PossibleAnswer("Sample answer 1-3")
                    )
                ),
                Question(
                    text = "Sample Question 2", possibleAnswers = listOf(
                        PossibleAnswer("Sample answer 2-1"),
                        PossibleAnswer("Sample answer 2-2")
                    )
                )
            )
        )

    private fun randomInt(variance: Int = 6) =
        (3..3 + variance).shuffled().first()

    fun random(): CreatePoll =
        CreatePoll(
            caption = "Random Poll - ${UUID.randomUUID()}",
            questions = (1..randomInt()).map { question ->
                Question(
                    text = "Random Question $question - ${UUID.randomUUID()}",
                    possibleAnswers = (1..randomInt()).map { answer ->
                        PossibleAnswer("Random Answer $answer - ${UUID.randomUUID()}")
                    }.toList()
                )
            }.toList()
        )
}
