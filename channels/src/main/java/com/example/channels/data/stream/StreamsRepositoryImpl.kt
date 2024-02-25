package com.example.channels.data.stream

import com.example.channels.data.stream.datasource.local.StreamDao
import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.stream.datasource.remote.StreamApi
import com.example.channels.data.stream.mapper.StreamApiToDbMapper
import com.example.channels.data.stream.mapper.StreamDbToDomainMapper
import com.example.channels.data.stream.mapper.SubscribedStreamApiToDbMapper
import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.stream.StreamsRepository
import com.example.common.asyncAwaitWithoutTransform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

internal class StreamsRepositoryImpl @Inject constructor(
    private val api: StreamApi,
    private val local: StreamDao,
    private val subscribedStreamApiToDbMapper: SubscribedStreamApiToDbMapper,
    private val streamApiToDbMapper: StreamApiToDbMapper,
    private val streamDbToDomainMapper: StreamDbToDomainMapper,
) : StreamsRepository {

    override fun subscribeOnStreams(isSubscribedStreams: Boolean): Flow<List<StreamModel>> {
        return local.getStreams(isSubscribedStreams)
            .filter { it.isNotEmpty() }
            .mapNotNull { streamDbToDomainMapper.toStreamModel(it) }
    }

    override suspend fun loadStreams(isSubscribedStreams: Boolean) {
        val streamList = getStreamsFromNetwork(isSubscribedStreams)
        saveInDb(streamList)
    }

    override suspend fun createNewStream(streamName: String, description: String) {
        val requestParams = createRequestParams(streamName, description)
        api.createNewStream(requestParams)
        updateStreamsInDB()
    }

    private suspend fun updateStreamsInDB() {
        asyncAwaitWithoutTransform(
            { loadStreams(false) },
            { loadStreams(true) }
        )
    }

    private suspend fun saveInDb(streamList: List<StreamDB>) {
        local.saveStreams(streamList)
    }

    private suspend fun getStreamsFromNetwork(isSubscribedStreams: Boolean): List<StreamDB> {
        val streamList = if (isSubscribedStreams) {
            subscribedStreamApiToDbMapper.toStreamDb(api.getSubscribedStreams().subscriptions)
        } else {
            streamApiToDbMapper.toStreamDb(api.getAllStreams().streams)
        }
        return streamList
    }

    private fun createRequestParams(streamName: String, description: String): String {
        val query = listOf(
            mapOf(
                "name" to streamName,
                "description" to description
            )
        )

        return Json.encodeToJsonElement(query).toString()
    }
}

