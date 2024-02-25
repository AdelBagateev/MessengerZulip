package com.example.messenger.presentation.elm

import retrofit2.HttpException
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class MessengerReducer @Inject constructor() :
    DslReducer<MessengerEvent, MessengerState, MessengerEffect, MessengerCommand>() {
    override fun Result.reduce(event: MessengerEvent): Any {
        return when (event) {
            //UI
            is MessengerEvent.Ui.LoadMessengerList -> {
                state {
                    copy(
                        isLoading = true,
                        error = null
                    )
                }
                commands {
                    +MessengerCommand.GetActualMessengerList(
                        event.streamName,
                        event.topicName,
                        event.anchor
                    )
                }
                commands {
                    +MessengerCommand.SubscribeOnMessengerList(
                        event.streamName,
                        event.topicName
                    )
                }
            }
            is MessengerEvent.Ui.PreLoadMessengerList -> {
                commands {
                    +MessengerCommand.GetActualMessengerList(
                        event.streamName,
                        event.topicName,
                        event.anchor
                    )
                }
            }
            is MessengerEvent.Ui.SendMessage -> {
                commands {
                    +MessengerCommand.SendMessage(
                        event.message,
                        event.streamName,
                        event.topicName
                    )
                }
            }
            is MessengerEvent.Ui.SendMediaMessage -> {
                commands {
                    +MessengerCommand.SendMediaMessage(
                        event.file,
                        event.streamName,
                        event.topicName
                    )
                }
            }
            is MessengerEvent.Ui.ChangeEmojiCounter -> {
                commands {
                    +MessengerCommand.ChangeEmojiCounter(
                        event.messageModel,
                        event.emojiView
                    )
                }
            }
            is MessengerEvent.Ui.AddNewEmojiToReactions -> {
                commands {
                    +MessengerCommand.AddNewEmojiToReactions(
                        event.messageId,
                        event.reaction
                    )
                }
            }
            MessengerEvent.Ui.BtnBackPressed -> {
                state { MessengerState() }
                commands {
                    +MessengerCommand.BtnBackPressed
                }
            }
            is MessengerEvent.Ui.TopicPressed -> {
                state { MessengerState() }
                commands {
                    +MessengerCommand.TopicPressed(event.streamName, event.topicName)
                }
            }
            is MessengerEvent.Ui.DeleteOldMessages -> {
                commands { +MessengerCommand.DeleteOldMessages(event.streamName) }
            }
            is MessengerEvent.Ui.UpdateMessage -> {

                if (isFieldNotEmpty(event.content, event.topic)) {
                    commands {
                        +MessengerCommand.UpdateMessage(event.messageId, event.content, event.topic)
                    }
                } else {
                    effects { +MessengerEffect.FieldsIsEmpty }
                }
            }
            is MessengerEvent.Ui.DeleteMessage -> {
                commands { +MessengerCommand.DeleteMessage(event.messageId) }
            }
            MessengerEvent.Ui.Init -> {
                state { copy() }
            }
            //INTERNAL
            //dialog
            is MessengerEvent.Internal.MessengerListLoaded -> {
                state { copy(isLoading = false, error = null, dialogList = event.list) }
            }
            is MessengerEvent.Internal.ErrorMessengerLoading -> {
                effects { +MessengerEffect.ListNotLoaded }
            }
            //message sending
            MessengerEvent.Internal.MessageSent -> {
                effects { +MessengerEffect.NewMessageAdded }
            }
            MessengerEvent.Internal.ErrorMessageSending -> {
                effects { +MessengerEffect.MessageDidNotSend }
            }
            is MessengerEvent.Internal.ErrorMessageUpdating -> {
                if (event.error is HttpException) {
                    effects { +MessengerEffect.TimeOutMessageUpdating }
                } else {
                    effects { +MessengerEffect.MessageDidNotUpdated }
                }
            }
            MessengerEvent.Internal.ErrorMessageDeleting -> {
                effects { +MessengerEffect.MessageDidNotDeleted }
            }
            //emoji counter changing
            MessengerEvent.Internal.ErrorCounterChanging -> {
                effects { +MessengerEffect.CounterEmojiDidNotChanged }
            }
            // add new emoji
            is MessengerEvent.Internal.SuccessNewEmojiAdded -> {
                state {
                    copy(
                        isLoading = false, error = null, dialogList = event.list,
                    )
                }
            }
            MessengerEvent.Internal.ErrorNewEmojiAdding -> {
                effects { +MessengerEffect.NewEmojiDidNotAdded }
            }

            // navigation
            MessengerEvent.Internal.SuccessNavigate -> {
                state { MessengerState() }
            }
            MessengerEvent.Internal.ErrorOldMessagesDeleting -> {
                effects { +MessengerEffect.MessageDidNotDeleted }
            }
        }
    }

    private fun isFieldNotEmpty(content: String, topicName: String): Boolean {
        if (content.isEmpty() || topicName.isEmpty()) {
            return false
        }
        return true
    }
}
