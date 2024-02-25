package com.example.channels.data.topic

import com.example.channels.data.topic.datasource.local.TopicDao
import com.example.channels.data.topic.datasource.local.entity.TopicDB
import com.example.channels.data.topic.datasource.remote.TopicApi
import com.example.channels.data.topic.mapper.TopicApiToDbMapper
import com.example.channels.data.topic.mapper.TopicDbToDomainMapper
import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.model.TopicModel
import com.example.channels.domain.topic.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class TopicRepositoryImpl @Inject constructor(
    private val api: TopicApi,
    private val local: TopicDao,
    private val topicApiToDbMapper: TopicApiToDbMapper,
    private val topicDbToDomainMapper: TopicDbToDomainMapper,
) : TopicRepository {

    override fun subscribeOnTopicsByStream(stream: StreamModel): Flow<List<TopicModel>> {
        return local.getTopics(stream.id)
            .filter { it.isNotEmpty() }
            .mapNotNull { topicDbToDomainMapper.toTopicModel(it) }
    }

    override suspend fun loadTopicByStream(stream: StreamModel) {
        val topics = getTopicsFromNetwork(stream)
        local.saveTopics(topics)
    }

    private suspend fun getTopicsFromNetwork(stream: StreamModel): List<TopicDB> {
        val topicsFromNetwork = api.getTopicByStreamId(stream.id).topics
        return topicApiToDbMapper.toTopicDB(topicsFromNetwork, stream)
    }
}
