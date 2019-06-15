package dev.bryanlindsey.trivia.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.createNavigateOnClickListener
import dev.bryanlindsey.trivia.R
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.welcome_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startButton.setOnClickListener(createNavigateOnClickListener(R.id.start_trivia))
    }
}