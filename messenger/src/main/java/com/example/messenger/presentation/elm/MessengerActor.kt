package com.example.messenger.presentation.elm

import com.example.common.adapter.interfaces.DelegateItem
import com.example.common.catchCancellationException
import com.example.messenger.di.deps.MessengerRouter
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.domain.model.ReactionModel
import com.example.messenger.domain.usecase.message.*
import com.example.messenger.domain.usecase.reaction.AddReactionInMessageUseCase
import com.example.messenger.domain.usecase.reaction.RemoveReactionInMessageUseCase
import com.example.messenger.presentation.mapper.MessageDomainToUiMapper
import com.example.messenger.presentation.ui.views.EmojiView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import java.io.File
import javax.inject.Inject

class MessengerActor @Inject constructor(
    private val subscribeOnMessageByStreamAndTopicUseCase: SubscribeOnMessageByStreamAndTopicUseCase,
    private val loadMessageByStreamAndTopicUseCase: LoadMessageByStreamAndTopicUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendMediaMessageUseCase: SendMediaMessageUseCase,
    private val updateMessageUseCase: UpdateMessageUseCase,
    private val deleteMessageByIdUseCase: DeleteMessageByIdUseCase,
    private val addReactionInMessageUseCase: AddReactionInMessageUseCase,
    private val removeReactionInMessageUseCase: RemoveReactionInMessageUseCase,
    private val deleteOldMessagesUseCase: DeleteOldMessagesUseCase,
    private val router: MessengerRouter,
    private val messageDomainToUiMapper: MessageDomainToUiMapper
) : Actor<MessengerCommand, MessengerEvent> {
    override fun execute(command: MessengerCommand): Flow<MessengerEvent> {
        return when (command) {
            is MessengerCommand.GetActualMessengerList -> getActualDialogList(
                command.streamName,
                command.topicName,
                command.anchor
            )
            is MessengerCommand.SubscribeOnMessengerList -> subscribeOnDialogList(
                command.streamName,
                command.topicName
            )
            is MessengerCommand.SendMessage -> sendMessage(
                command.message,
                command.streamName,
                command.topicName
            )
            is MessengerCommand.SendMediaMessage -> {
                sendMediaMessage(command.file, command.streamName, command.topicName)
            }
            is MessengerCommand.UpdateMessage -> {
                updateMessage(command.messageId, command.content, command.topic)
            }
            is MessengerCommand.DeleteMessage -> {
                deleteMessage(command.messageId)
            }
            is MessengerCommand.ChangeEmojiCounter -> changeEmojiCounter(
                command.messageModel,
                command.emojiView
            )
            is MessengerCommand.AddNewEmojiToReactions -> addNewEmojiToReactions(
                command.messageId,
                command.reaction
            )
            MessengerCommand.BtnBackPressed -> btnBackPressedAction()
            is MessengerCommand.DeleteOldMessages -> deleteOldMessages(
                command.streamName,
            )
            is MessengerCommand.TopicPressed -> topicPressedAction(
                command.streamName,
                command.topicName
            )
        }
    }

    private fun deleteMessage(messageId: Int): Flow<MessengerEvent> {
        return flow {
            emit(deleteMessageByIdUseCase(messageId))
        }.mapEvents {
            MessengerEvent.Internal.ErrorMessageDeleting
        }
    }

    private fun updateMessage(
        messageId: Int,
        content: String,
        topic: String
    ): Flow<MessengerEvent> {
        return flow {
            emit(updateMessageUseCase(messageId, content, topic))
        }.mapEvents {
            MessengerEvent.Internal.ErrorMessageUpdating(it)
        }
    }

    private fun topicPressedAction(streamName: String, topicName: String): Flow<MessengerEvent> {
        router.navigateToMessenger(streamName, topicName)
        return flow { emit(MessengerEvent.Internal.SuccessNavigate) }
    }

    private fun getActualDialogList(
        streamName: String,
        topicName: String?,
        anchor: String
    ): Flow<MessengerEvent> {
        return flow {
            emit(loadMessageByStreamAndTopicUseCase.invoke(streamName, topicName, anchor))
        }.flowOn(Dispatchers.Default)
            .mapEvents {
                MessengerEvent.Internal.ErrorMessengerLoading(it)
            }
    }

    private fun addNewEmojiToReactions(
        messageId: Int,
        reaction: ReactionModel
    ): Flow<MessengerEvent> {
        return flow<List<DelegateItem>> {
            addReactionInMessageUseCase(messageId, reaction.emojiName)
        }
            .flowOn(Dispatchers.Default)
            .mapEvents(
                { list ->
                    MessengerEvent.Internal.SuccessNewEmojiAdded(list)
                },
                { error ->
                    Timber.e(error.message)
                    MessengerEvent.Internal.ErrorNewEmojiAdding
                }
            )
    }

    private fun deleteOldMessages(streamName: String): Flow<MessengerEvent> {
        return flow {
            emit(deleteOldMessagesUseCase(streamName))
        }.flowOn(Dispatchers.Default)
            .mapEvents {
                MessengerEvent.Internal.ErrorOldMessagesDeleting
            }
    }

    private fun changeEmojiCounter(
        messageModel: MessageModel,
        emojiView: EmojiView
    ): Flow<MessengerEvent> {
        return flow<Unit> {
            val reaction = messageModel.reactions.first { it.emojiCode == emojiView.emoji }
            if (emojiView.isSelected) {
                addReactionInMessageUseCase(messageModel.id, reaction.emojiName)
            } else {
                removeReactionInMessageUseCase(messageModel.id, reaction.emojiName)
            }
        }.flowOn(Dispatchers.Default)
            .mapEvents { error ->
                catchCancellationException(error)
                Timber.e(error.message)
                MessengerEvent.Internal.ErrorCounterChanging
            }
    }

    private fun btnBackPressedAction(): Flow<MessengerEvent> {
        router.exit()
        return flow { emit(MessengerEvent.Internal.SuccessNavigate) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribeOnDialogList(
        streamName: String,
        topicName: String?
    ): Flow<MessengerEvent> {
        return subscribeOnMessageByStreamAndTopicUseCase(streamName, topicName)
            .distinctUntilChanged()
            .flatMapLatest {
                val delegateList = messageDomainToUiMapper.toDelegateItem(it)
                flowOf(delegateList)
            }.flowOn(Dispatchers.Default)
            .mapEvents(
                { list ->
                    MessengerEvent.Internal.MessengerListLoaded(list)

                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    MessengerEvent.Internal.ErrorMessengerLoading(error)
                }
            )
    }

    private fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): Flow<MessengerEvent> {
        return flow {
            emit(sendMessageUseCase(content, streamName, topicName))
        }
            .flowOn(Dispatchers.Default)
            .mapEvents(
                { MessengerEvent.Internal.MessageSent },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    MessengerEvent.Internal.ErrorMessageSending
                }
            )
    }

    private fun sendMediaMessage(
        file: File,
        streamName: String,
        topicName: String
    ): Flow<MessengerEvent> {
        return flow {
            emit(sendMediaMessageUseCase(file, streamName, topicName))
        }.mapEvents({
            MessengerEvent.Internal.MessageSent
        }, { error ->
            catchCancellationException(error)
            Timber.e(error.message)
            MessengerEvent.Internal.ErrorMessageSending
        })
    }
}
