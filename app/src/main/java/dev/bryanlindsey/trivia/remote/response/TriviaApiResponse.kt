package dev.bryanlindsey.trivia.remote.response

data class TriviaApiResponse(
    val response_code: Int,
    val results: List<TriviaApiResult>
)
