package com.example.homework2.channels.presentation.ui.delegates.stream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.common.adapter.interfaces.AdapterDelegate
import com.example.homework2.databinding.ItemStreamBinding

class StreamDelegate(
    private val onStreamClickListener: (StreamModel) -> Unit
) : AdapterDelegate<ViewHolder, StreamDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemStreamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onStreamClickListener
        )


    override fun onBindViewHolder(
        holder: ViewHolder,
        item: StreamDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is StreamDelegateItem
    }
}

class ViewHolder(
    private val binding: ItemStreamBinding,
    private val onStreamClickListener: (StreamModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: StreamModel) {

        with(binding) {
            title.text = model.name

            if (model.isChosen) {
                btnTopics.setImageResource(R.drawable.ic_arrow_up)
            } else {
                btnTopics.setImageResource(R.drawable.ic_arrow_down)
            }

            btnTopics.setOnClickListener {
                onStreamClickListener(model)
            }
        }
    }
}
