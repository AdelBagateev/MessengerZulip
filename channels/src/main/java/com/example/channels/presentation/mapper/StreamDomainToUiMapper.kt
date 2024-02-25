package com.example.channels.presentation.mapper

import com.example.channels.domain.model.StreamModel
import com.example.channels.presentation.ui.delegates.stream.StreamDelegateItem
import javax.inject.Inject

interface StreamDomainToUiMapper {
    fun toDelegateItem(list: List<StreamModel>): List<StreamDelegateItem>
}

internal class StreamDomainToUiMapperImpl @Inject constructor() :
    StreamDomainToUiMapper {
    override fun toDelegateItem(list: List<StreamModel>): List<StreamDelegateItem> =
        list.map { it.toStreamDelegateItem() }

    private fun StreamModel.toStreamDelegateItem(): StreamDelegateItem =
        StreamDelegateItem(this.copy())
}
