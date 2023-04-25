package com.example.homework2.dialog.presentation.elm

import android.os.Parcelable
import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.domain.model.ReactionModel
import com.example.homework2.views.EmojiView
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.File
import javax.inject.Inject

@Parcelize
data class DialogState(
    val dialogList: @RawValue List<DelegateItem>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    @Inject
    constructor() : this(null, null, false)
}

sealed interface DialogEvent {
    sealed interface Ui : DialogEvent {
        class LoadDialogList(val streamName: String, val topicName: String, val anchor: String) : Ui
        class PreLoadDialogList(val streamName: String, val topicName: String, val anchor: String) :
            Ui

        class SendMessage(val message: String, val streamName: String, val topicName: String) : Ui
        class SendMediaMessage(val file: File, val streamName: String, val topicName: String) : Ui
        class ChangeEmojiCounter(val messageModel: MessageModel, val emojiView: EmojiView) : Ui
        class AddNewEmojiToReactions(val messageId: Int, val reaction: ReactionModel) : Ui
        object BtnBackPressed : Ui
        class DeleteOldMessages(val streamName: String, val topicName: String) : Ui
        object Init : Ui
    }

    sealed interface Internal : DialogEvent {
        // dialog
        class DialogListLoaded(val list: List<DelegateItem>) : Internal
        class ErrorDialogLoading(val error: Throwable) : Internal
        object ErrorOldMessagesDeleting : Internal
        //msg sending
        object MessageSent : Internal
        object ErrorMessageSending : Internal

        //change emoji counter
        object ErrorCounterChanging : Internal

        //add new emoji to reactions
        class SuccessNewEmojiAdded(val list: List<DelegateItem>) : Internal
        object ErrorNewEmojiAdding : Internal

        //navigation
        object SuccessNavigate : Internal
    }
}

sealed interface DialogEffect {
    object MessageDidNotSend : DialogEffect
    object CounterEmojiDidNotChanged : DialogEffect
    object NewEmojiDidNotAdded : DialogEffect
    object ScrollToLastPosition : DialogEffect
    object NewMessageAdded : DialogEffect
    object ListNotLoaded : DialogEffect
}

sealed interface DialogCommand {
    class GetActualDialogList(val streamName: String, val topicName: String, val anchor: String) :
        DialogCommand

    class SendMediaMessage(val file: File, val streamName: String, val topicName: String) :
        DialogCommand

    class SubscribeOnDialogList(val streamName: String, val topicName: String) : DialogCommand
    class SendMessage(val message: String, val streamName: String, val topicName: String) :
        DialogCommand

    class ChangeEmojiCounter(val messageModel: MessageModel, val emojiView: EmojiView) :
        DialogCommand

    class AddNewEmojiToReactions(val messageId: Int, val reaction: ReactionModel) : DialogCommand
    object BtnBackPressed : DialogCommand
    class DeleteOldMessages(val streamName: String, val topicName: String) : DialogCommand
}
