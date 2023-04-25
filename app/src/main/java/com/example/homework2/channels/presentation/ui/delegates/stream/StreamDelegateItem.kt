package com.example.homework2.channels.presentation.ui.delegates.stream

import android.os.Parcelable
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.common.adapter.interfaces.DelegateItem
import kotlinx.parcelize.Parcelize

@Parcelize
class StreamDelegateItem(
    val value: StreamModel,
) : DelegateItem, Parcelable {
    override fun id(): String = value.id.toString()

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as StreamDelegateItem).value == this.value
    }
}
