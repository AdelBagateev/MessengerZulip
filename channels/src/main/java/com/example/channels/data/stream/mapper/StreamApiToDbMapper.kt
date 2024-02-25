package com.example.channels.data.stream.mapper


import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.stream.datasource.remote.response.all.Stream
import javax.inject.Inject

interface StreamApiToDbMapper {
    fun toStreamDb(list: List<Stream>): List<StreamDB>
}

internal class StreamApiToDbMapperImpl @Inject constructor() : StreamApiToDbMapper {

    override fun toStreamDb(list: List<Stream>): List<StreamDB> =
        list.map { it.toStreamDB() }

    private fun Stream.toStreamDB(): StreamDB = StreamDB(streamId, name, false)
}
