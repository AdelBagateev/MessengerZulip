package com.example.homework2.channels.utils

import com.example.homework2.channels.data.stream.datasource.local.entity.StreamDB
import com.example.homework2.channels.data.topic.datasource.local.entity.TopicDB
import com.example.homework2.channels.data.topic.datasource.remote.response.Topic
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import javax.inject.Inject

interface TopicMapperData {
    fun toTopicDB(list : List<Topic>, stream: StreamModel): List<TopicDB>
    fun toTopicModel(list : List<TopicDB>) : List<TopicModel>
}

class TopicMapperDataImpl @Inject constructor() : TopicMapperData {
    override fun toTopicDB(list: List<Topic>, stream: StreamModel): List<TopicDB> =
        list.map { it.toTopicDB(stream) }

    override fun toTopicModel(list: List<TopicDB>): List<TopicModel> =
        list.map {it.toTopicModel()}

    private fun  TopicDB.toTopicModel() : TopicModel {
        return TopicModel(name, stream.toStreamModel())
    }
    private fun StreamDB.toStreamModel() : StreamModel {
        return StreamModel(id, name)
    }
    private fun Topic.toTopicDB(stream: StreamModel): TopicDB = TopicDB(name, stream.toStreamDB())

    private fun StreamModel.toStreamDB() : StreamDB =
        StreamDB(id, name, false)

}
