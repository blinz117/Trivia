package dev.bryanlindsey.trivia.triviaquestiondisplay

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.bryanlindsey.trivia.MockServerTestUtils
import dev.bryanlindsey.trivia.OkHttpIdlingResourceRule
import dev.bryanlindsey.trivia.R
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TriviaQuestionDisplayFragmentTest {

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
    fun emptyResponse_showsNoQuestions() {
        MockServerTestUtils.setUpEmptyResponse(mockServer)

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(ViewMatchers.withId(R.id.triviaQuestionCard)).check(ViewAssertions.doesNotExist())
    }

    @Test
    fun responseWithQuestion_showsQuestion() {
        MockServerTestUtils.setUpSuccessfulResponse(mockServer)

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(withText("This is a test question")).check(matches(isDisplayed()))
        onView(withText("Correct Answer")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 1")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 2")).check(matches(isDisplayed()))
        onView(withText("Incorrect Answer 3")).check(matches(isDisplayed()))
    }

    @Test
    fun responseWithQuestion_showsSubmitButton() {
        MockServerTestUtils.setUpSuccessfulResponse(mockServer)

        launchFragmentInContainer<TriviaQuestionDisplayFragment>()

        onView(withText("Submit")).check(matches(isDisplayed()))
    }
}
