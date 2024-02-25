package com.example.people.domain

import com.example.people.domain.model.PeopleModel
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun subscribeOnPeople(): Flow<List<PeopleModel>>
    suspend fun loadPeople()
}
