package com.example.people.domain.usecase

import com.example.people.domain.PeopleRepository
import javax.inject.Inject

internal class LoadPeopleUseCaseImpl @Inject constructor(
    private val repository: PeopleRepository
) : LoadPeopleUseCase {
    override suspend operator fun invoke() =
        repository.loadPeople()
}

interface LoadPeopleUseCase {
    suspend operator fun invoke()
}
