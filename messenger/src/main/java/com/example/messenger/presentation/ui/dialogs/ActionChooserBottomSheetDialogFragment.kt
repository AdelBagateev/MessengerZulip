package com.example.messenger.presentation.ui.dialogs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.messenger.databinding.FragmentBottomSheetDialogActionChooserBinding
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vivid.money.elmslie.core.store.Store

class ActionChooserBottomSheetDialogFragment : BottomSheetDialogFragment() {

    var message: MessageModel? = null
    var messengerStore: Store<MessengerEvent, MessengerEffect, MessengerState>? = null

    private var _binding: FragmentBottomSheetDialogActionChooserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogActionChooserBinding.inflate(inflater, container, false)

        if (message?.isMy == false) {
            binding.changeMessageTopic.isVisible = false
            binding.editMessage.isVisible = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addReaction.setOnClickListener {
                showBottomSheetEmojiChooser()
                dismiss()
            }
            deleteMessage.setOnClickListener {
                sendDeleteEvent()
                dismiss()
            }
            editMessage.setOnClickListener {
                showDialogMessageEditor()
                dismiss()
            }
            changeMessageTopic.setOnClickListener {
                showDialogTopicChanger()
                dismiss()
            }
            copyContentOfMessage.setOnClickListener {
                copyContent()
                dismiss()
            }
        }
    }

    private fun sendDeleteEvent() {
        message?.let {
            messengerStore?.accept(MessengerEvent.Ui.DeleteMessage(it.id))
        }
    }

    private fun copyContent() {
        val clipboardManager =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", message?.content)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun showDialogTopicChanger() {
        ChangeMessageTopicDialogFragment().apply {
            messengerStore = this@ActionChooserBottomSheetDialogFragment.messengerStore
            message = this@ActionChooserBottomSheetDialogFragment.message
        }
            .show(parentFragmentManager, null)
    }

    private fun showDialogMessageEditor() {
        EditMessageDialogFragment().apply {
            messengerStore = this@ActionChooserBottomSheetDialogFragment.messengerStore
            message = this@ActionChooserBottomSheetDialogFragment.message
        }
            .show(parentFragmentManager, null)
    }

    private fun showBottomSheetEmojiChooser() {
        message?.let {
            EmojiBottomSheetDialogFragment.newInstance(it.id).apply {
                this.dialogStore = messengerStore
            }
                .show(parentFragmentManager, null)
        }
    }
}
