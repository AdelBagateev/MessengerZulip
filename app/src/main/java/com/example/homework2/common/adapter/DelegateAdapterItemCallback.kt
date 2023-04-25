package com.example.homework2.common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.homework2.common.adapter.interfaces.DelegateItem

class DelegateAdapterItemCallback : DiffUtil.ItemCallback<DelegateItem>() {
    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem::class == newItem::class && oldItem.id() == newItem.id()
    }

    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem.compareToOther(newItem)
    }
}
