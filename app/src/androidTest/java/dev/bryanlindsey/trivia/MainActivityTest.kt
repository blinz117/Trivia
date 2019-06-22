package dev.bryanlindsey.trivia

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class MainActivityTest {

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
    fun userStartsOnWelcomeScreen() {
        ActivityScenario.launch(MainActivity::class.java)

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTrivia_hitsBackButton_returnsToWelcomeScreen() {
        ActivityScenario.launch(MainActivity::class.java)

        startTrivia()

        onView(withId(R.id.welcomeText)).check(doesNotExist())
        onView(withId(R.id.questionItemContainer)).check(matches(withId(R.id.questionItemContainer)))

        pressBack()

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTriviaThenSubmitsAnswers_showsResultsScreen() {
       navigateToResultsScreen()

        onView(withId(R.id.questionItemContainer)).check(doesNotExist())
        onView(withId(R.id.resultsPrompt)).check(matches(isDisplayed()))
    }

    @Test
    fun userStartsTriviaThenSubmitsAnswersThenHitsBack_returnsToWelcomeScreen() {
        navigateToResultsScreen()

        pressBack()

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTriviaThenSubmitsAnswersThenHitsQuit_returnsToWelcomeScreen() {
        navigateToResultsScreen()

        onView(withId(R.id.quitButton)).perform(click())

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTriviaThenSubmitsAnswersThenHitsPlayAgain_showsQuestionScreen() {
        navigateToResultsScreen()

        queueQuestionResponse()

        onView(withId(R.id.playAgainButton)).perform(click())

        onView(withId(R.id.questionItemContainer)).check(matches(withId(R.id.questionItemContainer)))
    }

    private fun assertIsOnWelcomeScreen() {
        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()))
    }

    private fun startTrivia() {
        queueQuestionResponse()

        onView(withId(R.id.startButton)).perform(click())
    }

    private fun submitAnswers() {
        onView(withText("Submit")).perform(click())
    }

    private fun navigateToResultsScreen() {
        ActivityScenario.launch(MainActivity::class.java)

        startTrivia()

        submitAnswers()
    }

    private fun queueQuestionResponse() {
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
    }
}