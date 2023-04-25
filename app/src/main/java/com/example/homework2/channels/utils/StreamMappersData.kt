package com.example.homework2.channels.utils

import com.example.homework2.channels.data.stream.datasource.local.entity.StreamDB
import com.example.homework2.channels.data.stream.datasource.remote.response.all.Stream
import com.example.homework2.channels.data.stream.datasource.remote.response.subscribed.SubscribedStream
import com.example.homework2.channels.domain.model.StreamModel
import javax.inject.Inject

interface StreamMappersData {
    fun toStreamDB(list : List<SubscribedStream>): List<StreamDB>
    fun toStreamDBFromStream(list : List<Stream>): List<StreamDB>
    fun toStreamModel(list: List<StreamDB>) : List<StreamModel>
}
class StreamMappersDataImpl @Inject constructor() : StreamMappersData{
    override fun toStreamDB(list: List<SubscribedStream>): List<StreamDB> = list.map { it.toStreamDB() }
    override fun toStreamDBFromStream(list: List<Stream>): List<StreamDB> = list.map { it.toStreamDB() }
    override fun toStreamModel(list: List<StreamDB>): List<StreamModel> {
        return list.map { it.toStreamModel() }
    }

    private fun SubscribedStream.toStreamDB(): StreamDB = StreamDB(stream_id, name, true)
    private fun Stream.toStreamDB(): StreamDB = StreamDB(stream_id, name, false)
    private fun StreamDB.toStreamModel(): StreamModel = StreamModel(id, name)
}
