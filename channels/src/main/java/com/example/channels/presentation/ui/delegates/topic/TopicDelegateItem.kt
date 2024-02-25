package com.example.channels.presentation.ui.delegates.topic

import com.example.channels.domain.model.TopicModel
import com.example.common.adapter.interfaces.DelegateItem

class TopicDelegateItem(
    override val value: TopicModel,
) : DelegateItem {
    override fun id(): String = value.name

    override fun <T : DelegateItem> compareToOther(other: T): Boolean {
        return other.value == this.value
    }
}
