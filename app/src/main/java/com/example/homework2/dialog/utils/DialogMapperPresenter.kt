package com.example.homework2.dialog.utils

import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.dialog.domain.model.DateModel
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.presentation.ui.adapters.main.delegates.date.DateDelegateItem
import com.example.homework2.dialog.presentation.ui.adapters.main.delegates.message.MessageDelegateItem
import javax.inject.Inject

interface DialogMapperPresenter {
    fun toMessagesDataDelegates(list : List<MessageModel>): List<DelegateItem>
    fun addNewMessage(list : List<DelegateItem>, message: MessageDelegateItem): List<DelegateItem>
}

class DialogMapperPresenterImpl @Inject constructor() : DialogMapperPresenter {
    override fun toMessagesDataDelegates(list: List<MessageModel>): List<DelegateItem> {
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

    override fun addNewMessage(
        list: List<DelegateItem>,
        message: MessageDelegateItem
    ): List<DelegateItem> {
        val newDelegateItemList = list.toMutableList()
        val dateList = list.filterIsInstance<DateDelegateItem>().map { it.value.date }
        val messageDate = message.value.timestamp.toFormatDate()
        if (messageDate !in dateList) {
            newDelegateItemList.add(DateDelegateItem(DateModel(messageDate)))
        }
        newDelegateItemList.add(message)
        return newDelegateItemList
    }

    private fun MessageModel.toMessageDelegateItem(): MessageDelegateItem =
        MessageDelegateItem(this@toMessageDelegateItem)

    private fun MessageModel.toDateDelegateItem(): DateDelegateItem {
        val dateModel = DateModel(timestamp.toFormatDate())
        return DateDelegateItem(dateModel)
    }
}
