package com.example.messenger.presentation.ui.adapters.emoji

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemEmojiBinding
import com.example.messenger.domain.model.ReactionModel

class EmojisAdapter(
    private val onItemClickListener: (ReactionModel) -> Unit
) : ListAdapter<ReactionModel, EmojisAdapter.EmojiViewHolder>(EmojiDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        val holderBinding = ItemEmojiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmojiViewHolder(holderBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EmojiDiffUtil : DiffUtil.ItemCallback<ReactionModel>() {
        override fun areItemsTheSame(
            oldItem: ReactionModel,
            newItem: ReactionModel,
        ): Boolean =
            oldItem.emojiCode == newItem.emojiCode

        override fun areContentsTheSame(
            oldItem: ReactionModel,
            newItem: ReactionModel,
        ): Boolean =
            oldItem == newItem
    }

    class EmojiViewHolder(
        private val binding: ItemEmojiBinding,
        private val onItemClickListener: (ReactionModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reaction: ReactionModel) {
            binding.run {
                emoji.text = reaction.emojiCode
                emoji.setOnClickListener {
                    onItemClickListener(reaction)
                }
            }
        }
    }
}
