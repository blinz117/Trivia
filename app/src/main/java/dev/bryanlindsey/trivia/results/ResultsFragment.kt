package dev.bryanlindsey.trivia.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dev.bryanlindsey.trivia.DynamicValues
import dev.bryanlindsey.trivia.R
import kotlinx.android.synthetic.main.results_screen.*

class ResultsFragment : Fragment(R.layout.results_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playAgainButton.apply {
            text = DynamicValues.playAgainText
            setOnClickListener(Navigation.createNavigateOnClickListener(R.id.play_again))
        }
        quitButton.apply {
            text = DynamicValues.quitText
            setOnClickListener(Navigation.createNavigateOnClickListener(R.id.quit))
        }
    }
}