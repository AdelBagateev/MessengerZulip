package com.example.homework2

import androidx.test.core.app.ActivityScenario
import com.example.homework2.di.network.UrlProvider
import com.example.homework2.screen.ActionChooserScreen
import com.example.homework2.screen.EmojiSelectorScreen
import com.example.homework2.screen.MessengerScreen
import com.example.homework2.util.MockRequestDispatcher
import com.example.homework2.util.doDefaultStepsToGetMessages
import com.example.homework2.util.loadFromAssets
import com.example.homework2.util.returnsForPathWithDefault
import com.example.messenger.presentation.ui.views.EmojiView
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.common.views.KView
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MessengerTest : TestCase() {
    private val messagesUrl =
        "/messages?narrow=%5B%7B%22operator%22%3A%22stream%22%2C%22operand%22%3A%22PhotoTest%22%7D%2C%7B%22operator%22%3A%22topic%22%2C%22operand%22%3A%22stream%20events%22%7D%5D&num_before=20&num_after=0&anchor=newest&apply_markdown=false"

    @get:Rule
    val mockServer = MockWebServer()

    @Before
    fun setUp() {
        UrlProvider.url = mockServer.url("/").toString()
    }

    @After
    fun tearDown() {
        UrlProvider.reset()
    }

    @Test
    fun showMessageList() = run {
        ActivityScenario.launch(MainActivity::class.java)
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_not_empty.json")) }
        }

        doDefaultStepsToGetMessages()

        step("Список чата отображается") {
            MessengerScreen.messageList.isDisplayed()
        }
        step("Послденее сообщение содержит в себе контент") {
            MessengerScreen.messageList.lastChild<MessengerScreen.DialogItem> {
                message.hasAnyText()
            }
        }
    }

    @Test
    fun showEmptyList() = run {
        ActivityScenario.launch(MainActivity::class.java)
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_empty.json")) }
        }

        doDefaultStepsToGetMessages()

        step("Количества элементов чата = 0") {
            MessengerScreen.messageList.hasSize(0)
        }
    }

    @Test
    fun showError_WhenLoadingIsFailed() = run {
        ActivityScenario.launch(MainActivity::class.java)

        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) {
                setBody(loadFromAssets("messages_empty.json")).setResponseCode(404)
            }
        }

        doDefaultStepsToGetMessages()

        step("Snackbar  появился") {
            MessengerScreen.errorSnackbar.isDisplayed()
        }
    }

    @Test
    fun groupMessagesByDate() = run {
        ActivityScenario.launch(MainActivity::class.java)
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_not_empty.json")) }
        }
        doDefaultStepsToGetMessages()
        step("Даты проставленны над группами сообщений") {
            MessengerScreen.messageList.childAt<MessengerScreen.DialogItem>(20) {
                date.hasText("27 April")
            }
            MessengerScreen.messageList.childAt<MessengerScreen.DialogItem>(24) {
                date.hasText("28 April")
            }
        }
    }

    @Test
    fun messageHaveNotReactions() = run {
        ActivityScenario.launch(MainActivity::class.java)
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_not_empty.json")) }
        }
        doDefaultStepsToGetMessages()
        step("У сообщения нет реакции") {
            MessengerScreen.messageList.childAt<MessengerScreen.DialogItem>(22) {
                reactions.isNotDisplayed()
            }
        }
    }

    @Test
    fun messageHaveMyAndOtherReaction() = run {
        ActivityScenario.launch(MainActivity::class.java)
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_not_empty.json")) }
        }
        doDefaultStepsToGetMessages()
        step("Первая реакция последнего сообщения не нажата") {
            MessengerScreen.apply {
                messageList.childAt<MessengerScreen.DialogItem>(getMessageListSize() - 1) {
                    KView(parent) {
                        isAssignableFrom(EmojiView::class.java)
                        withIndex(0) {}
                    }.isNotSelected()
                }

            }
        }
        step("Вторая реакция последнего сообщения нажата") {
            MessengerScreen.apply {
                messageList.childAt<MessengerScreen.DialogItem>(getMessageListSize() - 1) {
                    KView(parent) {
                        isAssignableFrom(EmojiView::class.java)
                        withIndex(1) {}
                    }.isSelected()
                }
            }
        }
    }

    @Test
    fun messageCanBeAddedWithNewReaction() = run {
        ActivityScenario.launch(MainActivity::class.java)
        val updatedMessageUrl =
            "/messages?num_before=0&num_after=0&anchor=353140530&apply_markdown=false"
        val addReactionUrl = "/messages/353140530/reactions?emoji_name=grinning"
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPathWithDefault(messagesUrl) { setBody(loadFromAssets("messages_not_empty.json")) }
            returnsForPath(addReactionUrl) { setBody(loadFromAssets("success_new_emoji_added.json")) }
            returnsForPath(updatedMessageUrl) { setBody(loadFromAssets("updated_message_with_reaction.json")) }
        }
        doDefaultStepsToGetMessages()

        step("longClick на сообщение") {
            MessengerScreen.apply {
                messageList.childAt<MessengerScreen.DialogItem>(getMessageListSize() - 3) {
                    message.longClick()
                }
            }
        }
        step("нажатие на кнопку add reaction") {
            Thread.sleep(100)
            ActionChooserScreen.addReactionBtn.click()
        }
        step("выбираем emoji в bottomSheetDialog") {
            EmojiSelectorScreen.emojiList.childAt<EmojiSelectorScreen.EmojiItem>(0) {
                emoji.click()
            }
        }
        step("Реакция проставилась") {
            MessengerScreen.apply {
                messageList.childAt<MessengerScreen.DialogItem>(getMessageListSize() - 3) {
                    reactions.isDisplayed()
                }
            }
        }
    }
}
