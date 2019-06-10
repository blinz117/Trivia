package dev.bryanlindsey.trivia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bryanlindsey.trivia.remote.service.BASE_URL
import dev.bryanlindsey.trivia.remote.service.TriviaService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TriviaQuestionDisplayViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(TriviaService::class.java)

    private val _triviaQuestionsLiveData = MutableLiveData<String>()

    var triviaQuestionsLiveData: LiveData<String> = _triviaQuestionsLiveData

    fun getMoreTriviaQuestions() =
            viewModelScope.launch {
                runBlocking {
                    val response = service.getTriviaQuestions(10)

                    _triviaQuestionsLiveData.postValue(response.toString())
                }
            }
}