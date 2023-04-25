package com.example.homework2.dialog.presentation.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class DialogReducer @Inject constructor() :
    DslReducer<DialogEvent, DialogState, DialogEffect, DialogCommand>() {
    override fun Result.reduce(event: DialogEvent): Any {
        return when (event) {
            //UI
            is DialogEvent.Ui.LoadDialogList -> {
                state {
                    copy(
                        isLoading = true,
                        error = null
                    )
                }
                commands {
                    +DialogCommand.GetActualDialogList(
                        event.streamName,
                        event.topicName,
                        event.anchor
                    )
                }
                commands { +DialogCommand.SubscribeOnDialogList(event.streamName, event.topicName) }
            }
            is DialogEvent.Ui.PreLoadDialogList -> {
                commands {
                    +DialogCommand.GetActualDialogList(
                        event.streamName,
                        event.topicName,
                        event.anchor
                    )
                }
            }
            is DialogEvent.Ui.SendMessage -> {
                commands {
                    +DialogCommand.SendMessage(
                        event.message,
                        event.streamName,
                        event.topicName
                    )
                }
            }
            is DialogEvent.Ui.SendMediaMessage -> {
                commands { +DialogCommand.SendMediaMessage(event.file, event.streamName, event.topicName) }
            }
            is DialogEvent.Ui.ChangeEmojiCounter -> {
                commands { +DialogCommand.ChangeEmojiCounter(event.messageModel, event.emojiView) }
            }
            is DialogEvent.Ui.AddNewEmojiToReactions -> {
                commands { +DialogCommand.AddNewEmojiToReactions(event.messageId, event.reaction) }
            }
            DialogEvent.Ui.BtnBackPressed -> {
                state { DialogState() }
                commands {
                    +DialogCommand.BtnBackPressed
                }
            }
            is DialogEvent.Ui.DeleteOldMessages -> {
                commands { +DialogCommand.DeleteOldMessages(event.streamName, event.topicName) }
            }

            DialogEvent.Ui.Init -> {
                state { copy() }
            }
            //INTERNAL
            //dialog
            is DialogEvent.Internal.DialogListLoaded -> {
                state { copy(isLoading = false, error = null, dialogList = event.list) }
            }
            is DialogEvent.Internal.ErrorDialogLoading -> {
                effects { +DialogEffect.ListNotLoaded }
            }
            //message sending
            DialogEvent.Internal.MessageSent -> {
                effects { +DialogEffect.NewMessageAdded }
            }
            DialogEvent.Internal.ErrorMessageSending -> {
                effects { +DialogEffect.MessageDidNotSend }
            }
            //emoji counter changing
            DialogEvent.Internal.ErrorCounterChanging -> {
                effects { +DialogEffect.CounterEmojiDidNotChanged }
            }
            // add new emoji
            is DialogEvent.Internal.SuccessNewEmojiAdded -> {
                state {
                    copy(
                        isLoading = false, error = null, dialogList = event.list,
                    )
                }
            }
            DialogEvent.Internal.ErrorNewEmojiAdding -> {
                effects { +DialogEffect.NewEmojiDidNotAdded }
            }

            // navigation
            DialogEvent.Internal.SuccessNavigate -> {
                state { DialogState() }
            }
            DialogEvent.Internal.ErrorOldMessagesDeleting -> {}
        }
    }

}
