package dev.bryanlindsey.trivia.settings

import android.os.Bundle
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

        screen.addPreference(themePreference)
        preferenceScreen = screen
    }
}
