package com.example.people.data.mapper

import com.example.people.data.datasource.local.entity.PeopleDB
import com.example.people.domain.model.PeopleModel
import javax.inject.Inject

interface PeopleDbToDomainMapper {
    fun toPeopleModel(peopleDB: List<PeopleDB>): List<PeopleModel>
}

internal class PeopleDbToDomainMapperImpl @Inject constructor() : PeopleDbToDomainMapper {

    override fun toPeopleModel(peopleDB: List<PeopleDB>): List<PeopleModel> {
        return peopleDB.map { it.toPeopleModel() }
    }

    private fun PeopleDB.toPeopleModel(): PeopleModel =
        PeopleModel(id, fullName, avatarUrl, mail, status, isBot)
}
