package dev.bryanlindsey.trivia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel : TriviaQuestionDisplayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.triviaQuestionsLiveData.observe(
            this,
            Observer {
                triviaQuestionDisplayText.text = it
            }
        )

        viewModel.getMoreTriviaQuestions()
    }
}
