package com.example.homework2.util

import com.example.homework2.screen.ChannelScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import okhttp3.mockwebserver.MockResponse

fun MockRequestDispatcher.returnsForPathWithDefault(
    path: String,
    response: MockResponse.() -> MockResponse
) {
    returnsForPath("/users/me/subscriptions") { setBody(loadFromAssets("stream_list.json")) }
    returnsForPath("/users/me/383515/topics") { setBody(loadFromAssets("topic_list_of_first_stream.json")) }

    returnsForPath(path, response)
}

fun TestContext<Unit>.doDefaultStepsToGetMessages() {
    step("Нажатие на стрим для получения топиков") {
        ChannelScreen.streamList.childAt<ChannelScreen.StreamItem>(0) {
            btnShowTopics.click()
        }
    }
    step("Нажатие на топик для получени спика чата") {
        ChannelScreen.streamList.childAt<ChannelScreen.StreamItem>(1) {
            topicName.click()
        }
    }
}
