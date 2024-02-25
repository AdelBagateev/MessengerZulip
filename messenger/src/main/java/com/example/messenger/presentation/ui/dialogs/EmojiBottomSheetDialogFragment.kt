package com.example.messenger.presentation.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.lazyUnsafe
import com.example.messenger.databinding.FragmentBottomSheetDialogEmojiBinding
import com.example.messenger.domain.model.ReactionModel
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import com.example.messenger.presentation.ui.adapters.emoji.EmojisAdapter
import com.example.messenger.presentation.ui.utils.EmojiNCS
import com.example.messenger.presentation.ui.utils.ReactionsFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import vivid.money.elmslie.core.store.Store

class EmojiBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDialogEmojiBinding? = null
    private val binding get() = _binding!!

    private var messageId: Int? = null

    var dialogStore: Store<MessengerEvent, MessengerEffect, MessengerState>? = null

    private val emojiAdapter: EmojisAdapter by lazyUnsafe {
        EmojisAdapter { onEmojiClick(it) }
    }
    private val emojis by lazyUnsafe {
        ReactionsFactory.getEmojisList()
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
        messageId?.let {
            dialogStore?.accept(MessengerEvent.Ui.AddNewEmojiToReactions(it, reaction))
        }
        dismiss()
    }

    private fun List<EmojiNCS>.toReactionList(): List<ReactionModel> =
        map {
            ReactionModel(ReactionsFactory.getCodeString(it.code), 1, 1, true, it.name)
        }

    companion object {

        fun newInstance(messageId: Int): EmojiBottomSheetDialogFragment {
            val bottomSheetDialog = EmojiBottomSheetDialogFragment().apply {
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
