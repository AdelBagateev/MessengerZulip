package com.example.channels.presentation.mapper

import com.example.channels.domain.model.TopicModel
import com.example.channels.presentation.ui.delegates.topic.TopicDelegateItem
import javax.inject.Inject

interface TopicDomainToUiMapper {
    fun toDelegateItem(list: List<TopicModel>): List<TopicDelegateItem>
}

internal class TopicDomainToUiMapperImpl @Inject constructor() :
    TopicDomainToUiMapper {
    override fun toDelegateItem(list: List<TopicModel>): List<TopicDelegateItem> =
        list.map { it.toTopicDelegateItem() }

    private fun TopicModel.toTopicDelegateItem(): TopicDelegateItem = TopicDelegateItem(this)
}
