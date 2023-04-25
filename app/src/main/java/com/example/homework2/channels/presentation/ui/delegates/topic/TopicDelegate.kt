package com.example.homework2.channels.presentation.ui.delegates.topic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.common.adapter.interfaces.AdapterDelegate
import com.example.homework2.databinding.ItemTopicBinding

class TopicDelegate(
    private val navigateToDialog: (TopicModel) -> Unit
) : AdapterDelegate<ViewHolder, TopicDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            navigateToDialog
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: TopicDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is TopicDelegateItem
    }

}

class ViewHolder(
    private val binding: ItemTopicBinding,
    private val navigateToDialog: (TopicModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: TopicModel) {
        with(binding) {

            root.background = getTopicColor(adapterPosition, root.context)
            title.text = model.name

            root.setOnClickListener {
                navigateToDialog(model)
            }

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getTopicColor(position: Int, context: Context) : Drawable? {
        return if(position % 2 == 0){
            context.getDrawable(R.color.bg_topic_first)
        } else {
            context.getDrawable(R.color.bg_topic_second)
        }
    }
}
