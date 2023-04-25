package com.example.homework2.channels.data.topic

import com.example.homework2.channels.data.topic.datasource.local.TopicDao
import com.example.homework2.channels.data.topic.datasource.local.entity.TopicDB
import com.example.homework2.channels.data.topic.datasource.remote.TopicApi
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.channels.domain.topic.TopicRepository
import com.example.homework2.channels.utils.TopicMapperData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class TopicRepositoryImpl @Inject constructor(
    private val api: TopicApi,
    private val local: TopicDao,
    private val mapper : TopicMapperData
) : TopicRepository {

    override  fun subscribeOnTopicsByStream(stream: StreamModel): Flow<List<TopicModel>> {
        return local.getTopics(stream.id)
            .filter { it.isNotEmpty() }
            .mapNotNull { mapper.toTopicModel(it)}
    }

    override suspend fun loadTopicByStream(stream: StreamModel) {
        val topics = getTopicsFromNetwork(stream)
        local.saveTopics(topics)
    }

    private suspend fun getTopicsFromNetwork(stream: StreamModel): List<TopicDB> {
        val topicsFromNetwork = api.getTopicByStreamId(stream.id).topics
        return mapper.toTopicDB(topicsFromNetwork, stream)
    }
}
