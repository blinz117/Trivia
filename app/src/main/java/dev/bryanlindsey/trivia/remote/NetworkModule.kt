package dev.bryanlindsey.trivia.remote

import dev.bryanlindsey.trivia.remote.service.BASE_URL
import dev.bryanlindsey.trivia.remote.service.TriviaService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

val networkModule = module {

    single { retrofit.create(TriviaService::class.java) }
}