package dev.bryanlindsey.trivia.triviaquestiondisplay

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import dev.bryanlindsey.trivia.R
import dev.bryanlindsey.trivia.remote.response.TriviaApiResponse
import kotlinx.android.synthetic.main.trivia_questions_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class TriviaQuestionDisplayFragment: Fragment(R.layout.trivia_questions_fragment) {

    private val triviaQuestionDisplayViewModel: TriviaQuestionDisplayViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        triviaQuestionDisplayViewModel.triviaQuestionsLiveData.observe(
            this,
            Observer {
                render(it)
            }
        )

        triviaQuestionDisplayViewModel.getMoreTriviaQuestions()
    }

    private fun render(data: TriviaApiResponse) {
        for (question in data.results) {
            val triviaView = layoutInflater.inflate(R.layout.trivia_question, questionItemContainer, false)

            val questionText = triviaView.findViewById<TextView>(R.id.questionText)
            questionText.text = Html.fromHtml(question.question)

            val answerGroup = triviaView.findViewById<RadioGroup>(R.id.answersGroup)
            answerGroup.addView(
                RadioButton(context).apply {
                    text = Html.fromHtml(question.correct_answer)
                }
            )

            for (incorrectAnswer in question.incorrect_answers) {
                answerGroup.addView(
                    RadioButton(context).apply {
                        text = Html.fromHtml(incorrectAnswer)
                    }
                )
            }

            questionItemContainer.addView(triviaView)
        }

        questionItemContainer.addView(
            Button(context).apply {
                text = "Submit"
                setOnClickListener(Navigation.createNavigateOnClickListener(R.id.submit_answers))
            }
        )
    }
}