package com.example.channels.data.topic.mapper

import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.topic.datasource.local.entity.TopicDB
import com.example.channels.data.topic.datasource.remote.response.Topic
import com.example.channels.domain.model.StreamModel
import javax.inject.Inject

interface TopicApiToDbMapper {
    fun toTopicDB(list: List<Topic>, stream: StreamModel): List<TopicDB>
}

internal class TopicApiToDbMapperImpl @Inject constructor() : TopicApiToDbMapper {
    override fun toTopicDB(list: List<Topic>, stream: StreamModel): List<TopicDB> =
        list.map { it.toTopicDB(stream) }

    private fun Topic.toTopicDB(stream: StreamModel): TopicDB = TopicDB(name, stream.toStreamDB())

    private fun StreamModel.toStreamDB(): StreamDB =
        StreamDB(id, name, false)

}
