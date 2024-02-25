package com.example.channels.domain.stream

import com.example.channels.domain.model.StreamModel
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {
    fun subscribeOnStreams(isSubscribedStreams: Boolean): Flow<List<StreamModel>>
    suspend fun loadStreams(isSubscribedStreams: Boolean)

    suspend fun createNewStream(streamName: String, description: String)
}
