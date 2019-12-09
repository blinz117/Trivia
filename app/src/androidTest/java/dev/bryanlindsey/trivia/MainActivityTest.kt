package dev.bryanlindsey.trivia

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    // region Server Mocking Setup
    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockServer = MockWebServer()

    @Before
    fun setUp() {
        MockServerTestUtils.initializeMockServer(mockServer)
    }

    @After
    fun tearDown() {
        MockServerTestUtils.tearDownMockServer(mockServer)
    }
    // endregion

    @Test
    fun userStartsOnWelcomeScreen() {
        launchActivity()

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTrivia_hitsBackButton_returnsToWelcomeScreen() {
        launchActivity()

        startTrivia()

        onView(withId(R.id.welcomeText)).check(doesNotExist())
        onView(withId(R.id.questionItemContainer)).check(matches(withId(R.id.questionItemContainer)))

        pressBack()

        assertIsOnWelcomeScreen()
    }

    @Test
    fun userStartsTriviaThenSubmitsAnswers_showsResultsScreen() {
       navigateToResultsScreen()

        assertIsOnResultScreen()
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

    @Test
    fun givenUserOnResultsScreen_whenActivityIsRecreated_remainsOnResultsScreen() {
        navigateToResultsScreen()

        scenario.recreate()

        assertIsOnResultScreen()
    }

    private fun assertIsOnWelcomeScreen() {
        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()))
    }

    private fun assertIsOnResultScreen() {
        onView(withId(R.id.questionItemContainer)).check(doesNotExist())
        onView(withId(R.id.resultsPrompt)).check(matches(isDisplayed()))
    }

    private fun startTrivia() {
        queueQuestionResponse()

        onView(withId(R.id.startButton)).perform(click())
    }

    private fun submitAnswers() {
        onView(withText("Submit")).perform(click())
    }

    private fun navigateToResultsScreen() {
        launchActivity()

        startTrivia()

        submitAnswers()
    }

    private fun launchActivity() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun queueQuestionResponse() {
        MockServerTestUtils.setUpEmptyResponse(mockServer)
    }
}
