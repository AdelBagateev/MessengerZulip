package com.example.homework2.dialog.presentation.ui.adapters.main.delegates.message

import android.os.Parcelable
import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.dialog.domain.model.MessageModel
import kotlinx.parcelize.Parcelize

@Parcelize
class MessageDelegateItem(
    val value: MessageModel,
) : DelegateItem, Parcelable {
    override fun id(): String = value.id.toString()

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as MessageDelegateItem).value == this.value
    }

}
