package com.example.people.data

import com.example.common.asyncAwaitAndTransform
import com.example.people.data.datasource.local.PeopleDao
import com.example.people.data.datasource.local.entity.PeopleDB
import com.example.people.data.datasource.remote.PeopleApi
import com.example.people.data.mapper.PeopleApiToDbMapper
import com.example.people.data.mapper.PeopleDbToDomainMapper
import com.example.people.domain.PeopleRepository
import com.example.people.domain.model.PeopleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class PeopleRepositoryImpl @Inject constructor(
    private val api: PeopleApi,
    private val local: PeopleDao,
    private val peopleApiToDbMapper: PeopleApiToDbMapper,
    private val peopleDbToDomainMapper: PeopleDbToDomainMapper
) : PeopleRepository {
    override fun subscribeOnPeople(): Flow<List<PeopleModel>> {
        return local.getPeople()
            .filter { it.isNotEmpty() }
            .mapNotNull { peopleDbToDomainMapper.toPeopleModel(it) }
    }

    override suspend fun loadPeople() {
        val people = getPeopleFromNetwork()
        savePeopleInDb(people)
    }

    private suspend fun getPeopleFromNetwork(): List<PeopleDB> {
        val peopleDb = asyncAwaitAndTransform(
            { api.getPeoplePresence() },
            { api.getPeople() }
        ) { presencesResponse, peopleResponse ->
            peopleApiToDbMapper.toPeopleDB(presencesResponse, peopleResponse)
        }
        return peopleDb
    }

    private suspend fun savePeopleInDb(people: List<PeopleDB>) {
        local.savePeople(people)
    }
}
