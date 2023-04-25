package com.example.homework2.people.presentation.ui.delegate

import android.os.Parcelable
import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.people.domain.model.PeopleModel
import kotlinx.parcelize.Parcelize

@Parcelize
class PeopleDelegateItem(
    val value: PeopleModel,
) : DelegateItem, Parcelable {
    override fun id(): String = value.id.toString()

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as PeopleDelegateItem).value == this.value
    }
}
