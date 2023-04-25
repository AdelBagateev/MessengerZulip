package com.example.homework2.dialog.presentation.elm

import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.common.catchCancellationException
import com.example.homework2.dialog.data.datasource.remote.response.SendMessageResponse
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.domain.model.ReactionModel
import com.example.homework2.dialog.domain.usecase.message.*
import com.example.homework2.dialog.domain.usecase.reaction.AddReactionInMessageUseCase
import com.example.homework2.dialog.domain.usecase.reaction.RemoveReactionInMessageUseCase
import com.example.homework2.dialog.utils.DialogMapperPresenter
import com.example.homework2.views.EmojiView
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import java.io.File
import javax.inject.Inject

class DialogActor @Inject constructor(
    private val subscribeOnMessageByStreamAndTopicUseCase: SubscribeOnMessageByStreamAndTopicUseCase,
    private val loadMessageByStreamAndTopicUseCase: LoadMessageByStreamAndTopicUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val addReactionInMessageUseCase: AddReactionInMessageUseCase,
    private val removeReactionInMessageUseCase: RemoveReactionInMessageUseCase,
    private val deleteOldMessagesUseCase: DeleteOldMessagesUseCase,
    private val sendMediaMessageUseCase: SendMediaMessageUseCase,
    private val router: Router,
    private val mapper: DialogMapperPresenter
) : Actor<DialogCommand, DialogEvent> {


    override fun execute(command: DialogCommand): Flow<DialogEvent> {
        return when (command) {
            is DialogCommand.GetActualDialogList -> getActualDialogList(
                command.streamName,
                command.topicName,
                command.anchor
            )
            is DialogCommand.SubscribeOnDialogList -> subscribeOnDialogList(
                command.streamName,
                command.topicName
            )
            is DialogCommand.SendMessage -> sendMessage(
                command.message,
                command.streamName,
                command.topicName
            )
            is DialogCommand.SendMediaMessage -> {
                sendMediaMessage(command.file, command.streamName, command.topicName)
            }
            is DialogCommand.ChangeEmojiCounter -> changeEmojiCounter(
                command.messageModel,
                command.emojiView
            )
            is DialogCommand.AddNewEmojiToReactions -> addNewEmojiToReactions(
                command.messageId,
                command.reaction
            )
            DialogCommand.BtnBackPressed -> btnBackPressedAction()
            is DialogCommand.DeleteOldMessages -> deleteOldMessages(
                command.streamName,
                command.topicName
            )
        }
    }

    private fun getActualDialogList(
        streamName: String,
        topicName: String,
        anchor: String
    ): Flow<DialogEvent> {
        return flow {
            emit(loadMessageByStreamAndTopicUseCase.invoke(streamName, topicName, anchor))
        }.flowOn(Dispatchers.Default)
            .mapEvents {
                DialogEvent.Internal.ErrorDialogLoading(it)
            }
    }

    private fun addNewEmojiToReactions(messageId: Int, reaction: ReactionModel): Flow<DialogEvent> {
        return flow<List<DelegateItem>> {
            addReactionInMessageUseCase(messageId, reaction.emojiName)
        }
            .flowOn(Dispatchers.Default)
            .mapEvents(
                { list ->
                    DialogEvent.Internal.SuccessNewEmojiAdded(list)
                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    DialogEvent.Internal.ErrorNewEmojiAdding
                }
            )
    }

    private fun deleteOldMessages(streamName: String, topicName: String): Flow<DialogEvent> {
        return flow {
            emit(deleteOldMessagesUseCase(streamName, topicName))
        }.flowOn(Dispatchers.Default)
            .mapEvents {
                DialogEvent.Internal.ErrorOldMessagesDeleting
            }
    }

    private fun changeEmojiCounter(
        messageModel: MessageModel,
        emojiView: EmojiView
    ): Flow<DialogEvent> {
        return flow<Nothing> {
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
                DialogEvent.Internal.ErrorCounterChanging
            }
    }

    private fun btnBackPressedAction(): Flow<DialogEvent> {
        router.exit()
        return flow { emit(DialogEvent.Internal.SuccessNavigate) }
    }

    private fun subscribeOnDialogList(streamName: String, topicName: String): Flow<DialogEvent> {
        return subscribeOnMessageByStreamAndTopicUseCase(streamName, topicName)
            .distinctUntilChanged()
            .flatMapLatest {
                val delegateList = mapper.toMessagesDataDelegates(it)
                flowOf(delegateList)
            }.flowOn(Dispatchers.Default)
            .mapEvents(
                { list ->
                    DialogEvent.Internal.DialogListLoaded(list)
                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    DialogEvent.Internal.ErrorDialogLoading(error)
                }
            )
    }

    private fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): Flow<DialogEvent> {
        return flow<SendMessageResponse> {
            emit(sendMessageUseCase(content, streamName, topicName))
        }
            .flowOn(Dispatchers.Default)
            .mapEvents(
                { DialogEvent.Internal.MessageSent },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    DialogEvent.Internal.ErrorMessageSending
                }
            )
    }

    private fun sendMediaMessage(file: File, streamName: String, topicName: String): Flow<DialogEvent> {
        return flow {
        emit(sendMediaMessageUseCase(file, streamName, topicName))
        }.mapEvents({
            DialogEvent.Internal.MessageSent
        }, {error->
            catchCancellationException(error)
            Timber.e(error.message)
            DialogEvent.Internal.ErrorMessageSending
        })
    }
}
