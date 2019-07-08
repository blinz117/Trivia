package dev.bryanlindsey.trivia.triviaquestiondisplay

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bryanlindsey.trivia.remote.response.TriviaApiResponse
import dev.bryanlindsey.trivia.remote.service.TriviaService
import dev.bryanlindsey.trivia.settings.NUMBER_OF_QUESTIONS_PREFERENCE_KEY
import kotlinx.coroutines.launch

private const val DEFAULT_QUESTION_COUNT = 10

class TriviaQuestionDisplayViewModel(
    private val triviaService: TriviaService,
    private val preferences: SharedPreferences) : ViewModel() {

    private val _triviaQuestionsLiveData = MutableLiveData<TriviaApiResponse>()

    var triviaQuestionsLiveData: LiveData<TriviaApiResponse> = _triviaQuestionsLiveData

    init {
        getMoreTriviaQuestions()
    }

    fun getMoreTriviaQuestions() =
        viewModelScope.launch {
            val numberOfQuestions = preferences.getInt(NUMBER_OF_QUESTIONS_PREFERENCE_KEY, DEFAULT_QUESTION_COUNT)

            val response = triviaService.getTriviaQuestions(numberOfQuestions)

            _triviaQuestionsLiveData.value = response
        }
}
