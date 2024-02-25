package com.example.channels.data.stream.mapper

import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.domain.model.StreamModel
import javax.inject.Inject

interface StreamDbToDomainMapper {
    fun toStreamModel(list: List<StreamDB>): List<StreamModel>
}

internal class StreamDbToDomainMapperImpl @Inject constructor() : StreamDbToDomainMapper {
    override fun toStreamModel(list: List<StreamDB>): List<StreamModel> {
        return list.map { it.toStreamModel() }
    }

    private fun StreamDB.toStreamModel(): StreamModel = StreamModel(id, name)
}
