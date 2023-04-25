package com.example.homework2.dialog.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework2.common.lazyUnsafe
import com.example.homework2.databinding.FragmentBottomSheetDialogEmojiBinding
import com.example.homework2.dialog.domain.model.ReactionModel
import com.example.homework2.dialog.presentation.elm.DialogEffect
import com.example.homework2.dialog.presentation.elm.DialogEvent
import com.example.homework2.dialog.presentation.elm.DialogState
import com.example.homework2.dialog.presentation.ui.adapters.emoji.EmojisAdapter
import com.example.homework2.dialog.utils.ReactionsRepo
import com.example.homework2.dialog.utils.toReactionList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import vivid.money.elmslie.core.store.Store

class EmojiBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDialogEmojiBinding? = null
    private val binding get() = _binding!!

    private var messageId: Int? = null

    var dialogStore: Store<DialogEvent, DialogEffect, DialogState>? = null

    private val emojiAdapter: EmojisAdapter by lazyUnsafe {
        EmojisAdapter { onEmojiClick(it) }
    }
    private val emojis by lazyUnsafe {
        ReactionsRepo.getEmojisList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            messageId = getInt(MESSAGE_ID)
        }
        _binding = FragmentBottomSheetDialogEmojiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEmojis.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        binding.rvEmojis.adapter = emojiAdapter
        lifecycleScope.launch {
            emojiAdapter.submitList(emojis.toReactionList())
        }
    }


    private fun onEmojiClick(reaction: ReactionModel) {
        lifecycleScope.launch {
            messageId?.let {
                dialogStore?.accept(DialogEvent.Ui.AddNewEmojiToReactions(it, reaction))
            }
        }
        dismiss()
    }

    companion object {
        fun newInstance(messageId: Int): EmojiBottomSheetDialog {
            val bottomSheetDialog = EmojiBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putInt(MESSAGE_ID, messageId)
                }
            }
            return bottomSheetDialog
        }

        private const val SPAN_COUNT = 8
        private const val MESSAGE_ID = "messageId"
    }
}
