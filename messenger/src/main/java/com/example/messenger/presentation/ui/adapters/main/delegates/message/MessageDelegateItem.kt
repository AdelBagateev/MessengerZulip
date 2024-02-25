package com.example.messenger.presentation.ui.adapters.main.delegates.message

import com.example.common.adapter.interfaces.DelegateItem
import com.example.messenger.domain.model.MessageModel


class MessageDelegateItem(
    override val value: MessageModel,
) : DelegateItem {
    override fun id(): String = value.id.toString()

    override fun <T : DelegateItem> compareToOther(other: T): Boolean {
        return other.value == this.value
    }
}
