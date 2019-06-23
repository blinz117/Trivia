package dev.bryanlindsey.trivia.triviaquestiondisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bryanlindsey.trivia.remote.response.TriviaApiResponse
import dev.bryanlindsey.trivia.remote.service.TriviaService
import kotlinx.coroutines.launch

private const val DEFAULT_QUESTION_COUNT = 10

class TriviaQuestionDisplayViewModel(private val triviaService: TriviaService) : ViewModel() {

    private val _triviaQuestionsLiveData = MutableLiveData<TriviaApiResponse>()

    var triviaQuestionsLiveData: LiveData<TriviaApiResponse> = _triviaQuestionsLiveData

    fun getMoreTriviaQuestions() =
        viewModelScope.launch {
            val response = triviaService.getTriviaQuestions(DEFAULT_QUESTION_COUNT)

            _triviaQuestionsLiveData.value = response
        }
}
