package dev.bryanlindsey.trivia.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.createNavigateOnClickListener
import dev.bryanlindsey.trivia.DynamicValues
import dev.bryanlindsey.trivia.R
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeFragment : Fragment(R.layout.welcome_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        welcomeText.text = DynamicValues.welcomeText
        startButton.setOnClickListener(createNavigateOnClickListener(R.id.start_trivia))
    }
}