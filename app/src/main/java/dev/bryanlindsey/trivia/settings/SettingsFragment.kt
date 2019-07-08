package dev.bryanlindsey.trivia.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat
import dev.bryanlindsey.trivia.DEFAULT_DARK_MODE_SETTING

const val NUMBER_OF_QUESTIONS_PREFERENCE_KEY = "number_of_questions"

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val themePreference = SwitchPreferenceCompat(context).apply {
            key = "theme"
            title = "Dark mode"
            summary = "Enable dark mode"
            setDefaultValue(DEFAULT_DARK_MODE_SETTING)
        }

        themePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val nightModeFlag = if (newValue == true) MODE_NIGHT_YES else MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(nightModeFlag)
                true
            }

        screen.addPreference(themePreference)

        val numberOfQuestionsPreference = SeekBarPreference(context).apply {
            key = NUMBER_OF_QUESTIONS_PREFERENCE_KEY
            title = "Questions per round"
            min = 1
            max = 20
            showSeekBarValue = true
            setDefaultValue(10)
        }

        screen.addPreference(numberOfQuestionsPreference)

        preferenceScreen = screen
    }
}
