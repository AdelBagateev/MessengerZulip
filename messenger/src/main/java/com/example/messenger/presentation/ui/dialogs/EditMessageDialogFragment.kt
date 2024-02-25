package com.example.messenger.presentation.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.common.lazyUnsafe
import com.example.messenger.R
import com.example.messenger.databinding.FragmentDialogEditMessageBinding
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import vivid.money.elmslie.core.store.Store

@SuppressLint("InflateParams")
class EditMessageDialogFragment : DialogFragment() {
    private var _binding: FragmentDialogEditMessageBinding? = null
    private val binding get() = _binding!!

    var messengerStore: Store<MessengerEvent, MessengerEffect, MessengerState>? = null
    var message: MessageModel? = null

    private val fragmentView by lazyUnsafe {
        LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_edit_message, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogEditMessageBinding.bind(fragmentView)
        binding.etMessageContent.setText(message?.content)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.edit_message_content))
            .setView(fragmentView)
            .setPositiveButton(getString(R.string.save)) { dialog, _ ->
                binding.apply {
                    val newContent = etMessageContent.text.toString()
                    message?.let {
                        messengerStore?.accept(
                            MessengerEvent.Ui.UpdateMessage(it.id, newContent, it.topicName)
                        )
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
