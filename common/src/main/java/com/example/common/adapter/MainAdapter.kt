package com.example.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem

class MainAdapter(vararg delegates: AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>) :
    ListAdapter<DelegateItem, RecyclerView.ViewHolder>(
        DelegateAdapterItemCallback()
    ) {

    private val delegates: MutableList<AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>> =
        mutableListOf()

    init {
        this.delegates.addAll(delegates)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent) { getItem(it) }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].onBindViewHolder(holder, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.indexOfFirst { it.isOfViewType(currentList[position]) }
    }
}
