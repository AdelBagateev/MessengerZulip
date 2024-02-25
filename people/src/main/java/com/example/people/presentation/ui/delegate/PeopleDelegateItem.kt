package com.example.people.presentation.ui.delegate

import com.example.common.adapter.interfaces.DelegateItem
import com.example.people.domain.model.PeopleModel

class PeopleDelegateItem(
    override val value: PeopleModel,
) : DelegateItem {
    override fun id(): String = value.id.toString()
    override fun <T : DelegateItem> compareToOther(other: T): Boolean {
        return other.value == this.value
    }
}
