package dev.bryanlindsey.trivia.triviaquestiondisplay

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.bryanlindsey.trivia.OkHttpIdlingResourceRule
import dev.bryanlindsey.trivia.R
import dev.bryanlindsey.trivia.remote.BASE_URL_DI_INSTANCE_NAME
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class TriviaQuestionDisplayFragmentTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockServer = MockWebServer()

    @Before
    fun setUp() {
        mockServer.start()

        val mockServerUrl = mockServer.url("").toString()
        loadKoinModules(
            module {
                single(named(BASE_URL_DI_INSTANCE_NAME), override = true) {
                    mockServerUrl
                }
            }
        )
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun emptyResponse_showsNoQuestions() {
        mockServer.enqueue(
            MockResponse().setBody(
                """
                {
                    "response_code": 0,
                    "results": []
                }
                """.trimIndent()
            )
        )

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(ViewMatchers.withId(R.id.triviaQuestionCard)).check(ViewAssertions.doesNotExist())
    }

    @Test
    fun responseWithQuestion_showsQuestion() {
        mockServer.enqueue(
            MockResponse().setBody(
                """
                {
                    "response_code": 0,
                    "results": [
                        {
                            "category": "Test",
                            "type": "multiple",
                            "difficulty": "easy",
                            "question": "This is a test question",
                            "correct_answer": "Correct Answer",
                            "incorrect_answers": [
                                "Incorrect Answer 1",
                                "Incorrect Answer 2",
                                "Incorrect Answer 3"
                            ]
                        }
                    ]
                }
                """.trimIndent()
            )
        )

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(withText("This is a test question")).check(matches(isDisplayed()))
        onView(withText("Correct Answer")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 1")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 2")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 3")).check(matches(isDisplayed()))
    }

    @Test
    fun responseWithQuestion_showsSubmitButton() {
        mockServer.enqueue(
            MockResponse().setBody(
                """
                {
                    "response_code": 0,
                    "results": [
                        {
                            "category": "Test",
                            "type": "multiple",
                            "difficulty": "easy",
                            "question": "This is a test question",
                            "correct_answer": "Correct Answer",
                            "incorrect_answers": [
                                "Incorrect Answer 1",
                                "Incorrect Answer 2",
                                "Incorrect Answer 3"
                            ]
                        }
                    ]
                }
                """.trimIndent()
            )
        )

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(withText("Submit")).check(matches(isDisplayed()))
    }
}
