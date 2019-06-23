package dev.bryanlindsey.trivia.remote.service

import dev.bryanlindsey.trivia.remote.response.TriviaApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://opentdb.com/"

interface TriviaService {

    @GET("api.php")
    suspend fun getTriviaQuestions(@Query("amount") numberOfQuestions: Int): TriviaApiResponse
}
