package com.example.messenger.presentation.ui.adapters.main.delegates.date

import com.example.common.adapter.interfaces.DelegateItem
import com.example.messenger.domain.model.DateModel


class DateDelegateItem(
    override val value: DateModel,
) : DelegateItem {
    override fun id(): String = value.date

    override fun <T : DelegateItem> compareToOther(other: T): Boolean {
        return other.value == this.value
    }
}
