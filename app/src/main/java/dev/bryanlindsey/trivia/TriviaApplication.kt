package dev.bryanlindsey.trivia

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dev.bryanlindsey.trivia.di.firebaseModule
import dev.bryanlindsey.trivia.di.viewModelModule
import dev.bryanlindsey.trivia.remote.networkModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TriviaApplication : Application() {

    private val firebaseRemoteConfig: FirebaseRemoteConfig by inject()

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        startKoin {
            androidContext(this@TriviaApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    firebaseModule
                )
            )
        }

        setUpFirebase()
    }

    private fun setUpFirebase() {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        firebaseRemoteConfig.fetchAndActivate()
    }
}