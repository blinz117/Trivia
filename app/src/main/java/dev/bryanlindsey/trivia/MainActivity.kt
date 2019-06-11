package dev.bryanlindsey.trivia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val triviaQuestionDisplayViewModel : TriviaQuestionDisplayViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        triviaQuestionDisplayViewModel.triviaQuestionsLiveData.observe(
            this,
            Observer {
                triviaQuestionDisplayText.text = it
            }
        )

        triviaQuestionDisplayViewModel.getMoreTriviaQuestions()
    }
}
