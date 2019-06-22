package dev.bryanlindsey.trivia.remote

import dev.bryanlindsey.trivia.remote.service.BASE_URL
import dev.bryanlindsey.trivia.remote.service.TriviaService
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL_DI_INSTANCE_NAME = "baseUrl"

val networkModule = module {

    single<String>(named(BASE_URL_DI_INSTANCE_NAME)) { BASE_URL }

    single{ OkHttpClient() }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(TriviaService::class.java) }
}