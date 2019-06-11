package dev.bryanlindsey.trivia

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel{ TriviaQuestionDisplayViewModel(get()) }
}