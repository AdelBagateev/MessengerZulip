package com.example.messenger.elm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.messenger.domain.MessageRepository
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.domain.model.ReactionModel
import com.example.messenger.domain.usecase.message.*
import com.example.messenger.domain.usecase.reaction.AddReactionInMessageUseCaseImpl
import com.example.messenger.domain.usecase.reaction.RemoveReactionInMessageUseCaseImpl
import com.example.messenger.presentation.elm.MessengerActor
import com.example.messenger.presentation.elm.MessengerCommand
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.ui.adapters.main.delegates.message.MessageDelegateItem
import com.example.messenger.stub.*
import com.example.messenger.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MessengerActorTest {
    @get:Rule
    val actorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `get messages from db`() = runTest {
        val messages =
            listOf(
                createMessage(1),
                createMessage(2),
                createMessage(3),
                createMessage(4),
                createMessage(5),
                createMessage(6),
                createMessage(7),
                createMessage(8),
            )
        val expectedLastMessage = messages.last()
        val repository = MessageRepositoryStub().apply {
            loadMessagesResultProvider = { messages }
        }

        val actor = createDialogActor(repository)
        //грузим актуальный список
        actor.execute(MessengerCommand.GetActualMessengerList("test", "test", "test"))
            .collect()
        //получаем список
        val data = actor.execute(MessengerCommand.SubscribeOnMessengerList("test", "test"))

        data.first {
            assertTrue(it is MessengerEvent.Internal.MessengerListLoaded)
            if (it is MessengerEvent.Internal.MessengerListLoaded) {
                val lastMessage = it.list.last() as MessageDelegateItem
                assertEquals(expectedLastMessage, lastMessage.value)
            }
            true
        }
    }

    @Test
    fun `get messages when db is empty`() = runTest {
        val messages =
            emptyList<MessageModel>()
        val repository = MessageRepositoryStub().apply {
            loadMessagesResultProvider = { messages }
        }
        val expected = null
        val actor = createDialogActor(repository)

        //получаем список
        val data = actor.execute(MessengerCommand.SubscribeOnMessengerList("test", "test"))

        //данные вообще не должны прийти
        //тк единственный источник данных - бд, и пустые листы фильтруются
        val ans = withTimeoutOrNull(1000) {
            data.firstOrNull()
        }
        assertEquals(expected, ans)

    }

    @Test
    fun `get messages with error`() = runTest {
        val repository = MessageRepositoryStub().apply {
            throwException = true
        }

        val actor = createDialogActor(repository)
        //грузим актуальный список
        val response =
            actor.execute(MessengerCommand.GetActualMessengerList("test", "test", "newest"))
        response.first {
            assert(it is MessengerEvent.Internal.ErrorMessengerLoading)
            true
        }
    }

    @Test
    fun `add reaction to message`() = runTest {
        val messages =
            listOf(
                createMessage(1, reactions = createReactionList()),
                createMessage(2),
                createMessage(3),
                createMessage(4),
                createMessage(5),
                createMessage(6),
                createMessage(7),
                createMessage(8),
            )
        val expectedChangedMessage = messages.first()

        val repository = MessageRepositoryStub().apply {
            reactionResultProvider = { messages }
        }
        val actor = createDialogActor(repository)
        //ставим реакцию + грузим актуальный список
        actor.execute(MessengerCommand.AddNewEmojiToReactions(1, createReaction())).collect()
        //получаем изменный список
        val data = actor.execute(MessengerCommand.SubscribeOnMessengerList("test", "test"))

        data.first {
            if (it is MessengerEvent.Internal.MessengerListLoaded) {
                val changedMessage = it.list[1] as MessageDelegateItem
                assertEquals(expectedChangedMessage, changedMessage.value)
            }
            true
        }
    }

    private fun createMessage(
        id: Int = 1,
        timestamp: Int = 1,
        avatarUrl: String = "",
        nameSender: String = "",
        content: String = "",
        mediaContent: String? = "",
        reactions: MutableList<ReactionModel> = mutableListOf(),
        isMy: Boolean = false,
        streamName: String = "",
        topicName: String = ""
    ): MessageModel {
        return MessageModel(
            id,
            timestamp,
            avatarUrl,
            nameSender,
            content,
            mediaContent,
            reactions,
            isMy,
            streamName,
            topicName
        )
    }

    private fun createReactionList(): MutableList<ReactionModel> {
        return mutableListOf(
            createReaction(count = 1),
            createReaction(count = 3),
            createReaction(count = 2),
        )
    }

    private fun createReaction(
        emojiCode: String = "",
        userId: Int = 1,
        count: Int = 1,
        isSelected: Boolean = false,
        emojiName: String = "",
    ): ReactionModel {
        return ReactionModel(
            emojiCode,
            userId,
            count,
            isSelected,
            emojiName
        )
    }

    private fun createDialogActor(
        repository: MessageRepository
    ): MessengerActor {
        return MessengerActor(
            subscribeOnMessageByStreamAndTopicUseCase = SubscribeOnMessageByStreamAndTopicUseCaseImpl(
                repository
            ),
            loadMessageByStreamAndTopicUseCase = LoadMessageByStreamAndTopicUseCaseImpl(repository),
            sendMessageUseCase = SendMessageUseCaseImpl(repository),
            sendMediaMessageUseCase = SendMediaMessageUseCaseImpl(repository),
            updateMessageUseCase = UpdateMessageUseCaseImpl(repository),
            deleteMessageByIdUseCase = DeleteMessageByIdUseCaseImpl(repository),
            addReactionInMessageUseCase = AddReactionInMessageUseCaseImpl(repository),
            removeReactionInMessageUseCase = RemoveReactionInMessageUseCaseImpl(repository),
            deleteOldMessagesUseCase = DeleteOldMessagesUseCaseImpl(repository),
            router = MessengerRouterStub(),
            messageDomainToUiMapper = MessagePresenterToDelegateItemStub(),
        )
    }
}
