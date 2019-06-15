package dev.bryanlindsey.trivia

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import dev.bryanlindsey.trivia.di.viewModelModule
import dev.bryanlindsey.trivia.remote.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TriviaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

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