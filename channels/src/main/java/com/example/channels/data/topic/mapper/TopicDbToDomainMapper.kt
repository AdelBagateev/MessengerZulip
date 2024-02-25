package com.example.channels.data.topic.mapper

import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.topic.datasource.local.entity.TopicDB
import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.model.TopicModel
import javax.inject.Inject

interface TopicDbToDomainMapper {
    fun toTopicModel(list: List<TopicDB>): List<TopicModel>
}

internal class TopicDbToDomainMapperImpl @Inject constructor() : TopicDbToDomainMapper {
    override fun toTopicModel(list: List<TopicDB>): List<TopicModel> =
        list.map { it.toTopicModel() }

    private fun TopicDB.toTopicModel(): TopicModel {
        return TopicModel(name, stream.toStreamModel())
    }

    private fun StreamDB.toStreamModel(): StreamModel {
        return StreamModel(id, name)
    }
}
