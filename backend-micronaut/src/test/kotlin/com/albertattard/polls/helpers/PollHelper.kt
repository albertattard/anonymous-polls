package com.albertattard.polls.helpers

import com.albertattard.polls.model.Poll
import com.albertattard.polls.model.PossibleAnswer
import com.albertattard.polls.model.Question
import java.util.UUID

object PollHelper {

    private fun randomInt(variance: Int = 6) =
        (3..3 + variance).shuffled().first()

    fun random(): Poll.Found =
        Poll.Found(
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
