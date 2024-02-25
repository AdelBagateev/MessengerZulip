package com.example.channels.presentation.ui.delegates.stream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.databinding.ItemStreamBinding
import com.example.channels.domain.model.StreamModel
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem

class StreamDelegate(
    private val onStreamArrowClickListener: (StreamModel) -> Unit,
    private val onStreamClickListener: (String) -> Unit
) : AdapterDelegate<ViewHolder, StreamDelegateItem> {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        getDelegateItem: (Int) -> DelegateItem
    ): ViewHolder =
        ViewHolder(
            ItemStreamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setupActions(this, binding, getDelegateItem)
        }


    override fun onBindViewHolder(
        holder: ViewHolder,
        item: StreamDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is StreamDelegateItem
    }

    private fun setupActions(
        viewHolder: ViewHolder,
        binding: ItemStreamBinding,
        getDelegateItem: (Int) -> DelegateItem
    ) {
        binding.apply {
            streamName.setOnClickListener {
                val streamDelegate =
                    getDelegateItem(viewHolder.adapterPosition) as StreamDelegateItem
                onStreamClickListener(streamDelegate.value.name)
            }
            btnArrow.setOnClickListener {
                val streamDelegate =
                    getDelegateItem(viewHolder.adapterPosition) as StreamDelegateItem
                onStreamArrowClickListener(streamDelegate.value)
            }
        }
    }
}

class ViewHolder(
    val binding: ItemStreamBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: StreamModel) {

        with(binding) {
            streamName.text = model.name

            if (model.isChosen) {
                btnArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                btnArrow.setImageResource(R.drawable.ic_arrow_down)
            }
        }
    }
}
