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
import androidx.navigation.findNavController
import dev.bryanlindsey.trivia.R
import dev.bryanlindsey.trivia.remote.response.TriviaApiResponse
import kotlinx.android.synthetic.main.trivia_questions_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val CORRECT_ANSWER_TAG = "correct answer"

class TriviaQuestionDisplayFragment : Fragment(R.layout.trivia_questions_fragment) {

    private val triviaQuestionDisplayViewModel: TriviaQuestionDisplayViewModel by viewModel()

    private val answerSets = mutableListOf<RadioGroup>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        triviaQuestionDisplayViewModel.triviaQuestionsLiveData.observe(
            this,
            Observer {
                render(it)
            }
        )
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
                    tag = CORRECT_ANSWER_TAG
                }
            )

            for (incorrectAnswer in question.incorrect_answers) {
                answerGroup.addView(
                    RadioButton(context).apply {
                        text = Html.fromHtml(incorrectAnswer)
                    }
                )
            }

            answerSets.add(answerGroup)

            questionItemContainer.addView(triviaView)
        }

        questionItemContainer.addView(
            Button(context).apply {
                text = "Submit"

                setOnClickListener {
                    val score = calculateScore()
                    val possiblePoints = answerSets.size
                    val action = TriviaQuestionDisplayFragmentDirections.submitAnswers(score, possiblePoints)
                    findNavController().navigate(action)
                }

            }
        )
    }

    private fun calculateScore() =
        answerSets
            .map { isAnswerCorrect(it) }
            .count { it }

    private fun isAnswerCorrect(answerSet: RadioGroup): Boolean {
        val checkedAnswerId = answerSet.checkedRadioButtonId
        val checkedView: View? = answerSet.findViewById(checkedAnswerId)
        return checkedView?.tag == CORRECT_ANSWER_TAG
    }
}
