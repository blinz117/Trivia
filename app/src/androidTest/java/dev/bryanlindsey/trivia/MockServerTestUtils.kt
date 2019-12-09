package dev.bryanlindsey.trivia

import dev.bryanlindsey.trivia.remote.BASE_URL_DI_INSTANCE_NAME
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

// TODO: Find a better way to handle reused code.
// I'm not a huge fan of "Util" classes. I'd prefer like a test rule or something. It's good enough
// for now to reduce duplicate code in the tests, which serves my immediate purpose, but I'd like
// a longer term solution at some point.
object MockServerTestUtils {

    fun initializeMockServer(mockServer: MockWebServer) {
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

    fun tearDownMockServer(mockServer: MockWebServer) {
        mockServer.shutdown()
    }

    fun setUpEmptyResponse(mockServer: MockWebServer) {
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

    fun setUpSuccessfulResponse(mockServer: MockWebServer) {
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
    }
}
