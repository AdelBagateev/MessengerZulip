package com.example.channels.presentation.ui.delegates.topic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.databinding.ItemTopicBinding
import com.example.channels.domain.model.TopicModel
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem

class TopicDelegate(
    private val onTopicClick: (TopicModel) -> Unit
) : AdapterDelegate<ViewHolder, TopicDelegateItem> {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        getDelegateItem: (Int) -> DelegateItem
    ): ViewHolder =
        ViewHolder(
            ItemTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        ).apply {
            setupActions(this, binding, getDelegateItem)
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: TopicDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is TopicDelegateItem
    }

    private fun setupActions(
        viewHolder: ViewHolder,
        binding: ItemTopicBinding,
        getDelegateItem: (Int) -> DelegateItem
    ) {
        binding.root.setOnClickListener {
            val topicDelegate = getDelegateItem(viewHolder.adapterPosition) as TopicDelegateItem
            onTopicClick(topicDelegate.value)
        }
    }
}

class ViewHolder(
    val binding: ItemTopicBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: TopicModel) {
        with(binding) {

            topicName.text = model.name
            root.background = getTopicColor(adapterPosition, root.context)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getTopicColor(position: Int, context: Context): Drawable? {
        return if (position % 2 == 0) {
            context.getDrawable(R.color.bg_topic_first)
        } else {
            context.getDrawable(R.color.bg_topic_second)
        }
    }
}
