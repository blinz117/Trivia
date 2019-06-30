package dev.bryanlindsey.trivia.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val themePreference = SwitchPreferenceCompat(context).apply {
            key = "theme"
            title = "Dark mode"
            summary = "Enable dark mode"
        }

        themePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val nightModeFlag = if (newValue == true) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(nightModeFlag)
                true
            }

        screen.addPreference(themePreference)
        preferenceScreen = screen
    }
}
