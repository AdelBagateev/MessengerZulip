package com.example.common.adapter.interfaces

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate<out VH : RecyclerView.ViewHolder, out DI : DelegateItem> {
    fun onCreateViewHolder(parent: ViewGroup, getDelegateItem: (Int) -> DelegateItem): VH
    fun onBindViewHolder(holder: @UnsafeVariance VH, item: @UnsafeVariance DI)
    fun isOfViewType(item: Any): Boolean
}
