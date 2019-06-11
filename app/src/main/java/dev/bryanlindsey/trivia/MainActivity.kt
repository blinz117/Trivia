package dev.bryanlindsey.trivia

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val triviaQuestionDisplayViewModel: TriviaQuestionDisplayViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        triviaQuestionDisplayViewModel.triviaQuestionsLiveData.observe(
            this,
            Observer {
                for (question in it.results) {
                    val triviaView = layoutInflater.inflate(R.layout.trivia_question, questionItemContainer, false)

                    val questionText = triviaView.findViewById<TextView>(R.id.questionText)
                    questionText.text = question.question

                    val answerGroup = triviaView.findViewById<RadioGroup>(R.id.answersGroup)
                    answerGroup.addView(
                        RadioButton(this).apply {
                            text = question.correct_answer
                        }
                    )

                    for (incorrectAnswer in question.incorrect_answers) {
                        answerGroup.addView(
                            RadioButton(this).apply {
                                text = incorrectAnswer
                            }
                        )
                    }

                    questionItemContainer.addView(triviaView)
                }
            }
        )

        triviaQuestionDisplayViewModel.getMoreTriviaQuestions()
    }
}
