package com.example.homework2.channels.presentation.ui.delegates.topic

import android.os.Parcelable
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.common.adapter.interfaces.DelegateItem
import kotlinx.parcelize.Parcelize

@Parcelize
class TopicDelegateItem(
    val value: TopicModel,
) : DelegateItem, Parcelable {
    override fun id(): String = value.name

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as TopicDelegateItem).value == this.value
    }
}
