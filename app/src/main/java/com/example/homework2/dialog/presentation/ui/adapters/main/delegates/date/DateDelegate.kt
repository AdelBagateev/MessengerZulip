package com.example.homework2.dialog.presentation.ui.adapters.main.delegates.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.common.adapter.interfaces.AdapterDelegate
import com.example.homework2.databinding.ItemDateBinding
import com.example.homework2.dialog.domain.model.DateModel

class DateDelegate : AdapterDelegate<ViewHolder, DateDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: DateDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is DateDelegateItem
    }

}

class ViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: DateModel) {
        with(binding) {
            tvDate.text = model.date
        }
    }
}
