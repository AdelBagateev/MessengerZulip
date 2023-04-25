package com.example.homework2.channels.data.stream

import com.example.homework2.channels.data.stream.datasource.local.StreamDao
import com.example.homework2.channels.data.stream.datasource.local.entity.StreamDB
import com.example.homework2.channels.data.stream.datasource.remote.StreamApi
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.stream.StreamsRepository
import com.example.homework2.channels.utils.StreamMappersData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class StreamsRepositoryImpl  @Inject constructor (
    private val api : StreamApi,
    private val local: StreamDao,
    private val mapper: StreamMappersData
) : StreamsRepository {

    override fun subscribeOnStreams(isSubscribedStreams : Boolean): Flow<List<StreamModel>> {
        return local.getStreams(isSubscribedStreams)
            .filter { it.isNotEmpty() }
            .mapNotNull { mapper.toStreamModel(it) }
    }

    override suspend fun loadStreams(isSubscribedStreams : Boolean) {
        val streamList = getStreamsFromNetwork(isSubscribedStreams)
        saveInDb(streamList)
    }

    private fun saveInDb(streamList: List<StreamDB>) {
        local.saveStreams(streamList)
    }

    private suspend fun getStreamsFromNetwork(isSubscribedStreams: Boolean): List<StreamDB> {
        val streamList = if(isSubscribedStreams) {
            mapper.toStreamDB( api.getSubscribedStreams().subscriptions)
        } else {
            mapper.toStreamDBFromStream( api.getAllStreams().streams)
        }
        return streamList
    }
}
//
//override suspend fun getSubscribedStreams() : List<StreamModel> {
//    val list = api.getSubscribedStreams().subscriptions
//    return mapper.toStreamModel(list)
//}
//override suspend fun getAllStreams(): List<StreamModel> {
//    val list = api.getAllStreams().streams
//    return mapper.streamToStreamModel(list)
//}
