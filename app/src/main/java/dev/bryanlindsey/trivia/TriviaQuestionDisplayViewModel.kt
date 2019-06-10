package dev.bryanlindsey.trivia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bryanlindsey.trivia.remote.service.TriviaService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TriviaQuestionDisplayViewModel(private val triviaService: TriviaService) : ViewModel() {

    private val _triviaQuestionsLiveData = MutableLiveData<String>()

    var triviaQuestionsLiveData: LiveData<String> = _triviaQuestionsLiveData

    fun getMoreTriviaQuestions() =
            viewModelScope.launch {
                runBlocking {
                    val response = triviaService.getTriviaQuestions(10)

                    _triviaQuestionsLiveData.postValue(response.toString())
                }
            }
}