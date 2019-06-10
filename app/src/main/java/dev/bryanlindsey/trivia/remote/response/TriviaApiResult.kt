package dev.bryanlindsey.trivia.remote.response

data class TriviaApiResult(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)