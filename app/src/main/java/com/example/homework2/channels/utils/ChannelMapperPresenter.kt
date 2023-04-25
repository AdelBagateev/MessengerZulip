package com.example.homework2.channels.utils

import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.channels.presentation.ui.delegates.stream.StreamDelegateItem
import com.example.homework2.channels.presentation.ui.delegates.topic.TopicDelegateItem
import javax.inject.Inject

interface ChannelMapperPresenter {
    fun toStreamDelegateItem(list: List<StreamModel> ): List<StreamDelegateItem>
    fun toTopicDelegateItem(list: List<TopicModel>): List<TopicDelegateItem>
}
class ChannelMapperPresenterImpl @Inject constructor() : ChannelMapperPresenter {
    override fun toStreamDelegateItem(list: List<StreamModel>): List<StreamDelegateItem>  =
        list.map { it.toStreamDelegateItem() }

    override fun toTopicDelegateItem(list: List<TopicModel>): List<TopicDelegateItem> =
        list.map { it.toTopicDelegateItem() }

    private fun StreamModel.toStreamDelegateItem(): StreamDelegateItem = StreamDelegateItem(this.copy())
    private fun TopicModel.toTopicDelegateItem(): TopicDelegateItem = TopicDelegateItem(this)
}
