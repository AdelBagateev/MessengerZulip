package com.example.homework2.channels.domain.topic

import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
     fun subscribeOnTopicsByStream(stream: StreamModel): Flow<List<TopicModel>>
     suspend fun loadTopicByStream(stream: StreamModel)
}
