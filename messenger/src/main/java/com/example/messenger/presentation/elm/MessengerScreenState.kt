package com.example.messenger.presentation.elm

import android.os.Parcelable
import com.example.common.adapter.interfaces.DelegateItem
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.domain.model.ReactionModel
import com.example.messenger.presentation.ui.views.EmojiView
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.File
import javax.inject.Inject

@Parcelize
data class MessengerState(
    val dialogList: @RawValue List<DelegateItem>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    @Inject
    constructor() : this(null, null, false)
}

sealed interface MessengerEvent {
    sealed interface Ui : MessengerEvent {
        class LoadMessengerList(
            val streamName: String,
            val topicName: String?,
            val anchor: String
        ) : Ui

        class PreLoadMessengerList(
            val streamName: String,
            val topicName: String?,
            val anchor: String
        ) : Ui

        class SendMessage(val message: String, val streamName: String, val topicName: String) : Ui
        class SendMediaMessage(val file: File, val streamName: String, val topicName: String) : Ui
        class ChangeEmojiCounter(val messageModel: MessageModel, val emojiView: EmojiView) : Ui
        class AddNewEmojiToReactions(val messageId: Int, val reaction: ReactionModel) : Ui
        object BtnBackPressed : Ui
        class TopicPressed(val streamName: String, val topicName: String) : Ui
        class UpdateMessage(val messageId: Int, val content: String, val topic: String) : Ui
        class DeleteMessage(val messageId: Int) : Ui
        class DeleteOldMessages(val streamName: String) : Ui
        object Init : Ui
    }

    sealed interface Internal : MessengerEvent {
        // dialog
        class MessengerListLoaded(val list: List<DelegateItem>) : Internal
        class ErrorMessengerLoading(val error: Throwable) : Internal
        object ErrorOldMessagesDeleting : Internal

        //msg sending
        object MessageSent : Internal
        object ErrorMessageSending : Internal

        //change emoji counter
        object ErrorCounterChanging : Internal

        //add new emoji to reactions
        class SuccessNewEmojiAdded(val list: List<DelegateItem>) : Internal
        object ErrorNewEmojiAdding : Internal
        class ErrorMessageUpdating(val error: Throwable) : Internal
        object ErrorMessageDeleting : Internal

        //navigation
        object SuccessNavigate : Internal
    }
}

sealed interface MessengerEffect {
    object MessageDidNotSend : MessengerEffect
    object CounterEmojiDidNotChanged : MessengerEffect
    object NewEmojiDidNotAdded : MessengerEffect
    object ScrollToLastPosition : MessengerEffect
    object NewMessageAdded : MessengerEffect
    object ListNotLoaded : MessengerEffect
    object MessageDidNotUpdated : MessengerEffect
    object FieldsIsEmpty : MessengerEffect
    object TimeOutMessageUpdating : MessengerEffect
    object MessageDidNotDeleted : MessengerEffect
}

sealed interface MessengerCommand {
    class GetActualMessengerList(
        val streamName: String,
        val topicName: String?,
        val anchor: String
    ) :
        MessengerCommand

    class SendMediaMessage(val file: File, val streamName: String, val topicName: String) :
        MessengerCommand

    class SubscribeOnMessengerList(val streamName: String, val topicName: String?) :
        MessengerCommand

    class SendMessage(val message: String, val streamName: String, val topicName: String) :
        MessengerCommand

    class ChangeEmojiCounter(val messageModel: MessageModel, val emojiView: EmojiView) :
        MessengerCommand

    class UpdateMessage(val messageId: Int, val content: String, val topic: String) :
        MessengerCommand

    class DeleteMessage(val messageId: Int) : MessengerCommand

    class AddNewEmojiToReactions(val messageId: Int, val reaction: ReactionModel) : MessengerCommand
    object BtnBackPressed : MessengerCommand
    class TopicPressed(val streamName: String, val topicName: String) : MessengerCommand
    class DeleteOldMessages(val streamName: String) : MessengerCommand
}
