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
import com.example.messenger.databinding.FragmentDialogChangeMessageTopicBinding
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import vivid.money.elmslie.core.store.Store

@SuppressLint("InflateParams")
class ChangeMessageTopicDialogFragment : DialogFragment() {
    private var _binding: FragmentDialogChangeMessageTopicBinding? = null
    private val binding get() = _binding!!

    var messengerStore: Store<MessengerEvent, MessengerEffect, MessengerState>? = null
    var message: MessageModel? = null

    private val fragmentView by lazyUnsafe {
        LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_change_message_topic, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogChangeMessageTopicBinding.bind(fragmentView)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.change_message_topic))
            .setView(fragmentView)
            .setPositiveButton(getString(R.string.change)) { dialog, _ ->
                binding.apply {
                    val newTopic = etMessageTopic.text.toString()
                    message?.let {
                        messengerStore?.accept(
                            MessengerEvent.Ui.UpdateMessage(it.id, it.content, newTopic)
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
