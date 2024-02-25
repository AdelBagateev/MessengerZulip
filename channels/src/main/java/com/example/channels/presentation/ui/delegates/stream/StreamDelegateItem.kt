package com.example.channels.presentation.ui.delegates.stream

import com.example.channels.domain.model.StreamModel
import com.example.common.adapter.interfaces.DelegateItem


class StreamDelegateItem(
    override val value: StreamModel,
) : DelegateItem {
    override fun id(): String = value.id.toString()

    override fun <T : DelegateItem> compareToOther(other: T): Boolean {
        return other.value == this.value
    }
}
