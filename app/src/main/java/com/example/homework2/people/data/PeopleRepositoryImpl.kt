package com.example.homework2.people.data

import com.example.homework2.common.asyncAwait
import com.example.homework2.people.data.datasource.local.PeopleDao
import com.example.homework2.people.data.datasource.local.entity.PeopleDB
import com.example.homework2.people.data.datasource.remote.PeopleApi
import com.example.homework2.people.domain.PeopleRepository
import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.utils.PeopleMapperData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class PeopleRepositoryImpl @Inject constructor(
    private val api: PeopleApi,
    private val local: PeopleDao,
    private val mapper: PeopleMapperData
) : PeopleRepository {
    override fun subscribeOnPeople(): Flow<List<PeopleModel>> {
        return local.getPeople()
            .filter{it.isNotEmpty()}
            .mapNotNull { mapper.toPeopleModel(it) }
    }

    override suspend fun loadPeople(){
        val people = getPeopleFromNetwork()
        savePeopleInDb(people)
    }

    private suspend fun getPeopleFromNetwork() : List<PeopleDB> {
        val peopleDb =  asyncAwait(
            { api.getPeoplePresence()},
            { api.getPeople()}
        ) {presencesResponse, peopleResponse ->
            mapper.toPeopleDB(presencesResponse, peopleResponse)
        }
        return peopleDb
    }

    private fun savePeopleInDb(people : List<PeopleDB>) {
        local.savePeople(people)
    }
}
