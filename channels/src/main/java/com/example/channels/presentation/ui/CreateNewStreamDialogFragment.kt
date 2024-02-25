package com.example.channels.presentation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.channels.R
import com.example.channels.databinding.FragmentDialogCreateNewStreamBinding
import com.example.channels.presentation.elm.ChannelEffect
import com.example.channels.presentation.elm.ChannelEvent
import com.example.channels.presentation.elm.ChannelState
import com.example.common.lazyUnsafe
import vivid.money.elmslie.core.store.Store
import com.example.common.R as CommonR

@SuppressLint("InflateParams")
class CreateNewStreamDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogCreateNewStreamBinding? = null
    private val binding get() = _binding!!

    var channelStore: Store<ChannelEvent, ChannelEffect, ChannelState>? = null
    private val fragmentView by lazyUnsafe {
        LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog_create_new_stream, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogCreateNewStreamBinding.bind(fragmentView)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.configure_new_stream))
            .setView(fragmentView)
            .setPositiveButton(getString(CommonR.string.create)) { dialog, _ ->
                binding.apply {
                    val name = etStreamName.text.toString()
                    val description = etDescription.text.toString()

                    channelStore?.accept(ChannelEvent.Ui.CreateNewStream(name, description))
                }
            }
            .setNegativeButton(getString(CommonR.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
