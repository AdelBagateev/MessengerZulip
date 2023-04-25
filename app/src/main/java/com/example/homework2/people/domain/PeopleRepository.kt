package com.example.homework2.people.domain

import com.example.homework2.people.domain.model.PeopleModel
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun subscribeOnPeople(): Flow<List<PeopleModel>>
    suspend fun loadPeople()
}
