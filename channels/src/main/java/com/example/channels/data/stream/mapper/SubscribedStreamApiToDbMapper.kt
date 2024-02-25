package com.example.channels.data.stream.mapper

import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.stream.datasource.remote.response.subscribed.SubscribedStream
import javax.inject.Inject

interface SubscribedStreamApiToDbMapper {
    fun toStreamDb(list: List<SubscribedStream>): List<StreamDB>
}

internal class SubscribedStreamApiToDbMapperImpl @Inject constructor() :
    SubscribedStreamApiToDbMapper {
    override fun toStreamDb(list: List<SubscribedStream>): List<StreamDB> =
        list.map { it.toStreamDB() }

    private fun SubscribedStream.toStreamDB(): StreamDB = StreamDB(streamId, name, true)
}
