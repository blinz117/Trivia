package dev.bryanlindsey.trivia.di

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}
