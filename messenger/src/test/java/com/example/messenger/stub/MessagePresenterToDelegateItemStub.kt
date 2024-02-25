package com.example.messenger.stub

import com.example.common.adapter.interfaces.DelegateItem
import com.example.messenger.domain.model.DateModel
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.mapper.MessageDomainToUiMapper
import com.example.messenger.presentation.ui.adapters.main.delegates.date.DateDelegateItem
import com.example.messenger.presentation.ui.adapters.main.delegates.message.MessageDelegateItem

class MessagePresenterToDelegateItemStub : MessageDomainToUiMapper {
    override fun toDelegateItem(list: List<MessageModel>): List<DelegateItem> {
        list.sortedBy { it.timestamp }
        val delegateItemList: MutableSet<DelegateItem> = mutableSetOf()
        val dates: MutableList<DateDelegateItem> = mutableListOf()
        list.forEach { message ->
            val date = message.toDateDelegateItem()
            if (dates.isEmpty()) {
                dates.add(date)
                delegateItemList.add(date)
            } else if (date.value.date !in dates.map { it.value.date }) {
                dates.add(date)
                delegateItemList.add(date)
            }
            delegateItemList.add(message.toMessageDelegateItem())
        }
        return delegateItemList.toList()
    }

    private fun MessageModel.toMessageDelegateItem(): MessageDelegateItem =
        MessageDelegateItem(this@toMessageDelegateItem)

    private fun MessageModel.toDateDelegateItem(): DateDelegateItem {
        val dateModel = DateModel(timestamp.toFormatDate())
        return DateDelegateItem(dateModel)
    }

    private fun Int.toFormatDate(): String {
        return "10 April"
    }
}
