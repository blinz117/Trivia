package dev.bryanlindsey.trivia

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dev.bryanlindsey.trivia.di.firebaseModule
import dev.bryanlindsey.trivia.di.viewModelModule
import dev.bryanlindsey.trivia.remote.networkModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

const val DEFAULT_DARK_MODE_SETTING = true

class TriviaApplication : Application() {

    private val firebaseRemoteConfig: FirebaseRemoteConfig by inject()

    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkModeEnabled = preferences.getBoolean("theme", DEFAULT_DARK_MODE_SETTING)
        val nightModeFlag = if (isDarkModeEnabled) MODE_NIGHT_YES else MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(nightModeFlag)

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
