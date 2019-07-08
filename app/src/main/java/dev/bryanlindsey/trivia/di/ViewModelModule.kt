package dev.bryanlindsey.trivia.di

import dev.bryanlindsey.trivia.triviaquestiondisplay.TriviaQuestionDisplayViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { TriviaQuestionDisplayViewModel(get(), get()) }
}
