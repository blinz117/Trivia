package dev.bryanlindsey.trivia

import android.app.Application
import dev.bryanlindsey.trivia.remote.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TriviaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TriviaApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}