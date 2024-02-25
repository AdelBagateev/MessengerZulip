package com.example.people.domain.usecase

import com.example.people.domain.PeopleRepository
import com.example.people.domain.model.PeopleModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class SubscribeOnPeopleUseCaseImpl @Inject constructor(
    private val repository: PeopleRepository
) : SubscribeOnPeopleUseCase {
    override operator fun invoke(): Flow<List<PeopleModel>> =
        repository.subscribeOnPeople()
}

interface SubscribeOnPeopleUseCase {
    operator fun invoke(): Flow<List<PeopleModel>>
}
