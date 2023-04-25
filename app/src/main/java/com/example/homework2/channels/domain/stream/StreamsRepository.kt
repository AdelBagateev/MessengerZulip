package com.example.homework2.channels.domain.stream

import com.example.homework2.channels.domain.model.StreamModel
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {
    fun subscribeOnStreams(isSubscribedStreams : Boolean): Flow<List<StreamModel>>
    suspend fun loadStreams(isSubscribedStreams : Boolean)
}
