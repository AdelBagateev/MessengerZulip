package com.example.homework2.dialog.presentation.ui.adapters.main.delegates.date

import android.os.Parcelable
import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.dialog.domain.model.DateModel
import kotlinx.parcelize.Parcelize

@Parcelize
class DateDelegateItem(
    val value: DateModel,
) : DelegateItem, Parcelable {
    override fun id(): String = value.date

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as DateDelegateItem).value == this.value
    }
}
